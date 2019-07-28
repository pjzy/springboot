package com.zy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zy.common.FastjsonFilter;
import com.zy.common.Jurisdiction;
import com.zy.common.Page;
import com.zy.common.PageData;
import com.zy.common.enums.DataStatus;
import com.zy.common.utils.DateUtils;
import com.zy.common.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础Controller,其他Controller继承此Controller来获得writeJson和ActionSupport的功能
 *
 * <p>基本的CRUD已实现，子类继承BaseController的时候，提供setService方法即可
 *
 * @author yang
 */
@Controller
public class BaseController {
  protected Logger logger = LoggerFactory.getLogger(this.getClass());
  /** 保存成功 */
  protected static final String SAVE_SUCCESS = "\u4FDD\u5B58\u6210\u529F";
  /** 修改成功 */
  protected static final String UPDATE_SUCCESS = "\u4FEE\u6539\u6210\u529F";
  /** 刪除成功 */
  protected static final String DELETE_SUCCESS = "\u522A\u9664\u6210\u529F";
  /** 保存失败 */
  protected static final String SAVE_FAILED = "\u4FDD\u5B58\u5931\u8D25";
  /** 修改失败 */
  protected static final String UPDATE_FAILED = "\u4FEE\u6539\u5931\u8D25";
  /** 刪除失败 */
  protected static final String DELETE_FAILED = "\u522A\u9664\u5931\u8D25";

  static {
  }
  /**
   * 将对象转换成JSON字符串，并响应回前台
   *
   * @param object
   * @param includesProperties 需要转换的属性
   * @param excludesProperties 不需要转换的属性
   */
  public void writeJsonByFilter(
      Object object,
      HttpServletResponse response,
      Map<Class<?>, String[]> includesProperties,
      Map<Class<?>, String[]> excludesProperties) {
    try {
      FastjsonFilter filter = new FastjsonFilter(); // excludes优先于includes
      if (excludesProperties != null && !excludesProperties.isEmpty()) {
        filter.getExcludes().putAll(excludesProperties);
      }
      if (includesProperties != null && !includesProperties.isEmpty()) {
        filter.getIncludes().putAll(includesProperties);
      }
      // logger.info("对象转JSON：要排除的属性[" + excludesProperties + "]要包含的属性[" +
      // includesProperties + "]");
      String json;
      String User_Agent = getRequest().getHeader("User-Agent");
      if (User_Agent.indexOf("MSIE 6") > -1) {
        // 使用SerializerFeature.BrowserCompatible特性会把所有的中文都会序列化为\\uXXXX这种格式，字节数会多一些，但是能兼容IE6
        json =
            JSON.toJSONString(
                object,
                filter,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.BrowserCompatible);
      } else {
        // 使用SerializerFeature.WriteDateUseDateFormat特性来序列化日期格式的类型为yyyy-MM-dd hh24:mi:ss
        // 使用SerializerFeature.DisableCircularReferenceDetect特性关闭引用检测和生成
        json =
            JSON.toJSONString(
                object,
                filter,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.DisableCircularReferenceDetect);
      }
      // logger.info("转换后的JSON字符串：" + json);
      response.setContentType("text/html;charset=utf-8");
      response.getWriter().write(json);
      response.getWriter().flush();
      response.getWriter().close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Map<String, String> setUserType(Map<String, String> QX) {
//    Session session = Jurisdiction.getSession();
//    JwtUser user = (JwtUser) session.getAttribute(Const.SESSION_USER);
    return QX;
  }

  public Long getUserId() {
    return Jurisdiction.getJwtUser().getId();
  }

  public HttpServletResponse getResponse() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
  }

  /**
   * 将对象转换成JSON字符串，并响应回前台
   *
   * @param object
   * @throws IOException
   */
  public void writeJson(Object object, HttpServletResponse response) {
    if (object instanceof Page) {
      Page page = (Page) object;
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("rows", page.getRows());
      map.put("total", page.getTotal());
      object = map;
    }
    writeJsonByFilter(object, response, null, null);
  }

  /**
   * @Title: parseArray @Description: 将json字符串转换为指定的Java对象集合
   *
   * @param params
   * @param clazz
   * @return
   * @return Map<String,List<T>>返回类型
   * @throws
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public <T> Map<DataStatus, List<T>> parseArray(Map<String, Object> params, Class clazz) {
    Map<DataStatus, List<T>> result = new HashMap<>();
    if (params.containsKey("insert")) {
      List<T> insert = (List<T>) JSON.parseArray(params.get("insert").toString(), clazz);
      result.put(DataStatus.INSERT, insert);
    }
    if (params.containsKey("update")) {
      List<T> update = (List<T>) JSON.parseArray(params.get("update").toString(), clazz);
      result.put(DataStatus.MODIFIED, update);
    }
    if (params.containsKey("delete")) {
      List<T> delete = (List<T>) JSON.parseArray(params.get("delete").toString(), clazz);
      result.put(DataStatus.DELETE, delete);
    }
    return result;
  }

  /**
   * @Title: convertParamsType @Description: 转换参数类型
   *
   * @param params
   * @param clazz
   * @return Map<String,Object>返回类型
   */
  protected Map<String, Object> convertParamsType(Map<String, Object> params, Class clazz) {
    Field[] fields = clazz.getDeclaredFields();
    String fn;
    String fnLT;
    String fnLE;
    String fnGT;
    String fnGE;
    for (Field field : fields) {
      if (!field.getType().getName().equals("java.lang.Long")
          && !field.getType().getName().equals("java.lang.Double")
          && !field.getType().getName().equals("java.lang.Float")
          && !field.getType().getName().equals("java.lang.Integer")
          && !field.getType().getName().equals("java.util.Boolean")
          && !field.getType().getName().equals("java.util.Date")
          && !field.getType().getName().equals("java.math.BigDecimal")) {
        continue;
      }
      fn = field.getName();
      fnLT = fn + "LT";
      fnLE = fn + "LE";
      fnGT = fn + "GT";
      fnGE = fn + "GE";
      convertTypeByFiled(params, fn, field);
      convertTypeByFiled(params, fnLT, field);
      convertTypeByFiled(params, fnLE, field);
      convertTypeByFiled(params, fnGT, field);
      convertTypeByFiled(params, fnGE, field);
    }
    return params;
  }

  private void convertTypeByFiled(Map<String, Object> params, String fn, Field field) {
    try {
      if (params.containsKey(fn) && params.get(fn) != null && !params.get(fn).equals("")) {
        if (field.getType().getName().equals("java.lang.Long")) {
          params.put(fn, Long.valueOf(params.get(fn).toString()));
        } else if (field.getType().getName().equals("java.lang.Double")) {
          params.put(fn, Double.valueOf(params.get(fn).toString()));
        } else if (field.getType().getName().equals("java.lang.Float")) {
          params.put(fn, Float.valueOf(params.get(fn).toString()));
        } else if (field.getType().getName().equals("java.lang.Integer")) {
          params.put(fn, Integer.valueOf(params.get(fn).toString()));
        } else if (field.getType().getName().equals("java.util.Boolean")) {
          params.put(fn, Boolean.valueOf(params.get(fn).toString()));
        } else if (field.getType().getName().equals("java.math.BigDecimal")) {
          params.put(fn, new BigDecimal(params.get(fn).toString()));
        } else if (field.getType().getName().equals("java.util.Date")) {
          params.put(fn, DateUtils.formatStringToDate(params.get(fn).toString()));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 得到ModelAndView
   *
   * @return
   */
  public ModelAndView getModelAndView() {
    return new ModelAndView();
  }

  /**
   * 得到request对象
   *
   * @return
   */
  public HttpServletRequest getRequest() {
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    return request;
  }

  /**
   * 得到32位的uuid
   *
   * @return
   */
  public String get32UUID() {
    return UuidUtil.get32UUID();
  }

  /**
   * 得到分页列表的信息
   *
   * @return
   */
  public Page getPage() {
    return new Page();
  }

  public static void logBefore(Logger logger, String interfaceName) {
    logger.info("");
    logger.info("start");
    logger.info(interfaceName);
  }

  public static void logAfter(Logger logger) {
    logger.info("end");
    logger.info("");
  }
  /**
   * new PageData对象
   *
   * @return
   */
  public PageData getPageData() {
    return new PageData(this.getRequest());
  }
}
