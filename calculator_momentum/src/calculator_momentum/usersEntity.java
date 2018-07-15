package calculator_momentum;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.Entity;
/**
 * Entity implementation class for Entity: users
 *
 */
@Entity
@Table(name = "users")
public class usersEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "user_name", nullable = false, length = 50)
    private String user_name;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "surname", nullable = false, length = 50)
    private String surname;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "password", nullable = false, length = 50)
    private String password;
	
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "is_admin", nullable = false, length = 1)
    private int is_admin;
	
	public usersEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIs_admin() {
		return is_admin;
	}

	public void setIs_admin(int is_admin) {
		this.is_admin = is_admin;
	}
   
}
