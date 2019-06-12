package com.yl.payinterface.core;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.enums.Status;
import com.yl.payinterface.core.model.AliPayCollectionCode;
import com.yl.payinterface.core.service.AliPayCollectionCodeService;
import com.yl.payinterface.core.utils.CodeBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 收款文本导入数据库
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/21
 */
public class importCode extends BaseTest {

    @Autowired
    private AliPayCollectionCodeService aliPayCollectionCodeService;

    @Test
    public void importCode() {
        File file = new File("E:\\收款码汇总\\备注容错\\核对完成\\rongmeikj@yeah.net.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            String[] date;
            AliPayCollectionCode aliPayCollectionCode;
            while ((s = br.readLine()) != null) {
                date = s.split("----");
                aliPayCollectionCode = new AliPayCollectionCode();
                aliPayCollectionCode.setCode(CodeBuilder.build("ALP", "yyyyMMdd", 6));
                aliPayCollectionCode.setVersion(System.currentTimeMillis());
                aliPayCollectionCode.setCreateTime(new Date());
                aliPayCollectionCode.setUrl(date[3]);
                aliPayCollectionCode.setAliPayAcc(date[0]);
                aliPayCollectionCode.setAmount(Double.valueOf(date[1]));
                aliPayCollectionCode.setStatus(Status.NO);
                aliPayCollectionCode.setRemarks(date[2]);
                aliPayCollectionCodeService.save(aliPayCollectionCode);
                System.out.println(JsonUtils.toJsonString(aliPayCollectionCode));
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update() {
        List<AliPayCollectionCode> aliPayCollectionCodes = aliPayCollectionCodeService.queryCode("5");
        AliPayCollectionCode aliPayCollectionCode = aliPayCollectionCodes.get(0);
        aliPayCollectionCode.setCount(0);
        aliPayCollectionCodeService.update(aliPayCollectionCode);
    }

    public static void main(String[] args) {
        String path = "F:\\收款码汇总\\多游测试\\多游第二批";
        List<File> list = getFileSort(path);
        for (File file : list) {
            System.out.println(file.getName() + " : " + file.lastModified());
        }
    }

    public static ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
              System.out.println("文     件：" + tempList[i]);
              files.add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {
              System.out.println("文件夹：" + tempList[i]);
            }
        }
        return files;
    }

    public static String[] extractAmountMsg(String ptCasinoMsg) {
        String returnAmounts[] = new String[4];
        ptCasinoMsg = ptCasinoMsg.replace(" | ", " ");
        String[] amounts = ptCasinoMsg.split(" ");
        for (int i = 0; i < amounts.length; i++) {
            Pattern p = Pattern.compile("(\\d+\\.\\d+)");
            Matcher m = p.matcher(amounts[i]);
            if (m.find()) {
                returnAmounts[i] = m.group(1);
            } else {
                p = Pattern.compile("(\\d+)");
                m = p.matcher(amounts[i]);
                if (m.find()) {
                    returnAmounts[i] = m.group(1);
                }
            }
        }
        return returnAmounts;
    }

    /**
     * 获取目录下所有文件(按时间排序)
     *
     * @param path
     * @return
     */
    public static List<File> getFileSort(String path) {
        List<File> list = getFiles(path, new ArrayList<File>());
        if (list != null && list.size() > 0) {
            Collections.sort(list, new Comparator<File>() {
                public int compare(File file, File newFile) {
                    if (file.lastModified() < newFile.lastModified()) {
                        return 1;
                    } else if (file.lastModified() == newFile.lastModified()) {
                        return 0;
                    } else {
                        return -1;
                    }

                }
            });
        }
        return list;
    }

    /**
     *
     * 获取目录下所有文件
     *
     * @param realpath
     * @param files
     * @return
     */
    public static List<File> getFiles(String realpath, List<File> files) {
        File realFile = new File(realpath);
        if (realFile.isDirectory()) {
            File[] subfiles = realFile.listFiles();
            for (File file : subfiles) {
                if (file.isDirectory()) {
                    getFiles(file.getAbsolutePath(), files);
                } else {
                    files.add(file);
                }
            }
        }
        return files;
    }
}