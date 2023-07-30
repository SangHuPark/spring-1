package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em; // 스프링 부트가 엔티티 매니저를 만드는데(DB 커넥션 속성이랑 알아서 연결하는 과정) 이를 injection 받는 문장.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // persist가 영속화하다, 영구저장하다는 의미
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member); // 없을 수도 있어서 옵셔널 처리
    }

    @Override
    public Optional<Member> findByName(String name) {
        // findByName에서는 특별히 JPQ라는 Query SQL 을 써야됨. 다른데서는 테이블 대상으로 SQL을 날리는데 객체를 대상으로 쿼리를 날리려면 JPQ를 써야함.
        // 여기서는 Member 엔티티 대상으로 쿼리를 날리기위해서 사용 from Member m 에서 m 은 as a 라는 엘리어스의 간결어.
        // 과정을 보면 select 시 m, 즉 멤버 엔티티 자체(객체 자체)를 select
        /*
        원래는
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .getResultList();
        return result;
        였으나 인라인 기능(Command + Option + N) 으로 간결히 만들 수 있음
        */

        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
