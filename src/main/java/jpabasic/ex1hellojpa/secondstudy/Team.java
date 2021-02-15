package jpabasic.ex1hellojpa.secondstudy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    // RDB는 연관관계를 설정할 때, 한쪽 테이블에서 다른쪽 테이블의 키(외래키)를 참조하여 양방향관계를 만들지만
    // 자바에서 연관관계를 설정하려면 양쪽 클래스 모두 다른 클래스의 참조값을 가져야한다.
    // 그래서 RDB랑 자바 클래스를 매핑할 때 차이가 발생하는데, 자바의 어떤 참조값을 DB의 외래키로 설정할 것인지 의문이 생기게 된다.
    // 왜냐하면 ex) Member의 참조값 team과, Team의 참조값 List<Member> members에 값을 바꾸게 되면 둘다 바꿔져야하나? 라는 의문 들기 때문이다.
    // 그래서 연관관계의 주인을 설정하여 RDB의 외래키처럼 사용해야하기 때문에 @ManyToOne를 연관관계주인(외래키)으로 설정하고,
    // OneToMany를 mappedBy로 연관관계주인에 연결시켜준다.
    // 따라서 값을 연관관계주인인 @ManyToOne에서만 바꿀 수 있고, @OneToMany는 읽기만 가능하다.

    //DB 입장에서는 (Member N : 1 Team) N쪽이 외래키를 들고 있음으로 N쪽을 연관관계의 주인으로 설정해야한다.
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    /*
    public void addMember(Member member){
        member.setTeam(this);
        members.add(member);
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

}
