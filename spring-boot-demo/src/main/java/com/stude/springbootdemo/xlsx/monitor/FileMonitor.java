package com.stude.springbootdemo.xlsx.monitor;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/6/2.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class FileMonitor extends FileAlterationListenerAdaptor implements CommandLineRunner {
    private Logger log = LoggerFactory.getLogger(getClass());

    private FileAlterationMonitor monitor = new FileAlterationMonitor();

    private Map<String, FileLoader> fileLoaders = new HashMap<>();

    public Map<String, FileAlterationObserver> fileObserver = new HashMap<>();


    public void start() {
        log.info("文件监视器启动");
        try {
            monitor.start();
        } catch (Exception e) {
            log.warn("文件监听器器动失败...", e);
        }
    }

    /**
     * 添加文件监听器
     *
     * @param fileName
     * @param fileLoader
     */
    public void addFileObserver(String fileName, FileLoader fileLoader, boolean load) {
        log.info("添加文件监听,fileName={},load={}", fileName, load);
        if (fileName == null || fileName.isEmpty()) {
            log.warn("添加文件监听错误，参数不能为空");
            return;
        }
        String[] fn = fileName.split("/");
        if (fn.length != 2) {
            log.warn("添加文件监听错误，参数不能为空，file=" + fileName);
            return;
        }
        String dirName = fn[0];
        String fName = fn[1];
        addDirectoryObserver(dirName, null);
        if (fileLoader != null) {
            fileLoaders.put(fName, fileLoader);
        }
        if (load) {
            onFileChange(new File(fileName));
        }
    }

    /**
     * 添加文件监听器
     *
     * @param dirName
     * @param fileLoader
     */
    public void addDirectoryObserver(String dirName, FileLoader fileLoader) {
        if (!fileObserver.containsKey(dirName)) {
            log.info("添加目录监听,dirName={},load={}", dirName);
            File file = new File(dirName);
            FileAlterationObserver observer = new FileAlterationObserver(file);
            observer.addListener(this);
            try {
                observer.initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
            monitor.addObserver(observer);
            fileObserver.putIfAbsent(dirName, observer);
            if (fileLoader != null) {
                fileLoaders.putIfAbsent(dirName, fileLoader);
            }
        }
    }


    @Override
    public void onFileChange(File file) {
        try {
            String fileName = file.getName();
            log.info("============监听到文件改变，fileName=" + fileName);
            FileLoader fileListener = fileLoaders.get(fileName);
            if (fileListener != null) {
                fileListener.load(file, false);
            } else {
                String path = file.getPath();
                fileLoaders.forEach((key, value) -> {
                    if (path.contains(key)) {
                        value.load(file, false);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFileCreate(File file) {
        try {
            String fileName = file.getName();
            if (fileName.startsWith("~")) {
                return;
            }
            log.info("============监听到文件创建，fileName=" + fileName);
            FileLoader fileListener = fileLoaders.get(fileName);
            if (fileListener != null) {
                fileListener.load(file, true);
            } else {
                String path = file.getPath();
                fileLoaders.forEach((key, value) -> {
                    if (path.contains(key)) {
                        value.load(file, false);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run(String... args) throws Exception {
        start();
    }
}
