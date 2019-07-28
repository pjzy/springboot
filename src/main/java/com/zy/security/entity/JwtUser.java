package com.zy.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * @title: JwtUser
 * @projectName com.zy.entity.security
 * @description: TODO
 * @author zy
 * @date 20190330 17:53
 */
public class JwtUser implements UserDetails {

  private final Long id;
  private final Long orgId;
    private final Long departmentId;
    private final Set<Long> departmentIds;
  private final String username;
  private final String firstname;
  private final String lastname;
  private final String password;
  private final String email;
  private final Collection<? extends GrantedAuthority> authorities;
  private final boolean enabled;
  private final Date lastPasswordResetDate;

  /**
   * 功能描述:
   *
   * @param:
   * @return:
   * @auther: zy
   * @date: 20190330 17:54
   */
  public JwtUser(
          Long id,
          Long orgId, Long departmentId, Set<Long> departmentIds, String username,
          String firstname,
          String lastname,
          String email,
          String password,
          Collection<? extends GrantedAuthority> authorities,
          boolean enabled,
          Date lastPasswordResetDate) {
    this.id = id;
      this.orgId = orgId;
      this.departmentId = departmentId;
      this.departmentIds = departmentIds;
      this.username = username;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
    this.enabled = enabled;
    this.lastPasswordResetDate = lastPasswordResetDate;
  }

  @JsonIgnore
  public Long getId() {
    return id;
  }

    public Long getOrgId() {
        return orgId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    @Override
  public String getUsername() {
    return username;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getEmail() {
    return email;
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @JsonIgnore
  public Date getLastPasswordResetDate() {
    return lastPasswordResetDate;
  }

  @Override
  public boolean equals(Object o) {
    if (o.toString().equals(this.username)) return true;
    return false;
  }

  @Override
  public int hashCode() {
    return username.hashCode();
  }

  @Override
  public String toString() {
    return this.username;
  }

    public Set<Long> getDepartmentIds() {
      return departmentIds;
    }
}
