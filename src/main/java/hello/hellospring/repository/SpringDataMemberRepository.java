package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 인터페이스가 인터페이스를 받을 때는 기존에 상속받는 방법인 implements 가 아니고 extends
// 스프링 데이터 JPA는 인터페이스 선언해주면 스프링 데이터 JPA가 Bean에 자동으로 등록함
// Save나 정의 안된 메서드들이 없어도 동작하는 이유는 스프링 데이터 JPA 안에 구현되어 있음
public interface SpringDataMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
}
