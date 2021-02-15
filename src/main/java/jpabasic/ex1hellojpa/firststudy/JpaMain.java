package jpabasic.ex1hellojpa.firststudy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        /*
        try{
            //code
            //회원 등록
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");
            em.persist(member);
        }catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }*/

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

        /*
        //모든 회원 조회하기
        List<Member> result = em.createQuery("select m from Member as m", Member.class)
                .setFirstResult(1) //페이징  1~10 데이터 가져와
                .setMaxResults(10) //페이징  1~10 데이터 가져와
                .getResultList();

        for (Member member : result) {
            System.out.println("member.getName() = " + member.getName());
        }*/


        /*
        ////------------------------------////
        //비영속
        Member member = new Member();
        member.setId(1L);
        member.setName("최강현");

        //영속
        em.persist(member); //persist를 해서 엔티티를 영속상태를 만들었지만 이때 DB에 저장되는 것은 아니다.
        //엔티티를 영속상태로 만든후 Transaction을 통해 commit를 해야 그때 DB에 쿼리가 날라가 저장된다.
        //em.detach(member); //준영속, 엔티티를 영속성 컨텍스트에서 분리한다.
        //em.remove(member); // 객체를 삭제한 상태*/



        /*
        ////------------------------------////
        //영속성 컨텍스트의 1차캐시와, 쓰기지연 SQL 저장소
        Member member1 = new Member(150L, "A");
        Member member2 = new Member(160L, "A");

        try{
            em.persist(member1); //persist 할때 영속성 컨텍스트에 있는 1차캐시에 pk와 엔티티를 저장하고, 쓰기 지연 SQL 저장소에 미리 SQL을 만들어 놓는다.
            em.persist(member2);

            System.out.println("===================");
            tx.commit(); // commit할때 영속성 컨텍스트에 있는 쓰기지연 SQL 저장소에서 SQL을 DB로 flush하고 실제 데이터가 저장된다.
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }*/

        /*
        ////------------------------------////
        //엔티티 수정 / 변경감지
        //JPA의 목적은 자바 컬렉션 다루듯이, 영속성 컨텍스에 엔티티가 종속되어 있을 때 엔티티의 변경이 있으면 자동으로 값이 변경된다.
        //위의 member1의 값을 수정해보자
        try{
            Member findMember = em.find(Member.class, 150L);
            findMember.setName("최강현"); //em.persist를 할 필요없이 현재 findMember가 영속성컨텍스트에 등록되어있기 때문에 자동으로 엔티티의 값이 수정된다.

            //em.persist(findMember);

            tx.commit(); // commit할때 영속성 컨텍스트에 있는 쓰기지연 SQL 저장소에서 SQL을 DB로 flush하고 실제 데이터가 저장된다.

            //변경감지 동작 구현 순서는
            // 첫번째로 1차 캐시에 ID와 엔티티 그리고 저장된 엔티티의 스냅샷을 찍어놓는다.
            // 두번째로 flush()를 했을 때 1차 캐시에 저장된 엔티티와 스냅샷을 비교한다.
            // 값이 다르면 update 쿼리를 쓰기지연 SQL 저장소에 저장해둔다.
            // 그래서 db에 update쿼리를 반영한다.

            //** flush(플러시) : 영속성 컨텍스트의 변경내용을 데이터베이스에 반영
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }*/



        ////------------------------------////
        //** flush(플러시) : 영속성 컨텍스트의 변경내용을 데이터베이스에 반영
        //플러시는 !
        // 영속성 컨텍스트를 비우지 않음.
        // 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
        // 트랜잭션이라는 작업 단위가 중요-> 커밋 직전에만 동기화 하면됨.
        try{
            Member member = new Member("성현");

            em.persist(member);
            em.flush(); //원래 트랜잭션의 commit을 할 때 flush를 날려 db에 저장하지만, 이렇게 트랙잭션 commit전에 SQL를 강제로 db에 flush로 날릴 수 있다.


            System.out.println("========================================");

            tx.commit(); // commit할때 영속성 컨텍스트에 있는 쓰기지연 SQL 저장소에서 SQL을 DB로 flush하고 실제 데이터가 저장된다.




            /*
            //**JPQL 쿼리 실행시 플러시가 자동으로 호출되는 이유
            em.persist(memberA);
            em.persist(memberB);
            em.persist(memberC);

            // 중간에 JPQL 실행
            List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .getResultList();
            //이렇게 em.persist로 memberA,B,C를 영속성 컨테스트에 저장하고, 바로 DB에서 데이터를 조회해 오면 원래는 데이터가 조회되지 않는다. 그 이유는 flush로 db에 memeberA,B,C가 저장되지 않았기 때문이다.
            //그래서 JPA는 JPQL문이 실행될 때 자동으로 flush를 통해 쓰기지연 SQL 저장소의 SQL문을 보낸다. -> 그래서 memberA,B,C가 조회된다.*/
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
