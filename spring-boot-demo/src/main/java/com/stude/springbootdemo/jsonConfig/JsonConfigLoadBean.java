package com.stude.springbootdemo.jsonConfig;

import com.alibaba.fastjson.JSON;
import com.stude.springbootdemo.xlsx.monitor.FileLoader;
import com.stude.springbootdemo.xlsx.monitor.FileMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2018/12/6.
 *
 * @author grayCat
 * @since 1.0
 */
public abstract class JsonConfigLoadBean<T> implements ApplicationRunner, FileLoader {

    Logger log = LoggerFactory.getLogger(getClass());

    @Value("${user.jsondir}")
    private String cinfig;

    @Value("${user.monitor}")
    private String monitorPath;

    private String myPath;

    private Map<String, String> allPath = new HashMap<>();

    private static List<JsonConfigLoadBean> allCheck = new ArrayList<>();

    @Autowired
    private FileMonitor monitor;

    protected abstract Class<T> getTClass();

    protected abstract void getJsonConfig(T club);

    public void jsonConfigBean(String cinfig) {
        try {
            File file = new File(cinfig);
            //使用io读出数据
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String str = null;
            StringBuilder all = new StringBuilder();
            while ((str = br.readLine()) != null) {
                all.append(str);
            }
            if (all != null) {
                //采用阿里的fastjson解析这个json
                HashMap params = JSON.parseObject(all.toString(), HashMap.class);
                T bean = JSON.parseObject(JSON.toJSONString(params), getTClass());
                getJsonConfig(bean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addPath() {
        String[] allPath = cinfig.split(",");
        for (String path : allPath) {
            String[] counts = path.split("&");
            if (counts.length >= 2) {
                this.allPath.put(counts[0], counts[1]);
            }
        }
        String name = getTClass().getSimpleName();
        if (this.allPath.get(name) != null) {
            myPath = this.allPath.get(name);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("begin to load jsonConfig ...");
        addPath();
        jsonConfigBean(myPath);
        monitor.addDirectoryObserver(monitorPath, this);
        allCheck.add(this);
    }

    @Override
    public void load(File file, boolean isNew) {
        try {
            log.info("on file change filename = {},isNew={}", file.getName(), isNew);
            for (JsonConfigLoadBean configLoadBean : allCheck) {
                configLoadBean.loadConfig();
            }
        } catch (Exception e) {
            log.warn("on file change err,filename={},isNew={}", file.getName(), isNew);
        }
    }

    private void loadConfig() {
        jsonConfigBean(myPath);
    }
}
