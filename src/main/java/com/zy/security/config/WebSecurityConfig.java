package com.zy.security.config;

import com.zy.filter.CuzUsernamePasswordAuthenticationFilter;
import com.zy.security.AuthenticationFailHandler;
import com.zy.security.AuthenticationSuccessHandler;
import com.zy.security.JwtAuthenticationEntryPoint;
import com.zy.security.JwtAuthorizationTokenFilter;
import com.zy.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true) // 添加注解@EnableGlobalMethodSecurity，并设置prePostEnabled为true（默认是false）
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private JwtAuthenticationEntryPoint unauthorizedHandler;

  @Autowired private JwtUserDetailsService jwtUserDetailsService;

  // Custom JWT based security filter
  @Autowired JwtAuthorizationTokenFilter authenticationTokenFilter;

  @Value("${jwt.header}")
  private String tokenHeader;

  @Value("${jwt.route.authentication.path}")
  private String authenticationPath;

  @Autowired
  @Qualifier("authenticationSuccessHandler")
  private AuthenticationSuccessHandler successHandler;

  @Autowired
  @Qualifier("authenticationFailHandler")
  private AuthenticationFailHandler failHandler;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //        auth
    //            .userDetailsService(jwtUserDetailsService)
    //            .passwordEncoder(passwordEncoderBean());
    // 自定义UserDetailsService,设置加密算法
    auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoderBean());
    // 不删除凭据，以便记住用户
    auth.eraseCredentials(false);
  }

  @Bean
  public PasswordEncoder passwordEncoderBean() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  @Qualifier("sessionRegistry")
  private SessionRegistry sessionRegistry;

  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  //  @Override
  //  public AuthenticationManager authenticationManagerBean() {
  //    AuthenticationManager authenticationManager = null;
  //    try {
  //      authenticationManager = super.authenticationManagerBean();
  //    } catch (Exception e) {
  //      e.printStackTrace();
  //    }
  //    return authenticationManager;
  //  }
  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        // we don't need CSRF because our token is invulnerable
        .csrf()
        .disable()
        .exceptionHandling()
        // 未登录就请求资源时，spring会交给AuthenticationEntryPoint处理。
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
        // session并发控制过滤器
        .addFilterAt(
            usernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterAt(
            new ConcurrentSessionFilter(sessionRegistry, sessionInformationExpiredStrategy()),
            ConcurrentSessionFilter.class)
        // don't create session
        .sessionManagement()
        // 因为我们使用了token，所以session要禁止掉创建和使用，不然会白白耗掉很多空间，SessionCreationPolicy设为STATELESS，即永不创建HttpSession并且不会使用HttpSession去获取SecurityContext。
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        // 允许对于网站静态资源的无授权访问
        .antMatchers(HttpMethod.GET, "/", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js","/**/*.png", "/**/*.jpg", "/**/*.jpeg",
            //swagger资源权限处理
            "/swagger-resources/**","/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
            "/configuration/security", "/swagger-ui.html**", "/webjars/**",
            "/user-service/v2/api-docs")
            .permitAll()
            // Un-secure H2 Database
            .antMatchers("/h2-console/**/**")
            .permitAll()
            .antMatchers("/auth/**","/deptdb1/**","/deptdb2/**","/order/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            // 把自定义的JwtAuthenticationFilter添加到UsernamePasswordAuthenticationFilter之前。
            .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)

            // disable page caching
            .headers()
            .frameOptions()
            .sameOrigin() // required to set for H2 else H2 Console will be blank.
            .cacheControl();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    // AuthenticationTokenFilter will ignore the below paths
    web.ignoring()
        .antMatchers(HttpMethod.POST, authenticationPath)
        .and()
        // allow anonymous resource requests.and()
        .ignoring()
        .antMatchers(
            HttpMethod.GET, "/", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js")
        // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in
        // production)
        .and()
        .ignoring()
        .antMatchers("/h2-console/**/**");
  }
  // SpringSecurity内置的session监听器
  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }

  private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter()
      throws Exception {
    UsernamePasswordAuthenticationFilter filter = new CuzUsernamePasswordAuthenticationFilter();
    filter.setPostOnly(true);
    filter.setAuthenticationManager(this.authenticationManager());
    filter.setUsernameParameter("name_key");
    filter.setPasswordParameter("pwd_key");
    filter.setRequiresAuthenticationRequestMatcher(
        new AntPathRequestMatcher("/checkLogin", "POST"));
    // 登陆失败之后，spring会跳到SimpleUrlAuthenticationFailureHandler
    filter.setAuthenticationFailureHandler(failHandler);
    // 登陆成功之后，spring会跳到AuthenticationSuccessHandler
    filter.setAuthenticationSuccessHandler(successHandler);
    // session并发控制,因为默认的并发控制方法是空方法.这里必须自己配置一个
    filter.setSessionAuthenticationStrategy(
        new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry));
    return filter;
  }
  /**
   * 功能描述:Code5 官方推荐加密算法
   *
   * @param:
   * @return:
   * @auther: zy
   * @date: 20190331 13:18
   */
  @Bean("passwordEncoder")
  public BCryptPasswordEncoder passwordEncoder() {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder;
  }
  /**
   * 功能描述: session失效跳转
   *
   * @param: []
   * @return: org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
   * @auther: zy
   * @date: 20190331 13:19
   */
  private SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
    return new SimpleRedirectSessionInformationExpiredStrategy("/login");
  }
  /** 投票器 */
  private AbstractAccessDecisionManager accessDecisionManager() {
    List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList();
    decisionVoters.add(new AuthenticatedVoter());
    decisionVoters.add(new RoleVoter()); // 角色投票器,默认前缀为ROLE_
    RoleVoter AuthVoter = new RoleVoter();
    AuthVoter.setRolePrefix("AUTH_"); // 特殊权限投票器,修改前缀为AUTH_
    decisionVoters.add(AuthVoter);
    decisionVoters.add(webExpressionVoter()); // 表达式投票器
    AbstractAccessDecisionManager accessDecisionManager = new AffirmativeBased(decisionVoters);
    return accessDecisionManager;
  }
  /**
   * 验证异常处理器
   *
   * @return
   */
  private SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler() {
    return new SimpleUrlAuthenticationFailureHandler("/getLoginError");
  }
  /**
   * 登录成功后跳转 如果需要根据不同的角色做不同的跳转处理,那么继承AuthenticationSuccessHandler重写方法
   *
   * @return
   */
  private SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler() {
    return new SimpleUrlAuthenticationSuccessHandler("/loginSuccess");
  }
  /**
   * 表达式控制器
   *
   * @return
   */
  private DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
    DefaultWebSecurityExpressionHandler webSecurityExpressionHandler =
        new DefaultWebSecurityExpressionHandler();
    return webSecurityExpressionHandler;
  }
  /**
   * 表达式投票器
   *
   * @return
   */
  private WebExpressionVoter webExpressionVoter() {
    WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
    webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());
    return webExpressionVoter;
  }

  private FilterSecurityInterceptor filterSecurityInterceptor(
      FilterInvocationSecurityMetadataSource securityMetadataSource,
      AccessDecisionManager accessDecisionManager,
      AuthenticationManager authenticationManager) {
    FilterSecurityInterceptor interceptor = new FilterSecurityInterceptor();
    interceptor.setSecurityMetadataSource(securityMetadataSource);
    interceptor.setAccessDecisionManager(accessDecisionManager);
    interceptor.setAuthenticationManager(authenticationManager);
    return interceptor;
  }
}
