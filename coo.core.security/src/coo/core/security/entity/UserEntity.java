package coo.core.security.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.validator.constraints.NotEmpty;

import coo.core.security.annotations.Log;

/**
 * 用户实体基类。
 * 
 * @param <U>
 *            用户类型
 * @param <A>
 *            职务类型
 * @param <S>
 *            用户设置类型
 */
@MappedSuperclass
public abstract class UserEntity<U extends UserEntity<U, A, S>, A extends ActorEntity<?, ?, ?>, S extends UserSettingsEntity<A>>
		extends ResourceEntity<U> {
	/** 姓名 */
	@NotEmpty
	@Field
	@Log(text = "姓名")
	private String name;
	/** 用户名 */
	@NotEmpty
	@Field(analyze = Analyze.NO)
	@Log(text = "用户名")
	private String username;
	/** 密码 */
	@NotEmpty
	@Log(text = "密码")
	private String password;
	/** 是否禁用 */
	@NotNull
	@Log(text = "是否启用")
	private Boolean enabled = Boolean.TRUE;
	/** 排序 */
	@Field(analyze = Analyze.NO)
	@Log(text = "排序")
	private Integer ordinal;
	/** 用户设置 */
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@PrimaryKeyJoinColumn
	private S settings;
	/** 用户职务 */
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("createDate")
	private List<A> actors = new ArrayList<A>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean disabled) {
		this.enabled = disabled;
	}

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public S getSettings() {
		return settings;
	}

	public void setSettings(S settings) {
		this.settings = settings;
	}

	public List<A> getActors() {
		return actors;
	}

	public void setActors(List<A> actors) {
		this.actors = actors;
	}
}
