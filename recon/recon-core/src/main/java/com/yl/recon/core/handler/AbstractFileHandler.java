package com.yl.recon.core.handler;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象文件处理器
 *
 * @author AnLin
 * @since 2017/6/22
 */
public abstract class AbstractFileHandler {

	private static final Logger logger = LoggerFactory.getLogger(AbstractFileHandler.class);
	
    /**
     * 文件
     */
    protected File file;

    /**
     * 文件地址
     */
    protected String filePath;

    /**
     * 参数分隔符
     */
    protected String separator;

    /**
     * 文件加载
     * @param filePath
     * @return AbstractFileHandler
     */
    public AbstractFileHandler loadFile(String filePath){
        this.filePath = filePath;
        file = new File(filePath);
        if(!file.isFile() && !file.exists()){
        	filePath = null;
        	file = null;
        	logger.error("file is not exists!");
        }
        return this;
    }

    /**
     * 文件处理
     * 默认分割符 1、"," 2、" " 3、"|"
     * @return List<?>
     */
    public abstract List<?> fileHandle();

    /**
     * 文件处理
     * @param separator 内容分隔符
     * @return List<?>
     */
    public abstract List<?> fileHandle(String separator);

	public File getFile() {
		return file;
	}

	public String getFilePath() {
		return filePath;
	}

}
