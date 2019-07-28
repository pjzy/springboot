package com.zy.common;

import com.zy.security.entity.JwtUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Set;

/**
 * 权限处理
 *
 * @author:zy 修改日期：2015/11/19
 */
public class Jurisdiction {

  /**
   * 访问权限及初始化按钮权限(控制按钮的显示 增删改查)
   *
   * @param menuUrl 菜单路径
   * @return
   */
  @SuppressWarnings("unchecked")
  public static boolean hasJurisdiction(String menuUrl) {
    // 判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
    /**
     * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到404页面
     * 根据按钮权限，授权按钮(当前点的菜单和角色中各按钮的权限匹对)
     */
    JwtUser user = getJwtUser();
    return readMenu(user.getAuthorities(), menuUrl);
  }

  /**
   * 校验菜单权限并初始按钮权限用于页面按钮显示与否(递归处理)
   *
   * @param authorities
   * @param menuUrl :访问地址
   * @return
   */
  @SuppressWarnings("unchecked")
  public static boolean readMenu(Collection<? extends GrantedAuthority> authorities, String menuUrl) {
//    for (int i = 0; i < authorities.size(); i++) {
//      if (menuList
//          .get(i)
//          .getMENU_URL()
//          .split(".do")[0]
//          .equals(menuUrl.split(".do")[0])) { // 访问地址与菜单地址循环匹配，如何匹配到就进一步验证，如果没匹配到就不处理(可能是接口链接或其它链接)
//        if (!menuList.get(i).isHasMenu()) { // 判断有无此菜单权限
//          return false;
//        } else { // 按钮判断
//          Map<String, String> map =
//              (Map<String, String>) session.getAttribute(USERNAME + Const.SESSION_QX); // 按钮权限(增删改查)
//          map.remove("add");
//          map.remove("del");
//          map.remove("edit");
//          map.remove("cha");
//          String MENU_ID = menuList.get(i).getMENU_ID();
//          Boolean isAdmin = "admin".equals(USERNAME);
//          map.put(
//              "add", (RightsHelper.testRights(map.get("adds"), MENU_ID)) || isAdmin ? "1" : "0");
//          map.put("del", RightsHelper.testRights(map.get("dels"), MENU_ID) || isAdmin ? "1" : "0");
//          map.put(
//              "edit", RightsHelper.testRights(map.get("edits"), MENU_ID) || isAdmin ? "1" : "0");
//          map.put("cha", RightsHelper.testRights(map.get("chas"), MENU_ID) || isAdmin ? "1" : "0");
//          session.removeAttribute(USERNAME + Const.SESSION_QX);
//          session.setAttribute(USERNAME + Const.SESSION_QX, map); // 重新分配按钮权限
//          return true;
//        }
//      } else {
//        if (!readMenu(menuList.get(i).getSubMenu(), menuUrl, session, USERNAME)) { // 继续排查其子菜单
//          return false;
//        }
//      }
//    }
    return true;
  }

  /**
   * 按钮权限(方法中校验)
   *
   * @param menuUrl 菜单路径
   * @param type 类型(add、del、edit、cha)
   * @return
   */
  @SuppressWarnings("unchecked")
  public static boolean buttonJurisdiction(String menuUrl, String type) {
    // 判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
    /**
     * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到404页面 根据按钮权限，授权按钮(当前点的菜单和角色中各按钮的权限匹对)
     */
    JwtUser user = getJwtUser();
    return readMenuButton(user.getAuthorities(), menuUrl, type);
  }

  /**
   * 校验按钮权限(递归处理)
   * @param authorities
   * @param menuUrl
   * @param type
   * @return
   */
  public static boolean readMenuButton(Collection<? extends GrantedAuthority> authorities, String menuUrl, String type) {
//    for (int i = 0; i < menuList.size(); i++) {
//      if (menuList
//          .get(i)
//          .getMENU_URL()
//          .split(".do")[0]
//          .equals(menuUrl.split(".do")[0])) { // 访问地址与菜单地址循环匹配，如何匹配到就进一步验证，如果没匹配到就不处理(可能是接口链接或其它链接)
//        if (!menuList.get(i).isHasMenu()) { // 判断有无此菜单权限
//          return false;
//        } else { // 按钮判断
//          Map<String, String> map =
//              (Map<String, String>) session.getAttribute(USERNAME + Const.SESSION_QX); // 按钮权限(增删改查)
//          String MENU_ID = menuList.get(i).getMENU_ID();
//          Boolean isAdmin = "admin".equals(USERNAME);
//          if ("add".equals(type)) {
//            return ((RightsHelper.testRights(map.get("adds"), MENU_ID)) || isAdmin);
//          } else if ("del".equals(type)) {
//            return ((RightsHelper.testRights(map.get("dels"), MENU_ID)) || isAdmin);
//          } else if ("edit".equals(type)) {
//            return ((RightsHelper.testRights(map.get("edits"), MENU_ID)) || isAdmin);
//          } else if ("cha".equals(type)) {
//            return ((RightsHelper.testRights(map.get("chas"), MENU_ID)) || isAdmin);
//          }
//        }
//      } else {
//        if (!readMenuButton(
//            menuList.get(i).getSubMenu(), menuUrl, session, USERNAME, type)) { // 继续排查其子菜单
//          return false;
//        }
//        ;
//      }
//    }
    return true;
  }

  /**
   * 获取当前登录的用户名
   *
   * @return
   */
  public static String getUsername() {
    return getJwtUser().getUsername();
  }

  /**
   * 获取用户的最高组织机构权限集合
   *
   * @return
   */
  public static Set<Long> getDEPARTMENT_IDS() {
    return getJwtUser().getDepartmentIds();
  }

  /**
   * 获取用户的最高组织机构权限
   *
   * @return
   */
  public static Long getDEPARTMENT_ID() {
    return getJwtUser().getDepartmentId();
    //    return getSession().getAttribute(Const.DEPARTMENT_ID).toString();
  }

  public static JwtUser getJwtUser() {
    UsernamePasswordAuthenticationToken token =
        (UsernamePasswordAuthenticationToken)
            SecurityContextHolder.getContext().getAuthentication();
    // details里面可能存放了当前登录用户的详细信息，也可以通过cast后拿到
    JwtUser userDetails = (JwtUser) token.getDetails();
    return userDetails;
  }
}
