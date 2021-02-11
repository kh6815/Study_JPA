package jpabasic.ex1hellojpa;

import ch.qos.logback.core.encoder.EchoEncoder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            //code
            /*//회원 등록
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");
            em.persist(member);*/

            /*
            //회원 수정
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getName() = " + findMember.getName());
            findMember.setName("MemberA");*/
            
            /*
            //회원삭제
            Member findMember = em.find(Member.class, 1L);
            em.remove(findMember);*/
            
            //모든 회원 조회하기
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1) //페이징  1~10 데이터 가져와
                    .setMaxResults(10) //페이징  1~10 데이터 가져와
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.getName() = " + member.getName());
            }
            tx.commit();

        }catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
