package com.tul.manage.config;

import cn.hutool.core.codec.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 服务器启动成功时执行
 * @author: znegyu
 * @create: 2020-11-23 11:14
 **/
@Component
@Slf4j
public class WebRunExecute implements CommandLineRunner {

    @Value("${spring.profiles.active}")
    public String active;

    @Value("${server.port}")
    public Integer port;

    @Value("${server.servlet.context-path}")
    public String contextPath;

    @Value("${project.describe.name}")
    public String projectName;


    @Override
    public void run(String... args) {
        printInitMessage(projectName,active,port.toString(),contextPath);
    }

    private void printInitMessage(String projectName,String active,String port,String contextPath){
        String address="";
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            address+=localHost.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String noBug=  "                   \033[32;1m_ooOoo_"+"\033[0m\n"+
                "                  \033[32;1mo8888888o"+"\033[0m\n"+
                "                  \033[32;1m88\" . \"88"+"\033[0m\n"+
                "                  \033[32;1m(| -_- |)"+"\033[0m\n"+
                "                  \033[32;1mO\\  =  /O"+"\033[0m\n"+
                "               \033[32;1m____/`---'\\____\033[0m"+"\n"+
                "             .\033[32;1m'  \\\\|     |//  `.\033[0m"+"\n"+
                "            \033[32;1m/  \\\\|||  :  |||//  \\\033[0m"+"\n"+
                "           \033[32;1m/  _||||| -:- |||||-  \\\033[0m"+"\n"+
                "           \033[32;1m|   | \\\\\\  -  /// |   |\033[0m"+"\n"+
                "           \033[32;1m| \\_|  ''\\---/''  |   |"+"\033[0m\n"+
                "           \033[32;1m\\  .-\\__  `-`  ___/-. /"+"\033[0m\n"+
                "         \033[32;1m___`. .'  /--.--\\  `. . __"+"\033[0m\n"+
                "      \033[32;1m.\"\" '<  `.___\\_<|>_/___.'  >'\"\"."+"\033[0m"+"                  欢迎使用        \033[36;2m"+ projectName +"\033[0m\n"+
                "     \033[32;1m| | :  `- \\`.;`\\ _ /`;.`/ - ` : | |"+"\033[0m"+"                启动成功        "+new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()) +"\n"+
                "     \033[32;1m\\  \\ `-.   \\_ __\\ /__ _/   .-` /  /"+"\033[0m"+"                运行环境        "+active+ "  " + "【"+  System.getProperty("os.name").toLowerCase()+"】【"+address+"】"+"\n"+
                "\033[32;1m======`-.____`-.___\\_____/___.-`____.-'======"+"\033[0m"+"           后端地址        http://localhost:" + port + contextPath + "/doc.html\n"+
                "                   \033[32;1m`=---='"+"\033[0m"+"                              Druid地址      http://localhost:" + port + contextPath + "/druid    账号:admin  密码:123456\n"+
                "\033[32;1m^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+"\033[0m\n"+
                "\033[32;1m佛祖保佑                               永无BUG\033[0m";
        System.out.println(noBug);
    }
}
