import cn.zkdcloud.component.qrcode.QrType;
import cn.zkdcloud.core.QrcodeComponent;
import org.junit.Before;
import org.junit.Test;

/**
 * 二维码
 * @author zk
 * @version 2017/9/4
 */
public class QrTest {
    private QrcodeComponent qrcodeComponent;

    @Before
    public void befor(){
        qrcodeComponent = QrcodeComponent.getInstance();
    }

    @Test
    public void createQr(){
        System.out.println(qrcodeComponent.createStrQr("abc",QrType.QR_STR_SCENE));
    }

}
