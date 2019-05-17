package com.stude.springbootdemo.xlsx.monitor;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 * Created on 2018/12/17.
 *
 * @author grayCat
 * @since 1.0
 */
public class FileAlterationListen extends FileAlterationListenerAdaptor {

    public static String Reportname = "gsqxjb_risk_zyt_road";

    public static String FileLocaltion = File.separator + "static" + File.separator + "pdf" + File.separator;

    public File DirContext;

    public FileAlterationListen(File dirContext) {
        super();
        DirContext = dirContext;
    }

    //文件夹创建
    @Override
    public void onDirectoryCreate(File directory) {
        System.out.println(directory.getName() + "  |  文件夹被创建" + "  |  路径为：" + directory.getPath());
    }

    //文件夹改变
    @Override
    public void onDirectoryChange(File directory) {
        System.out.println(directory.getName() + "  |  文件夹被改变" + "  |   路径为：" + directory.getPath());
    }

    //文件夹删除
    @Override
    public void onDirectoryDelete(File directory) {
        System.out.println(directory.getName() + "  |  文件夹被删除" + "  |   路径为：" + directory.getPath());
    }

    //文件创建
    @Override
    public void onFileCreate(File file) {
        super.onFileCreate(file);
        System.out.println(file.getName() + "  |  文件被创建" + "  |   路径为：" + file.getPath());
        traverseFolder2(DirContext.getPath(), file.getName());
    }

    //文件夹改变
    @Override
    public void onFileChange(File file) {
        super.onFileChange(file);
        System.out.println(file.getName() + "   |   文件被修改" + "  |   路径为：" + file.getPath());
        traverseFolder2(DirContext.getPath(), file.getName());
    }

    //文件删除
    @Override
    public void onFileDelete(File file) {
        super.onFileDelete(file);
        System.out.println(file.getName() + " 文件被删除" + "    路径为：" + file.getPath());
    }

    public void traverseFolder2(String path, String fileName) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        traverseFolder2(file2.getAbsolutePath(), fileName);
                    } else {
                        if (fileName.equals(file2.getName()) && file2.getName().contains(Reportname) && file2.getName().contains(".doc")) {
                            String name = file2.getName().substring(0, file2.getName().length() - 4);
                            // 得到静态资源的相对地址
                            String Apath = FileAlterationListen.class.getClassLoader().getResource("").getPath().split("WEB-INF")[0].replaceAll("/", "\\\\"), subPath = Apath.substring(1, Apath.length());
                            File outFile = new File(subPath + FileLocaltion + name + ".pdf");
//                            WordToPdf(file2, outFile);
                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    public static void main(String[] args) {
        File dir = new File("D://监听/DSC");
        FileAlterationMonitor monitor = new FileAlterationMonitor();


        IOFileFilter filter = FileFilterUtils.or(FileFilterUtils.directoryFileFilter(), FileFilterUtils.fileFileFilter());

        FileAlterationObserver observer = new FileAlterationObserver(dir, filter);
        observer.addListener(new FileAlterationListen(dir));

        monitor.addObserver(observer);
        try {
            //开始监听
            monitor.start();
            System.out.println("文件监听……");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
