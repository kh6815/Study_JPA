package jpabasic.ex1hellojpa.firststudy;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


//@Entity
//@Table(name = "USER")
@SequenceGenerator(name = "member_seq_generator",
    sequenceName = "member_seq"
)
public class Member {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "member_seq_generator" ) //ID 자동생성
    //strategy = GenerationType.AUTO -> 는 DB 방언에 맞춰서 자동으로 생성
    //strategy = GenerationType.IDENTITY  -> 기본키 생성을 데이터베이스에 위임.
    //IDENTITY전략은 회원 생성시 ID값이 DB에 들어가야 생김으로 em.persist할 때 바로 sql문을 DB에 날린다. 그리고 데이터를 가져와 영속성 컨텍스트에 저장한다.
    //strategy = GenerationType.SEQUENCE -> 오라클에서 많이 사용, 숫자형식으로 자동 생성
    private Long id;

    //@Column 속성  name은 칼럼 이름 매칭, unique 중복 체크, length는 문자 길이 제약조건, insertable/updatable 등록 변경 가능 여부(default = true),
    //nullable(DDL) not null 제약조건
    @Column(name = "name", unique = true, length = 10)
    private String username;
    private Integer age;


    //DB에는 Enum 타입이 없기 때문에 자바 Enum 타입을 사용하려면 @Enumerated를 사용해야한다.
    //EnumType.ORDINAL은 db에 Enum 타입이 저장될 때 값이 숫자 형태로 순서대로 저장되는데, Enum타입에 새로운 타입이 추가되면 기존의 모든 타입 숫자 순서가 맞지 않기 때문에
    //EnumType.STRING을 사용하자 -> Enum 문자 타입으로 db에 저장한다.
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    /*
    //DB는 DATE(날짜), TIME(시간), TIMESTAMP(날짜, 시간) 이렇게 3가지 타입으로 나누어서 사용되고,
    //자바에서는 Date로 날짜 시간 객체를 사용하기 때문에 두개의 ORM을 위해 @Temporal를 사용하여 맵핑해야한다.
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    // Date타입은 요즘에 잘 쓰지 않고 LocalDate, LocalDateTime을 사용하여 db에 별다른 애노테이션 없이 등록할 수 있다.
    */
    private LocalDate createdDate;
    private LocalDateTime lastModifiedDate;

    //DB에 VARCHER를 넘어서는 큰 스트링 값을 넣고 싶을 땐 @Lob을 사용하여 맵핑한다.
    @Lob
    private String description;

    //메모리에서 사용하고 DB에는 연관관계를 갖지 않을 때 사용하는 @Transient, DB에 칼럼으로 맵핑되지 않음.
    @Transient
    private int temp;

    public Member(){};
    public Member(String username) {
        //this.id = id;
        this.username = username;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
