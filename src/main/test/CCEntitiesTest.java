import com.alibaba.fastjson.JSON;
import com.bsnbase.sdk.client.fabric.service.TransactionService;
import com.bsnbase.sdk.entity.config.Config;
import com.bsnbase.sdk.entity.req.fabric.ReqKeyEscrow;
import com.bsnbase.sdk.util.Log;
import com.bsnbase.sdk.util.exception.GlobalException;
import com.chaincodeEntities.Application;
import com.chaincodeEntities.Business;
import com.chaincodeEntities.Profile;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CCEntitiesTest {

    public static String chaincodeID = "cc_app0001202006081111440843077_00";
    public void initConfig() throws IOException {
        Config config = new Config();
        config.setAppCode("app0001202006081111440843077");
        config.setUserCode("USER0001202006050930126612022");
        config.setApi("https://nanjinode.bsngate.com:17602");
        config.setCert("certs/bsn_gateway_https.crt");
        config.setPrk("certs/nanji/userAppCert/private_key.pem");
        config.setPuk("certs/nanji/gatewayCert/gateway_public_cert_secp256r1.pem");
        config.setMspDir("D:/test");
        config.initConfig(config);
    }



    @Test
    public void testEntitiesToJson(){


    }

    @Test
    public void reqCreateBusinessActivity() {
        try {
            initConfig();
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
        ReqKeyEscrow reqkey = new ReqKeyEscrow();
//        String[] args = {"test"};
        Business business = new Business();
        business.setUid("0001");
        business.setBid("test01");
        business.setMenu("test01");
        business.setActivity("actest01");
        business.setFunds("100");
        business.setCreate_time("testdate");
        String[] args = {JSON.toJSONString(business)};
        reqkey.setArgs(args);
        reqkey.setFuncName("createBusinessActivity");
        reqkey.setChainCode("cc_app0001202006081111440843077_00");
//        reqkey.setUserName("test21");
        reqkey.setTransientData(null);
        try {
            TransactionService.reqChainCode(reqkey);
        } catch(GlobalException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void reqGetBusinessActivityInfoByUID() {
        try {
            initConfig();
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
        ReqKeyEscrow reqkey = new ReqKeyEscrow();
//        String[] args = {"test"};
        Business business = new Business();
        business.setUid("0001");
        String[] args = {JSON.toJSONString(business)};
        reqkey.setArgs(args);
        reqkey.setFuncName("getBusinessActivityInfoByUID");
        reqkey.setChainCode("cc_app0001202006081111440843077_00");
//        reqkey.setUserName("test21");
        reqkey.setTransientData(null);
        try {
            TransactionService.reqChainCode(reqkey);
        } catch(GlobalException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void reqCreateProfile() {
        try {
            initConfig();
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
        ReqKeyEscrow reqkey = new ReqKeyEscrow();
//        String[] args = {"test"};
        Profile profile = new Profile();
        profile.setId("0001");
        profile.setOpenid("test01");
        profile.setIdentity_card("test01");
        profile.setAction("actest01");
        profile.setName("100");
        profile.setWx_card("testdate");
        profile.setWx_card_num("t");
        String[] args = {JSON.toJSONString(profile)};
        reqkey.setArgs(args);
        reqkey.setFuncName("createProfile");
        reqkey.setChainCode("cc_app0001202006081111440843077_00");
//        reqkey.setUserName("test21");
        reqkey.setTransientData(null);
        try {
            TransactionService.reqChainCode(reqkey);
        } catch(GlobalException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void reqGetProfileByID() {
        try {
            initConfig();
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
        ReqKeyEscrow reqkey = new ReqKeyEscrow();
//        String[] args = {"test"};
        Profile profile = new Profile();
        profile.setId("0001");
        String[] args = {JSON.toJSONString(profile)};
        reqkey.setArgs(args);
        reqkey.setFuncName("getProfileByID");
        reqkey.setChainCode("cc_app0001202006081111440843077_00");
//        reqkey.setUserName("test21");
        reqkey.setTransientData(null);
        try {
            TransactionService.reqChainCode(reqkey);
        } catch(GlobalException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void reqApply() {
        try {
            initConfig();
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
        ReqKeyEscrow reqkey = new ReqKeyEscrow();
//        String[] args = {"test"};
        Application application = new Application();
        application.setId("applicationtest01");
        application.setAp_id("test01");
        application.setPid("appid01");
        application.setPolicy("policy01");
        application.setStatus("success");

        String[] args = {JSON.toJSONString(application)};
        reqkey.setArgs(args);
        reqkey.setFuncName("apply");
        reqkey.setChainCode("cc_app0001202006081111440843077_00");
//        reqkey.setUserName("test21");
        reqkey.setTransientData(null);
        try {
            TransactionService.reqChainCode(reqkey);
        } catch(GlobalException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void reqGetApplicationInfo() {
        try {
            initConfig();
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
        ReqKeyEscrow reqkey = new ReqKeyEscrow();
//        String[] args = {"test"};
        Application application = new Application();
        application.setId("applicationtest01");
        String[] args = {JSON.toJSONString(application)};
        reqkey.setArgs(args);
        reqkey.setFuncName("getApplicationInfo");
        reqkey.setChainCode("cc_app0001202006081111440843077_00");
//        reqkey.setUserName("test21");
        reqkey.setTransientData(null);
        try {
            TransactionService.reqChainCode(reqkey);
        } catch(GlobalException | IOException e) {
            e.printStackTrace();
        }
    }

}
