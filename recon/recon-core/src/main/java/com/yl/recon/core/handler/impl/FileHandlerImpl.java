package com.yl.recon.core.handler.impl;

import com.yl.recon.core.enums.ReconBillType;
import com.yl.recon.core.handler.AbstractFileHandler;

import java.util.List;

/**
 * 文件处理
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public class FileHandlerImpl extends AbstractFileHandler {

    private ReconBillType reconBillType;

    public List<?> fileHandle() {
        recognitionFileType();
        switch (reconBillType) {
            case CSV:
                return new CsvFileHandlerImpl().loadFile(super.filePath).fileHandle();
            case TXT:
                return new TextFileHandlerImpl().loadFile(super.filePath).fileHandle();
            case EXCLE:
                return new ExcleFileHandlerImpl().loadFile(super.filePath).fileHandle();
            default:
                throw new RuntimeException("reconBillType isNot support");
        }
    }

    @Override
    public List<?> fileHandle(String separator) {
        recognitionFileType();
        switch (reconBillType) {
            case CSV:
                return new CsvFileHandlerImpl().loadFile(super.filePath).fileHandle(separator);
            case TXT:
                return new TextFileHandlerImpl().loadFile(super.filePath).fileHandle(separator);
            case EXCLE:
                return new ExcleFileHandlerImpl().loadFile(super.filePath).fileHandle(separator);
            default:
                throw new RuntimeException("reconBillType isNot support");
        }
    }

    private void recognitionFileType() {
        try {
            reconBillType = ReconBillType.valueOf(super.filePath.substring(filePath.indexOf(".") + 1, filePath.length()).toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("reconBillType is error");
        }
    }
}
