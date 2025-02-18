package kumaru.spring_beginning.repository;

import jakarta.persistence.EntityManager;
import kumaru.spring_beginning.domain.Member;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository
{
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name).getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
//        jpql. 객체(멤버 엔티티)를 대상으로 쿼리를 날림 -> sql로 번역됨
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
