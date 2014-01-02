package com;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: hpf
 * Date: 14-1-2
 * Time: 上午11:08
 * To change this template use File | Settings | File Templates.
 */
public class Test {


    RandomAccessFile raf ;
    private   static int  encrpyStrLen = 2048;// "FiprintStackTraceleInpubodytStFileNotFoundExceptionreammesyousourceFile";
    private byte buffer [] ;
    private String encrpyFileName ;

    public static void main(String args[]) {

         Test t = new Test();
         t.init("/Users/hpf/Desktop/tmp/encrpy/start1.mp4");
         t.ok();

    }


    private void init(String  encrpyFileName) {
        buffer = new byte[encrpyStrLen];
        this.encrpyFileName = encrpyFileName;
        File sourceFile = new File(encrpyFileName);
        if(!sourceFile.exists()) {
            System.out.println("source file :"+encrpyFileName+" not exists .");
            return ;
        }
        System.out.println("inited." );

    }

    //加密解密函数
    private void ok(){
        long t1 = System.currentTimeMillis();
        try {
            //备份之前的文件，
            bak();
            raf = new RandomAccessFile(encrpyFileName,"rw");
            raf.read(buffer);//encrpyStrLen字节
            for (int i = 0; i < buffer.length; i++)
                buffer[i] = (byte) (~buffer[i]);// 将缓冲区中的内容按位取反
            raf.seek(0);// 将文件指针定位到文件头部
            raf.write(buffer);// 将缓冲区中的内容写入到文件中
            raf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        long t2 = System.currentTimeMillis();
        System.out.println("source file :"+encrpyFileName+" encrpy finished ,time :"+(t2-t1)+" ms.");

    }


    private static boolean  del(String fileName){
        boolean result = false;
        File f = new File(fileName);
        if (f.exists()){
            try{
                result = f.delete();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            result = true;
        }
        return result;
    }


    private  void bak() {
        long startTime = System.currentTimeMillis();
        byte buffer [] = new byte[1024];
        String bakFilenName = encrpyFileName+".bak";
        try {
            FileInputStream fin = new FileInputStream(new File(encrpyFileName));
            FileOutputStream fout = new FileOutputStream(bakFilenName);
            while (true) {
                int ins = fin.read(buffer);
                if (ins == -1) {
                    fin.close();
                    fout.flush();
                    fout.close();
                    break;
                } else{
                    fout.write(buffer, 0, ins);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("bak file cost time  : " + (endTime - startTime)+" ms,path :"+bakFilenName);
    }


}

