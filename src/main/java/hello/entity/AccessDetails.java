package hello.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;


@Entity
@Table(name = "access_details")
@Getter
@Setter
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class AccessDetails {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accessId;

    @Column
    private String channel;

    @Type(type = "jsonb")
    @Column
    private Object jsonStructure;

    @Column
    private Integer dependentAccessId;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private EmployeeRole role;
}