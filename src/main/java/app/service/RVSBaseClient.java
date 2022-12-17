package app.service;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mks.api.CmdRunner;
import com.mks.api.Command;
import com.mks.api.IntegrationPoint;
import com.mks.api.IntegrationPointFactory;
import com.mks.api.Session;
import com.mks.api.response.APIException;
import com.mks.api.response.Response;
import com.mks.api.util.APIVersion;

import app.AppLogger;

/**
 * RVS 基础客户端组件
 * 
 */
public abstract class RVSBaseClient {

    private static Logger logger = AppLogger.getLogger(RVSBaseClient.class);

    /**
     * 全局唯一的 Session 对象
     * 
     */
    private static Session mksapi;

    public RVSBaseClient() {
        if(mksapi != null) return;
        try {
            IntegrationPoint ip = IntegrationPointFactory.getInstance().createLocalIntegrationPoint(APIVersion.API_4_16);
            ip.setAutoStartIntegrityClient(true);
            mksapi = ip.getCommonSession();
            mksapi.setDefaultHostname(System.getenv("MKSSI_HOST"));
            mksapi.setDefaultPort(Integer.valueOf(System.getenv("MKSSI_PORT")));
            mksapi.setDefaultUsername(System.getenv("MKSSI_USER"));
        } catch (APIException e) {
            logger.log(Level.SEVERE, "", e);
        }
    }

    /**
     * 提交 mksapi 命令
     * 
     * @param cmd 命令对象
     * @return 响应对象
     * @throws APIException
     */    
    protected Response _exec(Command cmd) throws APIException {
        CmdRunner runner = null;
        try {
            runner = mksapi.createCmdRunner();
            logger.log(Level.INFO, Arrays.toString(cmd.toStringArray()));
            Response resp = runner.execute(cmd);
            logger.log(Level.INFO, "resp.count = " + resp.getWorkItemListSize());
            return resp;
        } finally {
            try {
                runner.release();
            } catch (APIException e) {
            }
        }
    }
}
