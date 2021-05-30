package com.chunhe.custom.framework.security;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

public class AdConnect {
    public AdConnect() {

    }

    private String host,url,adminName,adminPassword;
    private LdapContext ctx = null;

    public void initLdap(String host,String post,String username,String password) {

        Hashtable<String, String> HashEnv = new Hashtable<String, String>();
        HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别(none,simple,strong)
        HashEnv.put(Context.SECURITY_PRINCIPAL, username); //AD的用户名
        HashEnv.put(Context.SECURITY_CREDENTIALS, password); //AD的密码
        HashEnv.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory"); // LDAP工厂类
        HashEnv.put("com.sun.jndi.ldap.connect.timeout", "3000");//连接超时设置为3秒
        HashEnv.put(Context.PROVIDER_URL, "ldap://" + host + ":" + post);// 默认端口389
        String company = "";
        try {
            ctx = new InitialLdapContext(HashEnv, null);
            System.out.println("身份验证成功!");
        } catch (AuthenticationException e) {
            System.out.println("身份验证失败!");
            e.printStackTrace();
        } catch (javax.naming.CommunicationException e) {
            System.out.println("AD域连接失败!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("身份验证未知异常!");
            e.printStackTrace();
        }
    }


    /**
     * 关闭ldap
     */
    public void closeLdap(){
        if(this.ctx == null){
            return;
        }
        try {
            this.ctx.close();
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getLdapInfo(String type ,String filter ,String name) {
        String userName = name; // 用户名称
        if (userName == null) {
            userName = "";
        }
        String company = "";
        String result = "";
        try {
            //搜索控制器
            SearchControls searchCtls = new SearchControls();
            //设置搜索范围
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            //LDAP搜索过滤器类
            String searchFilter = "(&(objectClass="+type+")("+filter+"=" + name + "))";
            //域节点
            String searchBase = "ou=wasu,dc=wasutv,dc=com";
            //String searchBase = "ou=信息技术部,ou=办公室,ou=传媒集团,ou=wasu,dc=wasutv,dc=com";
            //返回属性(不设置，返回所有)
            //String returnedAtts[] = {"cn","title","st","street","streetaddress"};
            //searchCtls.setReturningAttributes(returnedAtts);
            if(ctx == null){
                return "ctx==null";
            }
            NamingEnumeration answer = ctx.search(searchBase, searchFilter,searchCtls);// Search for objects using the filter
            // 初始化搜索结果数为0
            int totalResults = 0;// Specify the attributes to return
            int rows = 0;
            while (answer.hasMoreElements()) {// 遍历结果集
                SearchResult sr = (SearchResult) answer.next();// 得到符合搜索条件的DN
                ++rows;
                String dn = sr.getName();
                System.out.println(dn);
                Attributes Attrs = sr.getAttributes();// 得到符合条件的属性集
                if (Attrs != null) {
                    try {
                        for (NamingEnumeration ne = Attrs.getAll(); ne.hasMore();) {
                            Attribute Attr = (Attribute) ne.next();// 得到下一个属性
                            System.out.print(" AttributeID(属性名)："+ Attr.getID().toString());
                            // 读取属性值
                            for (NamingEnumeration e = Attr.getAll(); e.hasMore(); totalResults++) {
                                company = e.next().toString();
                                System.out.println("    AttributeValues(属性值)："+ company);
                            }
                        }
                        System.out.println("-------------------------");
                    } catch (NamingException e) {
                        System.err.println("Throw Exception : " + e);
                    }
                }// if
            }// while
            System.out.println("************************************************");
            System.out.println("Number: " + totalResults);
            System.out.println("总共用户数："+rows);
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Throw Exception : " + e);
        }
        return result;
    }

    public static void main(String[] args) {
        AdConnect ad = new AdConnect();
        ad.initLdap("10.0.102.20", "389", "baicai", "wosai!@#123");
        ad.getLdapInfo("*","cn","baicai");
//        AdConnect.connect("10.0.102.20", "389", "qiumy", "abc!@#123");
//        AdConnect.connect("10.0.102.20", "389", "用户test", "wosai!@#123");
        ad.closeLdap();
    }


}
