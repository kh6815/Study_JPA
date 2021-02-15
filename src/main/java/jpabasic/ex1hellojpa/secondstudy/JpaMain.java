package jpabasic.ex1hellojpa.secondstudy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            /*
            // 그냥 연관관계 매핑 없이 구현했을 때
            Team team = new Team();
            team.setName("TeamA");

            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            // 영속성 컨텍스트는 항상 @Id(primaryKey)와 엔티티를 같이 저장한다.
            // 현재 Team 엔티티는 Id를 @GeneratedValue로 DB에 위임하고 있기때문에 em.persist(team)를 하면 DB에 먼저 team데이터를 저장하여 PK를 얻고
            // 그후에 DB에서 값을 가져와 영속성 컨텍스트에 저장한다. 고로 team.getId()로 값을 가져올 수 있다.
            member.setTeamId(team.getId());
            em.persist(member);
            
            Member findMember = em.find(Member.class, member.getId());
            Long teamId = member.getTeamId();
            em.find(Team.class, teamId)*/
            
            
            
            /*
            //연관관계 매핑 있을 때 @ManyToOne
            Team team = new Team();
            team.setName("TeamA");

            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member);

            //지금 member가 영속성 컨텍스트의 1차캐시에 저장되어 있기때문에, 따로 DB에 SQL을 통해 데이터를 가져오지 않고 1차캐시에서 값을 가져온다.
            // 혹시나 DB에서 값을 가져오는 SQL을 보고싶으면
            // em.flush로 영속성컨텍스트에 있는 SQL쿼리를 다 강제로 날려버리고, em.clear();로 영속성 컨텍스트를 초기화 시킨다.
            // em.flush();
            // em.clear();
            Team findTeam = em.find(Member.class, member.getId()).getTeam();
            System.out.println("findTeam.getName() = " + findTeam.getName());
            */


            //연관관계 매핑 OneToMany
            Team team = new Team();
            team.setName("Team1");

            em.persist(team);

            Member member = new Member();
            member.setUsername("강현");
            member.changeTeam(team);

            em.persist(member);

            //team.getMembers().add(member);

            em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();

            for (Member mb : members) {
                System.out.println("맴버 = " + mb);
            }

            List<Member> members2 = em.find(Member.class, member.getId()).getTeam().getMembers();
            for (Member mb : members2) {
                System.out.println("맴버 = " + mb);
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
