package coo.core.security.entity;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.shiro.SecurityUtils;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Resolution;

import coo.base.util.DateUtils;
import coo.base.util.StringUtils;
import coo.core.model.UuidEntity;
import coo.core.security.annotations.Log;
import coo.core.security.service.AbstractSecurityService;
import coo.core.util.SpringUtils;

/**
 * 资源实体基类。
 * 
 * @param <U>
 *            用户类型
 */
@MappedSuperclass
public abstract class ResourceEntity<U extends UserEntity<U, ?, ?>> extends
		UuidEntity {
	@Log(text = "创建人", property = "username")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creatorId")
	@NotNull
	protected U creator;
	@Log(text = "创建时间", format = DateUtils.TO_SECOND)
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Field(analyze = Analyze.NO)
	@DateBridge(resolution = Resolution.MILLISECOND)
	protected Date createDate;
	@Log(text = "修改人", property = "username")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modifierId")
	@NotNull
	protected U modifier;
	@Log(text = "修改时间", format = DateUtils.TO_SECOND)
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Field(analyze = Analyze.NO)
	@DateBridge(resolution = Resolution.MILLISECOND)
	protected Date modifyDate;

	/**
	 * 根据上下文自动填充自身属性。
	 */
	public void autoFillIn() {
		if (StringUtils.isEmpty(getId())) {
			creator = getCurrentUser();
			createDate = new Date();
			modifier = getCurrentUser();
			modifyDate = new Date();
		} else {
			modifier = getCurrentUser();
			modifyDate = new Date();
		}
	}

	/**
	 * 从当前上下文中获取用户对象。
	 * 
	 * @return 返回当前上下文中的用户对象。
	 */
	@SuppressWarnings("unchecked")
	private U getCurrentUser() {
		String userId = (String) SecurityUtils.getSubject().getPrincipal();
		AbstractSecurityService<?, U, ?, ?, ?> securityService = (AbstractSecurityService<?, U, ?, ?, ?>) SpringUtils
				.getBean("securityService");
		return securityService.getUser(userId);
	}

	public U getCreator() {
		return creator;
	}

	public void setCreator(U creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public U getModifier() {
		return modifier;
	}

	public void setModifier(U modifier) {
		this.modifier = modifier;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}
