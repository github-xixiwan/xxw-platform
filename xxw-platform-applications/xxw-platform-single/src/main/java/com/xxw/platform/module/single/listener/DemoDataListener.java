package com.xxw.platform.module.single.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.xxw.platform.module.single.model.entity.DemoData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
@Slf4j
public class DemoDataListener implements ReadListener<DemoData> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<DemoData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
//        if (cachedDataList.size() >= BATCH_COUNT) {
//            saveData();
//            // 存储完成清理 list
//            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
//        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        BufferedWriter bw = null;
        FileWriter fr = null;
        try {
            //将写入转化为流的形式
            fr = new FileWriter("C:\\Users\\liaoxiting\\Desktop\\txt\\1.txt");
            bw = new BufferedWriter(fr);
            for (DemoData l : cachedDataList) {
                String waybillNo = l.getWaybillNo();
                String firstDispatchCode = "";
                String secondDispatchCode = "";
                String thirdlyDispatchCode = "";
                String terminalDispatchCode = l.getTerminalDispatchCode();
                if (StringUtils.isNotBlank(terminalDispatchCode)) {
                    String[] split = terminalDispatchCode.split(",");
                    if (split.length == 1) {
                        firstDispatchCode = split[0];
                    }
                    if (split.length == 2) {
                        firstDispatchCode = split[0];
                        secondDispatchCode = split[1];
                    }
                    if (split.length == 3) {
                        firstDispatchCode = split[0];
                        secondDispatchCode = split[1];
                        thirdlyDispatchCode = split[2];
                    }
                }
                String sql = "insert into t_ass_sorting_segmented (code, waybill_no, terminal_dispatch_code, first_dispatch_code, second_dispatch_code, thirdly_dispatch_code, customer_code, interceptor, order_type) " +
                        "values ('457101','" + waybillNo + "','" + terminalDispatchCode + "','" + firstDispatchCode + "','" + secondDispatchCode + "','" + thirdlyDispatchCode + "','',2,1);";
                bw.write(sql);
                bw.newLine();  //换行用
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("存储数据库成功！");
    }
}