package NetworkCamera.SDK_DaHua;

import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Union;
import com.sun.jna.Memory;
import com.sun.jna.examples.win32.W32API.HWND;

/**
 * SDK JNA�ӿڷ�װ
 */

public interface NetSDKLib extends StdCallLibrary{
	NetSDKLib COMMON_INSTANCE = (NetSDKLib) Native.loadLibrary("./Camera_DaHua/dhnetsdk" , NetSDKLib.class);
    NetSDKLib CONFIG_INSTANCE = (NetSDKLib) Native.loadLibrary("./Camera_DaHua/dhconfigsdk" , NetSDKLib.class);

    /************************************************************************
     ** ��������
     ***********************************************************************/
    public static final int NET_SERIALNO_LEN                      = 48;             // �豸���к��ַ�����
    public static final int NET_CFG_Max_VideoColor                = 24;             // ÿ��ͨ�������Ƶ������ɫ��������
    public static final int NET_CFG_Custom_Title_Len              = 1024;           // �Զ���������Ƴ���(���䵽1024)
    public static final int NET_CFG_Custom_TitleType_Len          = 32;             // �Զ���������ͳ���
    public static final int NET_CFG_Max_Video_Widget_Cover        = 16;             // �������򸲸��������
    public static final int NET_CFG_Max_Video_Widget_Custom_Title = 8;              // ��������Զ�������������
    public static final int NET_CFG_Max_Video_Widget_Sensor_Info  = 2;              // ����������Ӵ�������Ϣ�������Ŀ
    public static final int NET_CFG_Max_Description_Num           = 4;              // ��������������Ϣ��������

    // �������ʹ��ţ���ӦCLIENT_GetLastError�ӿڵķ���ֵ
    public static final int NET_NOERROR                         =  0;               // û�д���
    public static final int NET_ERROR                           = -1;               // δ֪����
    public static final int NET_SYSTEM_ERROR                    = (0x80000000|1);   // Windowsϵͳ����
    public static final int NET_NETWORK_ERROR                   = (0x80000000|2);   // ������󣬿�������Ϊ���糬ʱ
    public static final int NET_DEV_VER_NOMATCH                 = (0x80000000|3);   // �豸Э�鲻ƥ��
    public static final int NET_INVALID_HANDLE                  = (0x80000000|4);   // �����Ч
    public static final int NET_OPEN_CHANNEL_ERROR              = (0x80000000|5);   // ��ͨ��ʧ��
    public static final int NET_CLOSE_CHANNEL_ERROR             = (0x80000000|6);   // �ر�ͨ��ʧ��
    public static final int NET_ILLEGAL_PARAM                   = (0x80000000|7);   // �û��������Ϸ�
    public static final int NET_SDK_INIT_ERROR                  = (0x80000000|8);   // SDK��ʼ������
    public static final int NET_SDK_UNINIT_ERROR                = (0x80000000|9);   // SDK�������
    public static final int NET_RENDER_OPEN_ERROR               = (0x80000000|10);  // ����render��Դ����
    public static final int NET_DEC_OPEN_ERROR                  = (0x80000000|11);  // �򿪽�������
    public static final int NET_DEC_CLOSE_ERROR                 = (0x80000000|12);  // �رս�������
    public static final int NET_MULTIPLAY_NOCHANNEL             = (0x80000000|13);  // �໭��Ԥ���м�⵽ͨ����Ϊ0
    public static final int NET_TALK_INIT_ERROR                 = (0x80000000|14);  // ¼�����ʼ��ʧ��
    public static final int NET_TALK_NOT_INIT                   = (0x80000000|15);  // ¼����δ����ʼ��
    public static final int NET_TALK_SENDDATA_ERROR             = (0x80000000|16);  // ������Ƶ���ݳ���
    public static final int NET_REAL_ALREADY_SAVING             = (0x80000000|17);  // ʵʱ�����Ѿ����ڱ���״̬
    public static final int NET_NOT_SAVING                      = (0x80000000|18);  // δ����ʵʱ����
    public static final int NET_OPEN_FILE_ERROR                 = (0x80000000|19);  // ���ļ�����
    public static final int NET_PTZ_SET_TIMER_ERROR             = (0x80000000|20);  // ������̨���ƶ�ʱ��ʧ��
    public static final int NET_RETURN_DATA_ERROR               = (0x80000000|21);  // �Է������ݵ�У�����
    public static final int NET_INSUFFICIENT_BUFFER             = (0x80000000|22);  // û���㹻�Ļ���
    public static final int NET_NOT_SUPPORTED                   = (0x80000000|23);  // ��ǰSDKδ֧�ָù���
    public static final int NET_NO_RECORD_FOUND                 = (0x80000000|24);  // ��ѯ����¼��
    public static final int NET_NOT_AUTHORIZED                  = (0x80000000|25);  // �޲���Ȩ��
    public static final int NET_NOT_NOW                         = (0x80000000|26);  // ��ʱ�޷�ִ��
    public static final int NET_NO_TALK_CHANNEL                 = (0x80000000|27);  // δ���ֶԽ�ͨ��
    public static final int NET_NO_AUDIO                        = (0x80000000|28);  // δ������Ƶ
    public static final int NET_NO_INIT                         = (0x80000000|29);  // ����SDKδ����ʼ��
    public static final int NET_DOWNLOAD_END                    = (0x80000000|30);  // �����ѽ���
    public static final int NET_EMPTY_LIST                      = (0x80000000|31);  // ��ѯ���Ϊ��
    public static final int NET_ERROR_GETCFG_SYSATTR            = (0x80000000|32);  // ��ȡϵͳ��������ʧ��
    public static final int NET_ERROR_GETCFG_SERIAL             = (0x80000000|33);  // ��ȡ���к�ʧ��
    public static final int NET_ERROR_GETCFG_GENERAL            = (0x80000000|34);  // ��ȡ��������ʧ��
    public static final int NET_ERROR_GETCFG_DSPCAP             = (0x80000000|35);  // ��ȡDSP��������ʧ��
    public static final int NET_ERROR_GETCFG_NETCFG             = (0x80000000|36);  // ��ȡ��������ʧ��
    public static final int NET_ERROR_GETCFG_CHANNAME           = (0x80000000|37);  // ��ȡͨ������ʧ��
    public static final int NET_ERROR_GETCFG_VIDEO              = (0x80000000|38);  // ��ȡ��Ƶ����ʧ��
    public static final int NET_ERROR_GETCFG_RECORD             = (0x80000000|39);  // ��ȡ¼������ʧ��
    public static final int NET_ERROR_GETCFG_PRONAME            = (0x80000000|40);  // ��ȡ������Э������ʧ��
    public static final int NET_ERROR_GETCFG_FUNCNAME           = (0x80000000|41);  // ��ȡ232���ڹ�������ʧ��
    public static final int NET_ERROR_GETCFG_485DECODER         = (0x80000000|42);  // ��ȡ����������ʧ��
    public static final int NET_ERROR_GETCFG_232COM             = (0x80000000|43);  // ��ȡ232��������ʧ��
    public static final int NET_ERROR_GETCFG_ALARMIN            = (0x80000000|44);  // ��ȡ�ⲿ������������ʧ��
    public static final int NET_ERROR_GETCFG_ALARMDET           = (0x80000000|45);  // ��ȡ��̬��ⱨ��ʧ��
    public static final int NET_ERROR_GETCFG_SYSTIME            = (0x80000000|46);  // ��ȡ�豸ʱ��ʧ��
    public static final int NET_ERROR_GETCFG_PREVIEW            = (0x80000000|47);  // ��ȡԤ������ʧ��
    public static final int NET_ERROR_GETCFG_AUTOMT             = (0x80000000|48);  // ��ȡ�Զ�ά������ʧ��
    public static final int NET_ERROR_GETCFG_VIDEOMTRX          = (0x80000000|49);  // ��ȡ��Ƶ��������ʧ��
    public static final int NET_ERROR_GETCFG_COVER              = (0x80000000|50);  // ��ȡ�����ڵ�����ʧ��
    public static final int NET_ERROR_GETCFG_WATERMAKE          = (0x80000000|51);  // ��ȡͼ��ˮӡ����ʧ��
    public static final int NET_ERROR_SETCFG_GENERAL            = (0x80000000|55);  // �޸ĳ�������ʧ��
    public static final int NET_ERROR_SETCFG_NETCFG             = (0x80000000|56);  // �޸���������ʧ��
    public static final int NET_ERROR_SETCFG_CHANNAME           = (0x80000000|57);  // �޸�ͨ������ʧ��
    public static final int NET_ERROR_SETCFG_VIDEO              = (0x80000000|58);  // �޸���Ƶ����ʧ��
    public static final int NET_ERROR_SETCFG_RECORD             = (0x80000000|59);  // �޸�¼������ʧ��
    public static final int NET_ERROR_SETCFG_485DECODER         = (0x80000000|60);  // �޸Ľ���������ʧ��
    public static final int NET_ERROR_SETCFG_232COM             = (0x80000000|61);  // �޸�232��������ʧ��
    public static final int NET_ERROR_SETCFG_ALARMIN            = (0x80000000|62);  // �޸��ⲿ���뱨������ʧ��
    public static final int NET_ERROR_SETCFG_ALARMDET           = (0x80000000|63);  // �޸Ķ�̬��ⱨ������ʧ��
    public static final int NET_ERROR_SETCFG_SYSTIME            = (0x80000000|64);  // �޸��豸ʱ��ʧ��
    public static final int NET_ERROR_SETCFG_PREVIEW            = (0x80000000|65);  // �޸�Ԥ������ʧ��
    public static final int NET_ERROR_SETCFG_AUTOMT             = (0x80000000|66);  // �޸��Զ�ά������ʧ��
    public static final int NET_ERROR_SETCFG_VIDEOMTRX          = (0x80000000|67);  // �޸���Ƶ��������ʧ��
    public static final int NET_ERROR_SETCFG_COVER              = (0x80000000|68);  // �޸������ڵ�����ʧ��
    public static final int NET_ERROR_SETCFG_WATERMAKE          = (0x80000000|69);  // �޸�ͼ��ˮӡ����ʧ��
    public static final int NET_ERROR_SETCFG_WLAN               = (0x80000000|70);  // �޸�����������Ϣʧ��
    public static final int NET_ERROR_SETCFG_WLANDEV            = (0x80000000|71);  // ѡ�����������豸ʧ��
    public static final int NET_ERROR_SETCFG_REGISTER           = (0x80000000|72);  // �޸�����ע���������ʧ��
    public static final int NET_ERROR_SETCFG_CAMERA             = (0x80000000|73);  // �޸�����ͷ��������ʧ��
    public static final int NET_ERROR_SETCFG_INFRARED           = (0x80000000|74);  // �޸ĺ��ⱨ������ʧ��
    public static final int NET_ERROR_SETCFG_SOUNDALARM         = (0x80000000|75);  // �޸���Ƶ��������ʧ��
    public static final int NET_ERROR_SETCFG_STORAGE            = (0x80000000|76);  // �޸Ĵ洢λ������ʧ��
    public static final int NET_AUDIOENCODE_NOTINIT             = (0x80000000|77);  // ��Ƶ����ӿ�û�гɹ���ʼ��
    public static final int NET_DATA_TOOLONGH                   = (0x80000000|78);  // ���ݹ���
    public static final int NET_UNSUPPORTED                     = (0x80000000|79);  // �豸��֧�ָò���
    public static final int NET_DEVICE_BUSY                     = (0x80000000|80);  // �豸��Դ����
    public static final int NET_SERVER_STARTED                  = (0x80000000|81);  // �������Ѿ�����
    public static final int NET_SERVER_STOPPED                  = (0x80000000|82);  // ��������δ�ɹ�����
    public static final int NET_LISTER_INCORRECT_SERIAL         = (0x80000000|83);  // �������к�����
    public static final int NET_QUERY_DISKINFO_FAILED           = (0x80000000|84);  // ��ȡӲ����Ϣʧ��
    public static final int NET_ERROR_GETCFG_SESSION            = (0x80000000|85);  // ��ȡ����Session��Ϣ
    public static final int NET_USER_FLASEPWD_TRYTIME           = (0x80000000|86);  // ����������󳬹����ƴ���
    public static final int NET_LOGIN_ERROR_PASSWORD            = (0x80000000|100); // ���벻��ȷ
    public static final int NET_LOGIN_ERROR_USER                = (0x80000000|101); // �ʻ�������
    public static final int NET_LOGIN_ERROR_TIMEOUT             = (0x80000000|102); // �ȴ���¼���س�ʱ
    public static final int NET_LOGIN_ERROR_RELOGGIN            = (0x80000000|103); // �ʺ��ѵ�¼
    public static final int NET_LOGIN_ERROR_LOCKED              = (0x80000000|104); // �ʺ��ѱ�����
    public static final int NET_LOGIN_ERROR_BLACKLIST           = (0x80000000|105); // �ʺ��ѱ���Ϊ������
    public static final int NET_LOGIN_ERROR_BUSY                = (0x80000000|106); // ��Դ���㣬ϵͳæ
    public static final int NET_LOGIN_ERROR_CONNECT             = (0x80000000|107); // ��¼�豸��ʱ���������粢����
    public static final int NET_LOGIN_ERROR_NETWORK             = (0x80000000|108); // ��������ʧ��
    public static final int NET_LOGIN_ERROR_SUBCONNECT          = (0x80000000|109); // ��¼�豸�ɹ������޷�������Ƶͨ������������״��
    public static final int NET_LOGIN_ERROR_MAXCONNECT          = (0x80000000|110); // �������������
    public static final int NET_LOGIN_ERROR_PROTOCOL3_ONLY      = (0x80000000|111); // ֻ֧��3��Э��
    public static final int NET_LOGIN_ERROR_UKEY_LOST           = (0x80000000|112); // δ����U�ܻ�U����Ϣ����
    public static final int NET_LOGIN_ERROR_NO_AUTHORIZED       = (0x80000000|113); // �ͻ���IP��ַû�е�¼Ȩ��
    public static final int NET_RENDER_SOUND_ON_ERROR           = (0x80000000|120); // Render�����Ƶ����
    public static final int NET_RENDER_SOUND_OFF_ERROR          = (0x80000000|121); // Render��ر���Ƶ����
    public static final int NET_RENDER_SET_VOLUME_ERROR         = (0x80000000|122); // Render�������������
    public static final int NET_RENDER_ADJUST_ERROR             = (0x80000000|123); // Render�����û����������
    public static final int NET_RENDER_PAUSE_ERROR              = (0x80000000|124); // Render����ͣ���ų���
    public static final int NET_RENDER_SNAP_ERROR               = (0x80000000|125); // Render��ץͼ����
    public static final int NET_RENDER_STEP_ERROR               = (0x80000000|126); // Render�ⲽ������
    public static final int NET_RENDER_FRAMERATE_ERROR          = (0x80000000|127); // Render������֡�ʳ���
    public static final int NET_RENDER_DISPLAYREGION_ERROR      = (0x80000000|128); // Render��������ʾ�������
    public static final int NET_GROUP_EXIST                     = (0x80000000|140); // �����Ѵ���
    public static final int NET_GROUP_NOEXIST                   = (0x80000000|141); // ����������
    public static final int NET_GROUP_RIGHTOVER                 = (0x80000000|142); // ���Ȩ�޳���Ȩ���б�Χ
    public static final int NET_GROUP_HAVEUSER                  = (0x80000000|143); // �������û�������ɾ��
    public static final int NET_GROUP_RIGHTUSE                  = (0x80000000|144); // ���ĳ��Ȩ�ޱ��û�ʹ�ã����ܳ���
    public static final int NET_GROUP_SAMENAME                  = (0x80000000|145); // ������ͬ���������ظ�
    public static final int NET_USER_EXIST                      = (0x80000000|146); // �û��Ѵ���
    public static final int NET_USER_NOEXIST                    = (0x80000000|147); // �û�������
    public static final int NET_USER_RIGHTOVER                  = (0x80000000|148); // �û�Ȩ�޳�����Ȩ��
    public static final int NET_USER_PWD                        = (0x80000000|149); // �����ʺţ��������޸�����
    public static final int NET_USER_FLASEPWD                   = (0x80000000|150); // ���벻��ȷ
    public static final int NET_USER_NOMATCHING                 = (0x80000000|151); // ���벻ƥ��
    public static final int NET_USER_INUSE                      = (0x80000000|152); // �˺�����ʹ����
    public static final int NET_ERROR_GETCFG_ETHERNET           = (0x80000000|300); // ��ȡ��������ʧ��
    public static final int NET_ERROR_GETCFG_WLAN               = (0x80000000|301); // ��ȡ����������Ϣʧ��
    public static final int NET_ERROR_GETCFG_WLANDEV            = (0x80000000|302); // ��ȡ���������豸ʧ��
    public static final int NET_ERROR_GETCFG_REGISTER           = (0x80000000|303); // ��ȡ����ע�����ʧ��
    public static final int NET_ERROR_GETCFG_CAMERA             = (0x80000000|304); // ��ȡ����ͷ����ʧ��
    public static final int NET_ERROR_GETCFG_INFRARED           = (0x80000000|305); // ��ȡ���ⱨ������ʧ��
    public static final int NET_ERROR_GETCFG_SOUNDALARM         = (0x80000000|306); // ��ȡ��Ƶ��������ʧ��
    public static final int NET_ERROR_GETCFG_STORAGE            = (0x80000000|307); // ��ȡ�洢λ������ʧ��
    public static final int NET_ERROR_GETCFG_MAIL               = (0x80000000|308); // ��ȡ�ʼ�����ʧ��
    public static final int NET_CONFIG_DEVBUSY                  = (0x80000000|309); // ��ʱ�޷�����
    public static final int NET_CONFIG_DATAILLEGAL              = (0x80000000|310); // �������ݲ��Ϸ�
    public static final int NET_ERROR_GETCFG_DST                = (0x80000000|311); // ��ȡ����ʱ����ʧ��
    public static final int NET_ERROR_SETCFG_DST                = (0x80000000|312); // ��������ʱ����ʧ��
    public static final int NET_ERROR_GETCFG_VIDEO_OSD          = (0x80000000|313); // ��ȡ��ƵOSD��������ʧ��
    public static final int NET_ERROR_SETCFG_VIDEO_OSD          = (0x80000000|314); // ������ƵOSD��������ʧ��
    public static final int NET_ERROR_GETCFG_GPRSCDMA           = (0x80000000|315); // ��ȡCDMA\GPRS��������ʧ��
    public static final int NET_ERROR_SETCFG_GPRSCDMA           = (0x80000000|316); // ����CDMA\GPRS��������ʧ��
    public static final int NET_ERROR_GETCFG_IPFILTER           = (0x80000000|317); // ��ȡIP��������ʧ��
    public static final int NET_ERROR_SETCFG_IPFILTER           = (0x80000000|318); // ����IP��������ʧ��
    public static final int NET_ERROR_GETCFG_TALKENCODE         = (0x80000000|319); // ��ȡ�����Խ���������ʧ��
    public static final int NET_ERROR_SETCFG_TALKENCODE         = (0x80000000|320); // ���������Խ���������ʧ��
    public static final int NET_ERROR_GETCFG_RECORDLEN          = (0x80000000|321); // ��ȡ¼������������ʧ��
    public static final int NET_ERROR_SETCFG_RECORDLEN          = (0x80000000|322); // ����¼������������ʧ��
    public static final int NET_DONT_SUPPORT_SUBAREA            = (0x80000000|323); // ��֧������Ӳ�̷���
    public static final int NET_ERROR_GET_AUTOREGSERVER         = (0x80000000|324); // ��ȡ�豸������ע���������Ϣʧ��
    public static final int NET_ERROR_CONTROL_AUTOREGISTER      = (0x80000000|325); // ����ע���ض���ע�����
    public static final int NET_ERROR_DISCONNECT_AUTOREGISTER   = (0x80000000|326); // �Ͽ�����ע�����������
    public static final int NET_ERROR_GETCFG_MMS                = (0x80000000|327); // ��ȡmms����ʧ��
    public static final int NET_ERROR_SETCFG_MMS                = (0x80000000|328); // ����mms����ʧ��
    public static final int NET_ERROR_GETCFG_SMSACTIVATION      = (0x80000000|329); // ��ȡ���ż���������������ʧ��
    public static final int NET_ERROR_SETCFG_SMSACTIVATION      = (0x80000000|330); // ���ö��ż���������������ʧ��
    public static final int NET_ERROR_GETCFG_DIALINACTIVATION   = (0x80000000|331); // ��ȡ���ż���������������ʧ��
    public static final int NET_ERROR_SETCFG_DIALINACTIVATION   = (0x80000000|332); // ���ò��ż���������������ʧ��
    public static final int NET_ERROR_GETCFG_VIDEOOUT           = (0x80000000|333); // ��ѯ��Ƶ�����������ʧ��
    public static final int NET_ERROR_SETCFG_VIDEOOUT           = (0x80000000|334); // ������Ƶ�����������ʧ��
    public static final int NET_ERROR_GETCFG_OSDENABLE          = (0x80000000|335); // ��ȡosd����ʹ������ʧ��
    public static final int NET_ERROR_SETCFG_OSDENABLE          = (0x80000000|336); // ����osd����ʹ������ʧ��
    public static final int NET_ERROR_SETCFG_ENCODERINFO        = (0x80000000|337); // ��������ͨ��ǰ�˱����������ʧ��
    public static final int NET_ERROR_GETCFG_TVADJUST           = (0x80000000|338); // ��ȡTV��������ʧ��
    public static final int NET_ERROR_SETCFG_TVADJUST           = (0x80000000|339); // ����TV��������ʧ��
    public static final int NET_ERROR_CONNECT_FAILED            = (0x80000000|340); // ����������ʧ��
    public static final int NET_ERROR_SETCFG_BURNFILE           = (0x80000000|341); // �����¼�ļ��ϴ�ʧ��
    public static final int NET_ERROR_SNIFFER_GETCFG            = (0x80000000|342); // ��ȡץ��������Ϣʧ��
    public static final int NET_ERROR_SNIFFER_SETCFG            = (0x80000000|343); // ����ץ��������Ϣʧ��
    public static final int NET_ERROR_DOWNLOADRATE_GETCFG       = (0x80000000|344); // ��ѯ����������Ϣʧ��
    public static final int NET_ERROR_DOWNLOADRATE_SETCFG       = (0x80000000|345); // ��������������Ϣʧ��
    public static final int NET_ERROR_SEARCH_TRANSCOM           = (0x80000000|346); // ��ѯ���ڲ���ʧ��
    public static final int NET_ERROR_GETCFG_POINT              = (0x80000000|347); // ��ȡԤ�Ƶ���Ϣ����
    public static final int NET_ERROR_SETCFG_POINT              = (0x80000000|348); // ����Ԥ�Ƶ���Ϣ����
    public static final int NET_SDK_LOGOUT_ERROR                = (0x80000000|349); // SDKû�������ǳ��豸
    public static final int NET_ERROR_GET_VEHICLE_CFG           = (0x80000000|350); // ��ȡ��������ʧ��
    public static final int NET_ERROR_SET_VEHICLE_CFG           = (0x80000000|351); // ���ó�������ʧ��
    public static final int NET_ERROR_GET_ATM_OVERLAY_CFG       = (0x80000000|352); // ��ȡatm��������ʧ��
    public static final int NET_ERROR_SET_ATM_OVERLAY_CFG       = (0x80000000|353); // ����atm��������ʧ��
    public static final int NET_ERROR_GET_ATM_OVERLAY_ABILITY   = (0x80000000|354); // ��ȡatm��������ʧ��
    public static final int NET_ERROR_GET_DECODER_TOUR_CFG      = (0x80000000|355); // ��ȡ������������Ѳ����ʧ��
    public static final int NET_ERROR_SET_DECODER_TOUR_CFG      = (0x80000000|356); // ���ý�����������Ѳ����ʧ��
    public static final int NET_ERROR_CTRL_DECODER_TOUR         = (0x80000000|357); // ���ƽ�����������Ѳʧ��
    public static final int NET_GROUP_OVERSUPPORTNUM            = (0x80000000|358); // �����豸֧������û�����Ŀ
    public static final int NET_USER_OVERSUPPORTNUM             = (0x80000000|359); // �����豸֧������û���Ŀ
    public static final int NET_ERROR_GET_SIP_CFG               = (0x80000000|368); // ��ȡSIP����ʧ��
    public static final int NET_ERROR_SET_SIP_CFG               = (0x80000000|369); // ����SIP����ʧ��
    public static final int NET_ERROR_GET_SIP_ABILITY           = (0x80000000|370); // ��ȡSIP����ʧ��
    public static final int NET_ERROR_GET_WIFI_AP_CFG           = (0x80000000|371); // ��ȡWIFI ap����ʧ��
    public static final int NET_ERROR_SET_WIFI_AP_CFG           = (0x80000000|372); // ����WIFI ap����ʧ��
    public static final int NET_ERROR_GET_DECODE_POLICY         = (0x80000000|373); // ��ȡ�����������ʧ��
    public static final int NET_ERROR_SET_DECODE_POLICY         = (0x80000000|374); // ���ý����������ʧ��
    public static final int NET_ERROR_TALK_REJECT               = (0x80000000|375); // �ܾ��Խ�
    public static final int NET_ERROR_TALK_OPENED               = (0x80000000|376); // �Խ��������ͻ��˴�
    public static final int NET_ERROR_TALK_RESOURCE_CONFLICIT   = (0x80000000|377); // ��Դ��ͻ
    public static final int NET_ERROR_TALK_UNSUPPORTED_ENCODE   = (0x80000000|378); // ��֧�ֵ����������ʽ
    public static final int NET_ERROR_TALK_RIGHTLESS            = (0x80000000|379); // ��Ȩ��
    public static final int NET_ERROR_TALK_FAILED               = (0x80000000|380); // ����Խ�ʧ��
    public static final int NET_ERROR_GET_MACHINE_CFG           = (0x80000000|381); // ��ȡ�����������ʧ��
    public static final int NET_ERROR_SET_MACHINE_CFG           = (0x80000000|382); // ���û����������ʧ��
    public static final int NET_ERROR_GET_DATA_FAILED           = (0x80000000|383); // �豸�޷���ȡ��ǰ��������
    public static final int NET_ERROR_MAC_VALIDATE_FAILED       = (0x80000000|384); // MAC��ַ��֤ʧ��
    public static final int NET_ERROR_GET_INSTANCE              = (0x80000000|385); // ��ȡ������ʵ��ʧ��
    public static final int NET_ERROR_JSON_REQUEST              = (0x80000000|386); // ���ɵ�json�ַ�������
    public static final int NET_ERROR_JSON_RESPONSE             = (0x80000000|387); // ��Ӧ��json�ַ�������
    public static final int NET_ERROR_VERSION_HIGHER            = (0x80000000|388); // Э��汾���ڵ�ǰʹ�õİ汾
    public static final int NET_SPARE_NO_CAPACITY               = (0x80000000|389); // �ȱ�����ʧ��, ��������
    public static final int NET_ERROR_SOURCE_IN_USE             = (0x80000000|390); // ��ʾԴ���������ռ��
    public static final int NET_ERROR_REAVE                     = (0x80000000|391); // �߼��û���ռ�ͼ��û���Դ
    public static final int NET_ERROR_NETFORBID                 = (0x80000000|392); // ��ֹ����
    public static final int NET_ERROR_GETCFG_MACFILTER          = (0x80000000|393); // ��ȡMAC��������ʧ��
    public static final int NET_ERROR_SETCFG_MACFILTER          = (0x80000000|394); // ����MAC��������ʧ��
    public static final int NET_ERROR_GETCFG_IPMACFILTER        = (0x80000000|395); // ��ȡIP/MAC��������ʧ��
    public static final int NET_ERROR_SETCFG_IPMACFILTER        = (0x80000000|396); // ����IP/MAC��������ʧ��
    public static final int NET_ERROR_OPERATION_OVERTIME        = (0x80000000|397); // ��ǰ������ʱ
    public static final int NET_ERROR_SENIOR_VALIDATE_FAILED    = (0x80000000|398); // �߼�У��ʧ��
    public static final int NET_ERROR_DEVICE_ID_NOT_EXIST       = (0x80000000|399); // �豸ID������
    public static final int NET_ERROR_UNSUPPORTED               = (0x80000000|400); // ��֧�ֵ�ǰ����
    public static final int NET_ERROR_PROXY_DLLLOAD             = (0x80000000|401); // ��������ʧ��
    public static final int NET_ERROR_PROXY_ILLEGAL_PARAM       = (0x80000000|402); // �����û��������Ϸ�
    public static final int NET_ERROR_PROXY_INVALID_HANDLE      = (0x80000000|403); // ��������Ч
    public static final int NET_ERROR_PROXY_LOGIN_DEVICE_ERROR  = (0x80000000|404); // �������ǰ���豸ʧ��
    public static final int NET_ERROR_PROXY_START_SERVER_ERROR  = (0x80000000|405); // �����������ʧ��

    // CLIENT_StartListenEx�����¼�
    public static final int NET_ALARM_ALARM_EX 					= 0x2101;     		// �ⲿ�����������ֽ������豸����ͨ��������ͬ��ÿ���ֽڱ�ʾһ������ͨ���ı���״̬��1Ϊ�б�����0Ϊ�ޱ�����
    public static final int NET_VIDEOLOST_ALARM_EX 				= 0x2103; 			// ��Ƶ��ʧ�����������ֽ������豸��Ƶͨ��������ͬ��ÿ���ֽڱ�ʾһ����Ƶͨ������Ƶ��ʧ����״̬��1Ϊ�б�����0Ϊ�ޱ�����
    public static final int NET_SHELTER_ALARM_EX 				= 0x2104;   		// ��Ƶ�ڵ������������ֽ������豸��Ƶͨ��������ͬ��ÿ���ֽڱ�ʾһ����Ƶͨ�����ڵ�(����)����״̬��1Ϊ�б�����0Ϊ�ޱ�����
    public static final int NET_DISKFULL_ALARM_EX 				= 0x2106;  			// Ӳ��������������Ϊ1���ֽڣ�1Ϊ��Ӳ����������0Ϊ�ޱ�����
    public static final int NET_DISKERROR_ALARM_EX 				= 0x2107; 			// ��Ӳ�̱���������Ϊ32���ֽڣ�ÿ���ֽڱ�ʾһ��Ӳ�̵Ĺ��ϱ���״̬��1Ϊ�б�����0Ϊ�ޱ�����
    public static final int NET_ALARM_BATTERYLOWPOWER 			= 0x2134;      		// ��ص����ͱ���(��Ӧ�ṹ��ALARM_BATTERYLOWPOWER_INFO)
    public static final int NET_ALARM_TEMPERATURE 				= 0x2135;  			// �¶ȹ��߱���(��Ӧ�ṹ��ALARM_TEMPERATURE_INFO)
    public static final int NET_ALARM_ALARM_EX2 				= 0x2175;    		// ���ر����¼�(��Ӧ�ṹ��ALARM_ALARM_INFO_EX2,��NET_ALARM_ALARM_EX����)
    public static final int NET_EVENT_VIDEOABNORMALDETECTION    = 0x218e;           // ��Ƶ�쳣�¼�(��ӦALARM_VIDEOABNORMAL_DETECTION_INFO)
    public static final int NET_ALARM_PARKING_CARD				= 0x31a4;			// ͣ��ˢ���¼�(��Ӧ�ṹ��  ALARM_PARKING_CARD)
    public static final int NET_ALARM_NEW_FILE                  = 0x31b3;           // ���ļ��¼�(��ӦALARM_NEW_FILE_INFO)
    public static final int NET_ALARM_HUMAM_NUMBER_STATISTIC    = 0x31cc;           // ������/������ͳ���¼� (��Ӧ�ṹ�� ALARM_HUMAN_NUMBER_STATISTIC_INFO)
    public static final int NET_ALARM_ARMMODE_CHANGE_EVENT      = 0x3175;			// ������״̬�仯�¼�(��Ӧ�ṹ�� ALARM_ARMMODE_CHANGE_INFO)
    public static final int NET_ALARM_RCEMERGENCY_CALL          = 0x318b;  			// �������б����¼�(��Ӧ�ṹ�� ALARM_RCEMERGENCY_CALL_INFO)
    
    
    // CLIENT_RealLoadPictureEx ����ץͼ�¼�
    public static final int EVENT_IVS_ALL                       = 0x00000001;       // ���������¼�
    public static final int EVENT_IVS_CROSSLINEDETECTION        = 0x00000002;       // �������¼�(��Ӧ DEV_EVENT_CROSSLINE_INFO)
    public static final int EVENT_IVS_CROSSREGIONDETECTION      = 0x00000003;       // �������¼�(��Ӧ DEV_EVENT_CROSSREGION_INFO)
    public static final int EVENT_IVS_WANDERDETECTION           = 0x00000007;       // �ǻ��¼�(��Ӧ  DEV_EVENT_WANDER_INFO)
    public static final int EVENT_IVS_FIGHTDETECTION            = 0x0000000E;       // ��Ź�¼�(��Ӧ DEV_EVENT_FIGHT_INFO)  
    public static final int EVENT_IVS_TRAFFICJUNCTION           = 0x00000017;       // ��ͨ·���¼�----�Ϲ���(��Ӧ DEV_EVENT_TRAFFICJUNCTION_INFO)
    public static final int EVENT_IVS_TRAFFICGATE               = 0x00000018;       // ��ͨ�����¼�----�Ϲ���(��Ӧ DEV_EVENT_TRAFFICGATE_INFO)
    public static final int EVENT_IVS_FACEDETECT                = 0x0000001A;       // ��������¼� (��Ӧ DEV_EVENT_FACEDETECT_INFO)
    public static final int EVENT_IVS_TRAFFICJAM                = 0x0000001B;       // ��ͨӵ���¼�(��Ӧ DEV_EVENT_TRAFFICJAM_INFO)
    public static final int EVENT_IVS_TRAFFIC_RUNREDLIGHT       = 0x00000100;       // ��ͨΥ��-������¼�(��Ӧ DEV_EVENT_TRAFFIC_RUNREDLIGHT_INFO)
    public static final int EVENT_IVS_TRAFFIC_OVERLINE          = 0x00000101;       // ��ͨΥ��-ѹ�������¼�(��Ӧ DEV_EVENT_TRAFFIC_OVERLINE_INFO)
    public static final int EVENT_IVS_TRAFFIC_RETROGRADE        = 0x00000102;       // ��ͨΥ��-�����¼�(��Ӧ  DEV_EVENT_TRAFFIC_RETROGRADE_INFO)
    public static final int EVENT_IVS_TRAFFIC_OVERSPEED         = 0x00000106;       // ��ͨΥ��-����(��Ӧ DEV_EVENT_TRAFFIC_OVERSPEED_INFO)
    public static final int EVENT_IVS_TRAFFIC_UNDERSPEED        = 0x00000107;       // ��ͨΥ��-����(��Ӧ DEV_EVENT_TRAFFIC_UNDERSPEED_INFO)
    public static final int EVENT_IVS_TRAFFIC_PARKING           = 0x00000108;       // ��ͨΥ��-Υ��ͣ��(��Ӧ DEV_EVENT_TRAFFIC_PARKING_INFO)
    public static final int EVENT_IVS_TRAFFIC_WRONGROUTE        = 0x00000109;       // ��ͨΥ��-����������ʻ(��Ӧ DEV_EVENT_TRAFFIC_WRONGROUTE_INFO)
    public static final int EVENT_IVS_TRAFFIC_CROSSLANE         = 0x0000010A;       // ��ͨΥ��-Υ�±��(��Ӧ DEV_EVENT_TRAFFIC_CROSSLANE_INFO)
    public static final int EVENT_IVS_TRAFFIC_OVERYELLOWLINE    = 0x0000010B;       // ��ͨΥ��-ѹ���� (��Ӧ DEV_EVENT_TRAFFIC_OVERYELLOWLINE_INFO)
    public static final int EVENT_IVS_TRAFFIC_YELLOWPLATEINLANE = 0x0000010E;       // ��ͨΥ��-���Ƴ�ռ���¼�(��Ӧ DEV_EVENT_TRAFFIC_YELLOWPLATEINLANE_INFO)
    public static final int EVENT_IVS_TRAFFIC_PEDESTRAINPRIORITY = 0x0000010F;      // ��ͨΥ��-���������������¼�(��Ӧ DEV_EVENT_TRAFFIC_PEDESTRAINPRIORITY_INFO)
    public static final int EVENT_IVS_TRAFFIC_NOPASSING         = 0x00000111;       // ��ͨΥ��-��ֹͨ���¼�(��Ӧ DEV_EVENT_TRAFFIC_NOPASSING_INFO)
    public static final int EVENT_IVS_FACERECOGNITION           = 0x00000117;       // ����ʶ���¼�(��Ӧ DEV_EVENT_FACERECOGNITION_INFO
    public static final int EVENT_IVS_TRAFFIC_MANUALSNAP        = 0x00000118;       // ��ͨ�ֶ�ץ���¼�(��Ӧ  DEV_EENT_TRAFFIC_MANUALSNAP_INFO)
    public static final int EVENT_IVS_TRAFFIC_FLOWSTATE         = 0x00000119;       // ��ͨ����ͳ���¼�(��Ӧ DEV_EVENT_TRAFFIC_FLOW_STATE)
    public static final int EVENT_IVS_TRAFFIC_VEHICLEINROUTE    = 0x0000011B;       // �г�ռ���¼�(��Ӧ DEV_EVENT_TRAFFIC_VEHICLEINROUTE_INFO)
    public static final int EVENT_IVS_TRAFFIC_TOLLGATE          = 0x00000120;       // ��ͨΥ��--�����¼�----�¹���(��Ӧ DEV_EVENT_TRAFFICJUNCTION_INFO)
    public static final int EVENT_IVS_TRAFFIC_VEHICLEINBUSROUTE = 0x00000124;       // ��ͨΥ��--ռ�ù��������¼�(��Ӧ DEV_EVENT_TRAFFIC_VEHICLEINBUSROUTE_INFO)
    public static final int EVENT_IVS_TRAFFIC_BACKING           = 0x00000125;       // ��ͨΥ��--Υ�µ����¼�(��Ӧ DEV_EVENT_IVS_TRAFFIC_BACKING_INFO)
    public static final int EVENT_IVS_AUDIO_ABNORMALDETECTION   = 0x00000126;       // �����쳣���(��Ӧ DEV_EVENT_IVS_AUDIO_ABNORMALDETECTION_INFO)
    public static final int EVENT_IVS_CLIMBDETECTION            = 0x00000128;       // �ʸ߼���¼�(��Ӧ DEV_EVENT_IVS_CLIMB_INFO)
    public static final int EVENT_IVS_LEAVEDETECTION            = 0x00000129;       // ��ڼ���¼�(��Ӧ DEV_EVENT_IVS_LEAVE_INFO)
    public static final int EVENT_IVS_TRAFFIC_PARKINGONYELLOWBOX = 0x0000012A;      // ��ͨΥ��--��������ץ���¼�(��Ӧ DEV_EVENT_TRAFFIC_PARKINGONYELLOWBOX_INFO)
    public static final int EVENT_IVS_TRAFFIC_PARKINGSPACEPARKING = 0x0000012B;     // ��λ�г��¼�(��Ӧ DEV_EVENT_TRAFFIC_PARKINGSPACEPARKING_INFO )
    public static final int EVENT_IVS_TRAFFIC_PARKINGSPACENOPARKING = 0x0000012C;   // ��λ�޳��¼�(��Ӧ  DEV_EVENT_TRAFFIC_PARKINGSPACENOPARKING_INFO )
    public static final int EVENT_IVS_TRAFFIC_PEDESTRAIN        = 0x0000012D;       // ��ͨ�����¼�(��Ӧ DEV_EVENT_TRAFFIC_PEDESTRAIN_INFO)
    public static final int EVENT_IVS_TRAFFIC_THROW             = 0x0000012E;       // ��ͨ������Ʒ�¼�(��Ӧ DEV_EVENT_TRAFFIC_THROW_INFO)
    public static final int EVENT_IVS_TRAFFIC_OVERSTOPLINE      = 0X00000137;       // ��ͨΥ��--ѹֹͣ���¼�(��Ӧ DEV_EVENT_TRAFFIC_OVERSTOPLINE)
    public static final int EVENT_IVS_TRAFFIC_WITHOUT_SAFEBELT  = 0x00000138;       // ��ͨΥ��--��ͨδϵ��ȫ���¼�(��Ӧ DEV_EVENT_TRAFFIC_WITHOUT_SAFEBELT)
    public static final int EVENT_IVS_TRAFFIC_PASSNOTINORDER    = 0x0000013C;       // ��ͨΥ��--δ���涨����ͨ��(��Ӧ DEV_EVENT_TRAFFIC_PASSNOTINORDER_INFO)
    public static final int EVENT_IVS_TRAFFIC_JAM_FORBID_INTO	= 0x00000163;       // ��ͨΥ��--����ӵ�½����¼�(��Ӧ DEV_EVENT_ALARM_JAMFORBIDINTO_INFO)
    public static final int EVENT_IVS_TRAFFIC_FCC               = 0x0000016B;       // ����վ��ǹ����ǹ�¼�(��Ӧ  DEV_EVENT_TRAFFIC_FCC_INFO)
    
    // CLIENT_GetNewDevConfig/CLIENT_SetNewDevConfig ������
    public static final String CFG_CMD_VIDEOWIDGET              = "VideoWidget";         // ��Ƶ�����������(��Ӧ NET_CFG_VideoWidget )
    public static final String CFG_CMD_ANALYSEMODULE            = "VideoAnalyseModule";  // ����ļ��ģ������(��Ӧ CFG_ANALYSEMODULES_INFO)
    public static final String CFG_CMD_ANALYSERULE              = "VideoAnalyseRule";    // ��Ƶ������������(��Ӧ CFG_ANALYSERULES_INFO)
    public static final String CFG_CMD_VIDEOINOPTIONS           = "VideoInOptions";      // ��Ƶ����ǰ��ѡ��(��ӦCFG_VIDEO_IN_OPTIONS)
    public static final String CFG_CMD_RAINBRUSHMODE            = "RainBrushMode";       // ��ˢģʽ�������(��ӦCFG_RAINBRUSHMODE_INFO����)
    public static final String CFG_CMD_RAINBRUSH                = "RainBrush";           // ��ˢ����(��ӦCFG_RAINBRUSH_INFO)
    public static final String CFG_CMD_ENCODE                   = "Encode";              // ͼ��ͨ����������(��ӦCFG_ENCODE_INFO)
    public static final String CFG_CMD_VIDEO_IN_ZOOM            = "VideoInZoom";         // ��̨ͨ���䱶����(��ӦCFG_VIDEO_IN_ZOOM)
    public static final String CFG_CMD_REMOTEDEVICE				= "RemoteDevice";		 // Զ���豸��Ϣ(��Ӧ  AV_CFG_RemoteDevice ����, ͨ���޹�)
    public static final String CFG_CMD_ANALYSESOURCE			= "VideoAnalyseSource";  // ��Ƶ������Դ����(��Ӧ CFG_ANALYSESOURCE_INFO)
    public static final String CFG_CMD_TRAFFICGLOBAL            = "TrafficGlobal";       // ���ܽ�ͨȫ������(CFG_TRAFFICGLOBAL_INFO)
    
    // CLIENT_FileTransmit�ӿڴ����ļ�����
    public static final int NET_DEV_BLACKWHITETRANS_START      = 0x0003;           // ��ʼ���ͺڰ�����(��Ӧ�ṹ�� DHDEV_BLACKWHITE_LIST_INFO)
    public static final int NET_DEV_BLACKWHITETRANS_SEND       = 0x0004;           // ���ͺڰ�����
    public static final int NET_DEV_BLACKWHITETRANS_STOP       = 0x0005;           // ֹͣ���ͺڰ�����
    
    // ��������,��ӦCLIENT_GetDevConfig��CLIENT_SetDevConfig�ӿ�
    public static final int NET_DEV_DEVICECFG                   = 0x0001;                // �豸��������

    // ��������, ��Ӧ CLIENT_QueryNewSystemInfo �ӿ�
    public static final String CFG_CAP_CMD_DEVICE_STATE         = "trafficSnap.getDeviceStatus"; // ��ȡ�豸״̬��Ϣ (��Ӧ CFG_CAP_TRAFFIC_DEVICE_STATUS)
    
    // Զ�����ýṹ����س���                 
    public static final int NET_MAX_MAIL_ADDR_LEN              = 128;              // �ʼ���(��)��ַ��󳤶�
    public static final int NET_MAX_MAIL_SUBJECT_LEN           = 64;               // �ʼ�������󳤶�
    public static final int NET_MAX_IPADDR_LEN                 = 16;               // IP��ַ�ַ�������
    public static final int NET_MAX_IPADDR_LEN_EX              = 40;               // ��չIP��ַ�ַ�������, ֧��IPV6

    public static final int NET_MAX_DEV_ID_LEN                 = 48;               // ���������󳤶�
    public static final int NET_MAX_HOST_NAMELEN               = 64;               // ����������,
    public static final int NET_MAX_HOST_PSWLEN                = 32;               // ���볤��
    public static final int NET_MAX_ETHERNET_NUM               = 2;                // ��̫����������
    public static final int NET_MAX_ETHERNET_NUM_EX            = 10;               // ��չ��̫����������
    public static final int NET_DEV_CLASS_LEN                  = 16;               // �豸�����ַ�������"IPC"������
    public static final int NET_N_WEEKS                        = 7;                // һ�ܵ�����    
    public static final int NET_N_TSECT                        = 6;                // ͨ��ʱ��θ���
    public static final int NET_N_REC_TSECT                    = 6;                // ¼��ʱ��θ���
    public static final int NET_N_COL_TSECT                    = 2;                // ��ɫʱ��θ���            
    public static final int NET_N_ENCODE_AUX                   = 3;                // ��չ��������    
    public static final int NET_N_TALK                         = 1;                // ���Խ�ͨ������
    public static final int NET_N_COVERS                       = 1;                // �ڵ��������    
    public static final int NET_N_CHANNEL                      = 16;               // ���ͨ������    
    public static final int NET_N_ALARM_TSECT                  = 2;                // ������ʾʱ��θ���
    public static final int NET_MAX_ALARMOUT_NUM               = 16;               // ��������ڸ�������
    public static final int NET_MAX_AUDIO_IN_NUM               = 16;               // ��Ƶ����ڸ�������
    public static final int NET_MAX_VIDEO_IN_NUM               = 16;               // ��Ƶ����ڸ�������
    public static final int NET_MAX_ALARM_IN_NUM               = 16;               // ��������ڸ�������
    public static final int NET_MAX_DISK_NUM                   = 16;               // Ӳ�̸�������,�ݶ�Ϊ16
    public static final int NET_MAX_DECODER_NUM                = 16;               // ������(485)��������    
    public static final int NET_MAX_232FUNCS                   = 10;               // 232���ڹ��ܸ�������
    public static final int NET_MAX_232_NUM                    = 2;                // 232���ڸ�������
    public static final int NET_MAX_232_NUM_EX                 = 16;               // ��չ�������ø�������          
    public static final int NET_MAX_DECPRO_LIST_SIZE           = 100;              // ������Э���б��������
    public static final int NET_FTP_MAXDIRLEN                  = 240;              // FTP�ļ�Ŀ¼��󳤶�
    public static final int NET_MATRIX_MAXOUT                  = 16;               // ���������������
    public static final int NET_TOUR_GROUP_NUM                 = 6;                // ���������������
    public static final int NET_MAX_DDNS_NUM                   = 10;               // �豸֧�ֵ�ddns������������
    public static final int NET_MAX_SERVER_TYPE_LEN            = 32;               // ddns����������,����ַ�������
    public static final int NET_MAX_DOMAIN_NAME_LEN            = 256;              // ddns����,����ַ�������
    public static final int NET_MAX_DDNS_ALIAS_LEN             = 32;               // ddns����������,����ַ�������
    public static final int NET_MAX_DEFAULT_DOMAIN_LEN         = 60;               // ddnsĬ������,����ַ�������     
    public static final int NET_MOTION_ROW                     = 32;               // ��̬������������
    public static final int NET_MOTION_COL                     = 32;               // ��̬������������
    public static final int NET_STATIC_ROW                     = 32;               // ��̬������������
    public static final int NET_STATIC_COL                     = 32;               // ��̬������������
    public static final int NET_FTP_USERNAME_LEN               = 64;               // FTP����,�û�����󳤶�
    public static final int NET_FTP_PASSWORD_LEN               = 64;               // FTP����,������󳤶�
    public static final int NET_TIME_SECTION                   = 2;                // FTP����,ÿ��ʱ��θ���
    public static final int NET_FTP_MAX_PATH                   = 240;              // FTP����,�ļ�·������󳤶�
    public static final int NET_FTP_MAX_SUB_PATH               = 128;              // FTP����,�ļ�·������󳤶�
    public static final int NET_INTERVIDEO_UCOM_CHANID         = 32;               // ƽ̨��������,U��ͨͨ��ID
    public static final int NET_INTERVIDEO_UCOM_DEVID          = 32;               // ƽ̨��������,U��ͨ�豸ID
    public static final int NET_INTERVIDEO_UCOM_REGPSW         = 16;               // ƽ̨��������,U��ͨע������
    public static final int NET_INTERVIDEO_UCOM_USERNAME       = 32;               // ƽ̨��������,U��ͨ�û���
    public static final int NET_INTERVIDEO_UCOM_USERPSW        = 32;               // ƽ̨��������,U��ͨ����
    public static final int NET_INTERVIDEO_NSS_IP              = 32;              // ƽ̨��������,������άIP
    public static final int NET_INTERVIDEO_NSS_SERIAL          = 32;               // ƽ̨��������,������άserial
    public static final int NET_INTERVIDEO_NSS_USER            = 32;               // ƽ̨��������,������άuser
    public static final int NET_INTERVIDEO_NSS_PWD             = 50;              // ƽ̨��������,������άpassword
    public static final int NET_MAX_VIDEO_COVER_NUM            = 16;               // �ڵ�����������
    public static final int NET_MAX_WATERMAKE_DATA             = 4096;             // ˮӡͼƬ������󳤶�
    public static final int NET_MAX_WATERMAKE_LETTER           = 128;              // ˮӡ������󳤶�
    public static final int NET_MAX_WLANDEVICE_NUM             = 10;               // ����������������豸����
    public static final int NET_MAX_WLANDEVICE_NUM_EX          = 32;               // ����������������豸����
    public static final int NET_MAX_ALARM_NAME                 = 64;               // ��ַ����
    public static final int NET_MAX_REGISTER_SERVER_NUM        = 10;               // ����ע�����������
    public static final int NET_SNIFFER_FRAMEID_NUM            = 6;                // 6��FRAME ID ѡ��
    public static final int NET_SNIFFER_CONTENT_NUM            = 4;                // ÿ��FRAME��Ӧ��4��ץ������
    public static final int NET_SNIFFER_CONTENT_NUM_EX         = 8;                // ÿ��FRAME��Ӧ��8��ץ������
    public static final int NET_SNIFFER_PROTOCOL_SIZE          = 20;               // Э�����ֳ���
    public static final int NET_MAX_PROTOCOL_NAME_LENGTH       = 20;               
    public static final int NET_SNIFFER_GROUP_NUM              = 4;                // 4��ץ������
    public static final int NET_ALARM_OCCUR_TIME_LEN           = 40;               // �µı����ϴ�ʱ��ĳ���
    public static final int NET_VIDEO_OSD_NAME_NUM             = 64;               // ���ӵ����Ƴ���,Ŀǰ֧��32��Ӣ��,16������
    public static final int NET_VIDEO_CUSTOM_OSD_NUM           = 8;               // ֧�ֵ��Զ�����ӵ���Ŀ,������ʱ���ͨ��
    public static final int NET_VIDEO_CUSTOM_OSD_NUM_EX        = 256;              // ֧�ֵ��Զ�����ӵ���Ŀ,������ʱ���ͨ��
    public static final int NET_CONTROL_AUTO_REGISTER_NUM      = 100;              // ֧�ֶ�������ע��������ĸ���
    public static final int NET_MMS_RECEIVER_NUM               = 100;              // ֧�ֶ��Ž����ߵĸ���
    public static final int NET_MMS_SMSACTIVATION_NUM          = 100;              // ֧�ֶ��ŷ����ߵĸ���
    public static final int NET_MMS_DIALINACTIVATION_NUM       = 100;              // ֧�ֲ��ŷ����ߵĸ���
    public static final int NET_MAX_ALARM_IN_NUM_EX            = 32;               // ��������ڸ�������
    public static final int NET_MAX_IPADDR_OR_DOMAIN_LEN       = 64;               // IP��ַ�ַ�������
    public static final int NET_MAX_CALLID                     = 32;               // ����ID
    public static final int NET_MAX_FENCE_LINE_NUM             = 2;                // Χ�����������
    public static final int MAX_SMART_VALUE_NUM                = 30;               // ����smart��Ϣ����
    public static final int NET_INTERVIDEO_AMP_DEVICESERIAL    = 48;               // ƽ̨��������,������� �豸���к��ַ�������
    public static final int NET_INTERVIDEO_AMP_DEVICENAME      = 16;               // ƽ̨��������,������� �豸�����ַ�������
    public static final int NET_INTERVIDEO_AMP_USER            = 32;               // ƽ̨��������,������� ע���û����ַ�������
    public static final int NET_INTERVIDEO_AMP_PWD             = 32;               // ƽ̨��������,������� ע�������ַ�������
    public static final int MAX_SUBMODULE_NUM                  = 32;               // �����ģ����Ϣ����
    public static final int NET_MAX_CARWAY_NUM                 = 8;                // ��ͨץ��,��󳵵���
    public static final int NET_MAX_SNAP_SIGNAL_NUM            = 3;                // һ�����������ץ������
    public static final int NET_MAX_CARD_NUM                   = 128;              // ���ŵ�������
    public static final int NET_MAX_CARDINFO_LEN               = 32;               // ÿ��������ַ���
    public static final int NET_MAX_CONTROLER_NUM              = 64;               // ���֧�ֿ�������Ŀ
    public static final int NET_MAX_LIGHT_NUM                  = 32;               // �����Ƶ�����
    public static final int NET_MAX_SNMP_COMMON_LEN            = 64;               // snmp ��д���ݳ���
    public static final int NET_MAX_DDNS_STATE_LEN             = 128;              // DDNS ״̬��Ϣ����
    public static final int NET_MAX_PHONE_NO_LEN               = 16;               // �绰���볤��
    public static final int NET_MAX_MSGTYPE_LEN                = 32;               // �������ͻ����Ϣ���ͳ���
    public static final int NET_MAX_MSG_LEN                    = 256;              // �����Ͷ���Ϣ����
    public static final int NET_MAX_GRAB_INTERVAL_NUM          = 4;                // ����ͼƬץ�ĸ���
    public static final int NET_MAX_FLASH_NUM                  = 5;                // ���֧������Ƹ���
    public static final int NET_MAX_ISCSI_PATH_NUM             = 64;               // ISCSIԶ��Ŀ¼�������
    public static final int NET_MAX_WIRELESS_CHN_NUM           = 256;              // ����·������ŵ���
    public static final int NET_PROTOCOL3_BASE                 = 100;              // ����Э��汾����
    public static final int NET_PROTOCOL3_SUPPORT              = 11;               // ֻ֧��3��Э��
    public static final int NET_MAX_STAFF_NUM                  = 20;               // Ũ����Ƶ������Ϣ�б��������
    public static final int NET_MAX_CALIBRATEBOX_NUM           = 10;               // Ũ����Ƶ������Ϣ�б궨����������
    public static final int NET_MAX_EXCLUDEREGION_NUM          = 10;               // Ũ����Ƶ������Ϣ���ų�����������
    public static final int NET_MAX_POLYLINE_NUM               = 20;               // Ũ����Ƶ������Ϣ�б������
    public static final int NET_MAX_COLOR_NUM                  = 16;               // �����ɫ��Ŀ
    public static final int MAX_OBJFILTER_NUM                  = 16;              // �������������
    public static final int NET_MAX_SYNOPSIS_STATE_NAME        = 64;               // ��ƵŨ��״̬�ַ�������
    public static final int NET_MAX_SYNOPSIS_QUERY_FILE_COUNT  = 10;               // ��ƵŨ�����ԭʼ�ļ�����·������ʱ�ļ���������
    public static final int NET_MAX_SSID_LEN                   = 36;              // SSID����
    public static final int NET_MAX_APPIN_LEN                  = 16;               // PIN�볤��
    public static final int NET_NETINTERFACE_NAME_LEN          = 260;              // �������Ƴ���
    public static final int NET_NETINTERFACE_TYPE_LEN          = 260;             // �������ͳ���
    public static final int NET_MAX_CONNECT_STATUS_LEN         = 260;              // ����״̬�ַ�������
    public static final int NET_MAX_MODE_LEN                   = 64;               // 3G֧�ֵ�����ģʽ����
    public static final int NET_MAX_MODE_NUM                   = 64;               // 3G֧�ֵ�����ģʽ����
    public static final int NET_MAX_COMPRESSION_TYPES_NUM      = 16;               // ��Ƶ�����ʽ����������
    public static final int NET_MAX_CAPTURE_SIZE_NUM           = 64;               // ��Ƶ�ֱ��ʸ���
    public static final int NET_NODE_NAME_LEN                  = 64;               // ��֯�ṹ�ڵ����Ƴ���
    public static final int MAX_CALIBPOINTS_NUM                = 256;              // ֧�����궨����
    public static final int NET_MAX_ATTR_NUM                   = 32;               // ��ʾ��Ԫ�����������
    public static final int NET_MAX_CLOUDCONNECT_STATE_LEN     = 128;             // ��ע������״̬��Ϣ����
    public static final int NET_MAX_IPADDR_EX_LEN              = 128;              // ��չIP��ַ��󳤶�
    public static final int MAX_EVENT_NAME                     = 128;              // ��¼���
    public static final int NET_MAX_ETH_NAME                   = 64;              // ���������
    public static final int NET_N_SCHEDULE_TSECT               = 8;                // ʱ���Ԫ�ظ���    
    public static final int NET_MAX_URL_NUM                    = 8;                // URL������
    public static final int NET_MAX_LOWER_MITRIX_NUM           = 16;               // �����λ������
    public static final int NET_MAX_BURN_CHANNEL_NUM           = 32;               // ����¼ͨ����
    public static final int NET_MAX_NET_STRORAGE_BLOCK_NUM     = 64;               // ���Զ�̴洢��������
    public static final int NET_MAX_CASE_PERSON_NUM            = 32;               // ������Ա�������
    public static final int NET_MAX_MULTIPLAYBACK_CHANNEL_NUM  = 64;               // ����ͨ��Ԥ���ط�ͨ����
    public static final int NET_MAX_MULTIPLAYBACK_SPLIT_NUM    = 32;               // ����ͨ��Ԥ���طŷָ�ģʽ��
    public static final int NET_MAX_AUDIO_ENCODE_TYPE          = 64;               // ��������������͸���
    public static final int MAX_CARD_RECORD_FIELD_NUM          = 16;               // ����¼�����������
    public static final int NET_BATTERY_NUM_MAX                = 16;               // ���������    
    public static final int NET_POWER_NUM_MAX                  = 16;               // ����Դ����        
    public static final int NET_MAX_AUDIO_PATH                 = 260;              // �����Ƶ�ļ�·����
    public static final int NET_MAX_DOORNAME_LEN               = 128;              // ����Ž����Ƴ���    
    public static final int NET_MAX_CARDPWD_LEN                = 64;               // ����Ž����Ƴ���    
    public static final int NET_MAX_FISHEYE_MOUNTMODE_NUM      = 4;                // ������۰�װģʽ����
    public static final int NET_MAX_FISHEYE_CALIBRATEMODE_NUM  = 16;               // ������۽���ģʽ����
    public static final int NET_MAX_FISHEYE_EPTZCMD_NUM        = 64;               // ������۵�����̨��������   
    public static final int POINT_NUM_IN_PAIR                  = 2;                // �궨����еĵ�����
    public static final int MAX_POINT_PAIR_NUM                 = 128;              // �궨���������
    public static final int CHANNEL_NUM_IN_POINT_GROUP         = 2;                // �궨���е���Ƶͨ����
    public static final int MAX_POINT_GROUP_NUM                = 32;               // �궨�����������, ÿ����ͨ������ƴ����Ҫһ��궨��
    public static final int MAX_LANE_INFO_NUM                  = 32;               // ��󳵵���Ϣ��
    public static final int MAX_LANE_DIRECTION_NUM             = 8;                // ������������
    public static final int NET_MAX_MONITORWALL_NUM            = 32;               // ����ǽ�������
    public static final int NET_MAX_OPTIONAL_URL_NUM           = 8;                // ����url�������
    public static final int NET_MAX_CAMERA_CHANNEL_NUM         = 1024;             // ��������ͨ����
    public static final int MAX_FILE_SUMMARY_NUM               = 32;               // ����ļ�ժҪ��
    public static final int MAX_AUDIO_ENCODE_NUM               = 64;               // ���֧����Ƶ�������

    public static final int MAX_FLASH_LIGHT_NUM                = 8;                // ���֧�ֵı�����(�����)����
    public static final int MAX_STROBOSCOPIC_LIGHT_NUM         = 8;                // ���֧�ֵ�Ƶ���Ƹ���
    public static final int MAX_MOSAIC_NUM					   = 8;				   // ���֧�ֵ�����������
    public static final int MAX_MOSAIC_CHANNEL_NUM			   = 256;			   // ֧�������˵��ӵ����ͨ������
    public static final int MAX_FIREWARNING_INFO_NUM           = 4;                // ����ȳ����Ż�㱨����Ϣ����
    public static final int MAX_AXLE_NUM                       = 8;                // ���������

    public static final int NET_MAX_BULLET_HOLES               = 10;               // ���ĵ����� 

    public static final int MAX_PLATE_NUM                      = 64;               // ÿ��ͼƬ�а���������Ƹ���
    public static final int MAX_PREVIEW_CHANNEL_NUM            = 64;               // ��󵼲�Ԥ����ͨ������ 

    public static final int MAX_EVENT_RESTORE_UUID			   = 36;			   // �¼��ش�uuid�����С
    public static final int MAX_EVENT_RESTORE_CODE_NUM         = 8;			   // ����¼��ش����͸���
    public static final int MAX_EVENT_RESOTER_CODE_TYPE	       = 32;			   // �¼��ش����������С
    public static final int MAX_SNAP_TYPE                      = 3;                // ץͼ��������
    public static final int MAX_MAINFORMAT_NUM                 = 4;                // ���֧����������������

    public static final int CUSTOM_TITLE_LEN				   = 1024;			   // �Զ���������Ƴ���(���䵽1024)
    public static final int MAX_CUSTOM_TITLE_NUM    		   = 8;                // ��������Զ�������������
    public static final int FORMAT_TYPE_LEN					   = 16;			   // ������������󳤶�

    public static final int MAX_CHANNEL_NAME_LEN			   = 256;     		   // ͨ��������󳤶�

    public static final int MAX_VIRTUALINFO_DOMAIN_LEN		   = 64;				   // �������������������
    public static final int MAX_VIRTUALINFO_TITLE_LEN		   = 64;				   // ��������������ⳤ��
    public static final int MAX_VIRTUALINFO_USERNAME_LEN	   = 32;				   // ��������û�������
    public static final int MAX_VIRTUALINFO_PASSWORD_LEN	   = 32;				   // ����������볤��
    public static final int MAX_VIRTUALINFO_PHONENUM_LEN	   = 12;				   // ��������ֻ��ų���
    public static final int MAX_VIRTUALINFO_IMEI_LEN	       = 16;				   // ������ݹ����ƶ��豸��ʶ����
    public static final int MAX_VIRTUALINFO_IMSI_LEN	       = 16;				   // ������ݹ����ƶ��û�ʶ���볤��
    public static final int MAX_VIRTUALINFO_LATITUDE_LEN	   = 16;				   // ������ݾ��ȳ���
    public static final int MAX_VIRTUALINFO_LONGITUDE_LEN	   = 16;				   // �������γ�ȳ���
    public static final int MAX_VIRTUALINFO_NUM				   = 1024;              // ������������Ϣ����

    public static final int MAX_CALL_ID_LEN					   = 64;				   // ����ID����
    
    // ��ѯ����,��ӦCLIENT_QueryDevState�ӿ�
    public static final int NET_DEVSTATE_COMM_ALARM            = 0x0001;           // ��ѯ��ͨ����״̬(�����ⲿ����,��Ƶ��ʧ,��̬���)
    public static final int NET_DEVSTATE_SHELTER_ALARM         = 0x0002;           // ��ѯ�ڵ�����״̬
    public static final int NET_DEVSTATE_RECORDING             = 0x0003;           // ��ѯ¼��״̬
    public static final int NET_DEVSTATE_DISK                  = 0x0004;           // ��ѯӲ����Ϣ
    public static final int NET_DEVSTATE_RESOURCE              = 0x0005;           // ��ѯϵͳ��Դ״̬
    public static final int NET_DEVSTATE_BITRATE               = 0x0006;           // ��ѯͨ������
    public static final int NET_DEVSTATE_CONN                  = 0x0007;           // ��ѯ�豸����״̬
    public static final int NET_DEVSTATE_PROTOCAL_VER          = 0x0008;           // ��ѯ����Э��汾��,pBuf = int*
    public static final int NET_DEVSTATE_TALK_ECTYPE           = 0x0009;           // ��ѯ�豸֧�ֵ������Խ���ʽ�б�,���ṹ��NETDEV_TALKFORMAT_LIST
    public static final int NET_DEVSTATE_SD_CARD               = 0x000A;           // ��ѯSD����Ϣ(IPC���Ʒ)
    public static final int NET_DEVSTATE_BURNING_DEV           = 0x000B;           // ��ѯ��¼����Ϣ
    public static final int NET_DEVSTATE_BURNING_PROGRESS      = 0x000C;           // ��ѯ��¼����
    public static final int NET_DEVSTATE_PLATFORM              = 0x000D;           // ��ѯ�豸֧�ֵĽ���ƽ̨
    public static final int NET_DEVSTATE_CAMERA                = 0x000E;           // ��ѯ����ͷ������Ϣ(IPC���Ʒ),pBuf = NETDEV_CAMERA_INFO *,�����ж���ṹ��
    public static final int NET_DEVSTATE_SOFTWARE              = 0x000F;           // ��ѯ�豸����汾��Ϣ
    public static final int NET_DEVSTATE_LANGUAGE              = 0x0010;           // ��ѯ�豸֧�ֵ���������
    public static final int NET_DEVSTATE_DSP                   = 0x0011;           // ��ѯDSP��������(��Ӧ�ṹ��NET_DEV_DSP_ENCODECAP)
    public static final int NET_DEVSTATE_OEM                   = 0x0012;           // ��ѯOEM��Ϣ
    public static final int NET_DEVSTATE_NET                   = 0x0013;           // ��ѯ��������״̬��Ϣ
    public static final int NET_DEVSTATE_TYPE                  = 0x0014;           // ��ѯ�豸����
    public static final int NET_DEVSTATE_SNAP                  = 0x0015;           // ��ѯ��������(IPC���Ʒ)
    public static final int NET_DEVSTATE_RECORD_TIME           = 0x0016;           // ��ѯ����¼��ʱ������¼��ʱ��
    public static final int NET_DEVSTATE_NET_RSSI              = 0x0017;           // ��ѯ���������ź�ǿ��,���ṹ��NETDEV_WIRELESS_RSS_INFO
    public static final int NET_DEVSTATE_BURNING_ATTACH        = 0x0018;           // ��ѯ������¼ѡ��
    public static final int NET_DEVSTATE_BACKUP_DEV            = 0x0019;           // ��ѯ�����豸�б�
    public static final int NET_DEVSTATE_BACKUP_DEV_INFO       = 0x001a;           // ��ѯ�����豸��ϸ��Ϣ NETDEV_BACKUP_INFO
    public static final int NET_DEVSTATE_BACKUP_FEEDBACK       = 0x001b;           // ���ݽ��ȷ���
    public static final int NET_DEVSTATE_ATM_QUERY_TRADE       = 0x001c;           // ��ѯATM��������
    public static final int NET_DEVSTATE_SIP                   = 0x001d;           // ��ѯsip״̬
    public static final int NET_DEVSTATE_VICHILE_STATE         = 0x001e;           // ��ѯ����wifi״̬
    public static final int NET_DEVSTATE_TEST_EMAIL            = 0x001f;           // ��ѯ�ʼ������Ƿ�ɹ�
    public static final int NET_DEVSTATE_SMART_HARD_DISK       = 0x0020;           // ��ѯӲ��smart��Ϣ
    public static final int NET_DEVSTATE_TEST_SNAPPICTURE      = 0x0021;           // ��ѯץͼ�����Ƿ�ɹ�
    public static final int NET_DEVSTATE_STATIC_ALARM          = 0x0022;           // ��ѯ��̬����״̬
    public static final int NET_DEVSTATE_SUBMODULE_INFO        = 0x0023;           // ��ѯ�豸��ģ����Ϣ
    public static final int NET_DEVSTATE_DISKDAMAGE            = 0x0024;           // ��ѯӲ�̻������� 
    public static final int NET_DEVSTATE_IPC                   = 0x0025;           // ��ѯ�豸֧�ֵ�IPC����, ���ṹ��NET_DEV_IPC_INFO
    public static final int NET_DEVSTATE_ALARM_ARM_DISARM      = 0x0026;           // ��ѯ����������״̬
    public static final int NET_DEVSTATE_ACC_POWEROFF_ALARM    = 0x0027;           // ��ѯACC�ϵ籨��״̬(����һ��DWORD, 1��ʾ�ϵ�,0��ʾͨ��)
    public static final int NET_DEVSTATE_TEST_FTP_SERVER       = 0x0028;           // ����FTP����������
    public static final int NET_DEVSTATE_3GFLOW_EXCEED         = 0x0029;           // ��ѯ3G����������ֵ״̬,(���ṹ�� NETDEV_3GFLOW_EXCEED_STATE_INFO)
    public static final int NET_DEVSTATE_3GFLOW_INFO           = 0x002a;           // ��ѯ3G����������Ϣ,���ṹ�� NET_DEV_3GFLOW_INFO
    public static final int NET_DEVSTATE_VIHICLE_INFO_UPLOAD   = 0x002b;           // �����Զ�����Ϣ�ϴ�(���ṹ�� ALARM_VEHICLE_INFO_UPLOAD)
    public static final int NET_DEVSTATE_SPEED_LIMIT           = 0x002c;           // ��ѯ���ٱ���״̬(���ṹ��ALARM_SPEED_LIMIT)
    public static final int NET_DEVSTATE_DSP_EX                = 0x002d;           // ��ѯDSP��չ��������(��Ӧ�ṹ�� NET_DEV_DSP_ENCODECAP_EX)
    public static final int NET_DEVSTATE_3GMODULE_INFO         = 0x002e;           // ��ѯ3Gģ����Ϣ(��Ӧ�ṹ��NET_DEV_3GMODULE_INFO)
    public static final int NET_DEVSTATE_MULTI_DDNS            = 0x002f;           // ��ѯ��DDNS״̬��Ϣ(��Ӧ�ṹ��NET_DEV_MULTI_DDNS_INFO)
    public static final int NET_DEVSTATE_CONFIG_URL            = 0x0030;           // ��ѯ�豸����URL��Ϣ(��Ӧ�ṹ��NET_DEV_URL_INFO)
    public static final int NET_DEVSTATE_HARDKEY               = 0x0031;           // ��ѯHardKey״̬����Ӧ�ṹ��NETDEV_HARDKEY_STATE)
    public static final int NET_DEVSTATE_ISCSI_PATH            = 0x0032;           // ��ѯISCSI·���б�(��Ӧ�ṹ��NETDEV_ISCSI_PATHLIST)
    public static final int NET_DEVSTATE_DLPREVIEW_SLIPT_CAP   = 0x0033;           // ��ѯ�豸����Ԥ��֧�ֵķָ�ģʽ(��Ӧ�ṹ��DEVICE_LOCALPREVIEW_SLIPT_CAP)
    public static final int NET_DEVSTATE_WIFI_ROUTE_CAP        = 0x0034;           // ��ѯ����·��������Ϣ(��Ӧ�ṹ��NETDEV_WIFI_ROUTE_CAP)
    public static final int NET_DEVSTATE_ONLINE                = 0x0035;           // ��ѯ�豸������״̬(����һ��DWORD, 1��ʾ����, 0��ʾ����)
    public static final int NET_DEVSTATE_PTZ_LOCATION          = 0x0036;           // ��ѯ��̨״̬��Ϣ(��Ӧ�ṹ�� NET_PTZ_LOCATION_INFO)
    public static final int NET_DEVSTATE_MONITOR_INFO          = 0x0037;           // �����ظ�����Ϣ(��Ӧ�ṹ��NETDEV_MONITOR_INFO)
    public static final int NET_DEVSTATE_SUBDEVICE             = 0x0300;           // ��ѯ���豸(��Դ, ���ȵ�)״̬(��Ӧ�ṹ��CFG_DEVICESTATUS_INFO)
    public static final int NET_DEVSTATE_RAID_INFO             = 0x0038;           // ��ѯRAID״̬(��Ӧ�ṹ��ALARM_RAID_INFO)  
    public static final int NET_DEVSTATE_TEST_DDNSDOMAIN       = 0x0039;           // ����DDNS�����Ƿ����
    public static final int NET_DEVSTATE_VIRTUALCAMERA         = 0x003a;           // ��ѯ��������ͷ״̬(��Ӧ NETDEV_VIRTUALCAMERA_STATE_INFO)
    public static final int NET_DEVSTATE_TRAFFICWORKSTATE      = 0x003b;           // ��ȡ�豸������Ƶ/��Ȧģʽ״̬��(��ӦNETDEV_TRAFFICWORKSTATE_INFO)
    public static final int NET_DEVSTATE_ALARM_CAMERA_MOVE     = 0x003c;           // ��ȡ�������λ�����¼�״̬(��ӦALARM_CAMERA_MOVE_INFO)
    public static final int NET_DEVSTATE_ALARM                 = 0x003e;           // ��ȡ�ⲿ����״̬(��Ӧ NET_CLIENT_ALARM_STATE) 
    public static final int NET_DEVSTATE_VIDEOLOST             = 0x003f;           // ��ȡ��Ƶ��ʧ����״̬(��Ӧ NET_CLIENT_VIDEOLOST_STATE) 
    public static final int NET_DEVSTATE_MOTIONDETECT          = 0x0040;           // ��ȡ��̬��ⱨ��״̬(��Ӧ NET_CLIENT_MOTIONDETECT_STATE)
    public static final int NET_DEVSTATE_DETAILEDMOTION        = 0x0041;           // ��ȡ��ϸ�Ķ�̬��ⱨ��״̬(��Ӧ NET_CLIENT_DETAILEDMOTION_STATE)
    public static final int NET_DEVSTATE_VEHICLE_INFO          = 0x0042;           // ��ȡ�����������Ӳ����Ϣ(��Ӧ NETDEV_VEHICLE_INFO)
    public static final int NET_DEVSTATE_VIDEOBLIND            = 0x0043;           // ��ȡ��Ƶ�ڵ�����״̬(��Ӧ NET_CLIENT_VIDEOBLIND_STATE)
    public static final int NET_DEVSTATE_3GSTATE_INFO          = 0x0044;           // ��ѯ3Gģ�������Ϣ(��Ӧ�ṹ��NETDEV_VEHICLE_3GMODULE)
    public static final int NET_DEVSTATE_NETINTERFACE          = 0x0045;           // ��ѯ����ӿ���Ϣ(��Ӧ NETDEV_NETINTERFACE_INFO)

    public static final int NET_DEVSTATE_PICINPIC_CHN          = 0x0046;           // ��ѯ���л�ͨ����(��ӦDWORD����)
    public static final int NET_DEVSTATE_COMPOSITE_CHN         = 0x0047;           // ��ѯ�ں���ͨ����Ϣ(��ӦDH_COMPOSITE_CHANNEL����)
    public static final int NET_DEVSTATE_WHOLE_RECORDING       = 0x0048;           // ��ѯ�豸����¼��״̬(��ӦBOOL), ֻҪ��һ��ͨ����¼��,��Ϊ�豸����״̬Ϊ¼��
    public static final int NET_DEVSTATE_WHOLE_ENCODING        = 0x0049;           // ��ѯ�豸�������״̬(��ӦBOOL), ֻҪ��һ��ͨ���ڱ���,��Ϊ�豸����״̬Ϊ����
    public static final int NET_DEVSTATE_DISK_RECORDE_TIME     = 0x004a;           // ��ѯ�豸Ӳ��¼��ʱ����Ϣ(pBuf = DEV_DISK_RECORD_TIME*,�����ж���ṹ��)
    public static final int NET_DEVSTATE_BURNER_DOOR           = 0x004b;           // �Ƿ��ѵ�����¼��������(��Ӧ�ṹ�� NET_DEVSTATE_BURNERDOOR)
    public static final int NET_DEVSTATE_GET_DATA_CHECK        = 0x004c;           // ��ѯ��������У�����(��Ӧ NET_DEVSTATE_DATA_CHECK)
    public static final int NET_DEVSTATE_ALARM_IN_CHANNEL      = 0x004f;           // ��ѯ��������ͨ����Ϣ(��ӦNET_ALARM_IN_CHANNEL����)
    public static final int NET_DEVSTATE_ALARM_CHN_COUNT       = 0x0050;           // ��ѯ����ͨ����(��ӦNET_ALARM_CHANNEL_COUNT)
    public static final int NET_DEVSTATE_PTZ_VIEW_RANGE        = 0x0051;           // ��ѯ��̨������״̬(��Ӧ NET_OUT_PTZ_VIEW_RANGE_STATUS	)
    public static final int NET_DEVSTATE_DEV_CHN_COUNT         = 0x0052;           // ��ѯ�豸ͨ����Ϣ(��ӦNET_DEV_CHN_COUNT_INFO)
    public static final int NET_DEVSTATE_RTSP_URL              = 0x0053;           // ��ѯ�豸֧�ֵ�RTSP URL�б�,���ṹ��DEV_RTSPURL_LIST
    public static final int NET_DEVSTATE_LIMIT_LOGIN_TIME      = 0x0054;           // ��ѯ�豸��¼�����߳�ʱʱ��,����һ��BTYE,����λ�����ӣ� ,0��ʾ������,������������ʾ���Ƶķ�����
    public static final int NET_DEVSTATE_GET_COMM_COUNT        = 0x0055;           // ��ȡ������ ���ṹ��NET_GET_COMM_COUNT
    public static final int NET_DEVSTATE_RECORDING_DETAIL      = 0x0056;           // ��ѯ¼��״̬��ϸ��Ϣ(pBuf = NET_RECORD_STATE_DETAIL*)
    public static final int NET_DEVSTATE_PTZ_PRESET_LIST       = 0x0057;           // ��ȡ��ǰ��̨��Ԥ�õ��б�(��Ӧ�ṹNET_PTZ_PRESET_LIST)
    public static final int NET_DEVSTATE_EXTERNAL_DEVICE       = 0x0058;           // ����豸��Ϣ(pBuf = NET_EXTERNAL_DEVICE*)
    public static final int NET_DEVSTATE_GET_UPGRADE_STATE     = 0x0059;           // ��ȡ�豸����״̬(��Ӧ�ṹ NETDEV_UPGRADE_STATE_INFO)
    public static final int NET_DEVSTATE_MULTIPLAYBACK_SPLIT_CAP = 0x005a;         // ��ȡ��ͨ��Ԥ���ָ�����( ��Ӧ�ṹ�� NET_MULTIPLAYBACK_SPLIT_CAP )
    public static final int NET_DEVSTATE_BURN_SESSION_NUM      = 0x005b;           // ��ȡ��¼�Ự����(pBuf = int*)
    public static final int NET_DEVSTATE_PROTECTIVE_CAPSULE    = 0X005c;           // ��ѯ������״̬(��Ӧ�ṹ��ALARM_PROTECTIVE_CAPSULE_INFO)
    public static final int NET_DEVSTATE_GET_DOORWORK_MODE     = 0X005d;           // ��ȡ��������ģʽ( ��Ӧ NET_GET_DOORWORK_MODE)
    public static final int NET_DEVSTATE_PTZ_ZOOM_INFO         = 0x005e;           // ��ѯ��̨��ȡ��ѧ�䱶��Ϣ(��Ӧ NET_OUT_PTZ_ZOOM_INFO )

    public static final int NET_DEVSTATE_POWER_STATE           = 0x0152;           // ��ѯ��Դ״̬(��Ӧ�ṹ��NET_POWER_STATUS)
    public static final int NET_DEVSTATE_ALL_ALARM_CHANNELS_STATE  = 0x153;        // ��ѯ����ͨ��״̬(��Ӧ�ṹ�� NET_CLIENT_ALARM_CHANNELS_STATE)
    public static final int NET_DEVSTATE_ALARMKEYBOARD_COUNT   = 0x0154;           // ��ѯ���������ӵı���������(��Ӧ�ṹ��NET_ALARMKEYBOARD_COUNT)
    public static final int NET_DEVSTATE_EXALARMCHANNELS       = 0x0155;           // ��ѯ��չ����ģ��ͨ��ӳ���ϵ(��Ӧ�ṹ�� NET_EXALARMCHANNELS)
    public static final int NET_DEVSTATE_GET_BYPASS            = 0x0156;           // ��ѯͨ����·״̬(��Ӧ�ṹ�� NET_DEVSTATE_GET_BYPASS)
    public static final int NET_DEVSTATE_ACTIVATEDDEFENCEAREA  = 0x0157;           // ��ȡ����ķ�����Ϣ(��Ӧ�ṹ�� NET_ACTIVATEDDEFENCEAREA)
    public static final int NET_DEVSTATE_DEV_RECORDSET         = 0x0158;           // ��ѯ�豸��¼����Ϣ(��Ӧ NET_CTRL_RECORDSET_PARAM)
    public static final int NET_DEVSTATE_DOOR_STATE            = 0x0159;           // ��ѯ�Ž�״̬(��ӦNET_DOOR_STATUS_INFO)
    public static final int NET_DEVSTATE_ANALOGALARM_CHANNELS  = 0x1560;           // ģ������������ͨ��ӳ���ϵ(��ӦNET_ANALOGALARM_CHANNELS)
    public static final int NET_DEVSTATE_GET_SENSORLIST        = 0x1561;           // ��ȡ�豸֧�ֵĴ������б�(��Ӧ NET_SENSOR_LIST)
    public static final int NET_DEVSTATE_ALARM_CHANNELS        = 0x1562;           // ��ѯ����������ģ��ͨ��ӳ���ϵ(��Ӧ�ṹ�� NET_ALARM_CHANNELS)
    																			   // ����豸��֧�ֲ�ѯ��չ����ģ��ͨ��,�����øù��ܲ�ѯ��չͨ�����߼�ͨ����,���������ر���ͨ��ʹ��
    public static final int NET_DEVSTATE_GET_ALARM_SUBSYSTEM_ACTIVATESTATUS = 0x1563;  // ��ȡ��ǰ��ϵͳ����״̬( ��Ӧ NET_GET_ALARM_SUBSYSTEM_ACTIVATE_STATUES)
    public static final int NET_DEVSTATE_AIRCONDITION_STATE    = 0x1564;           // ��ȡ�յ�����״̬(��Ӧ NET_GET_AIRCONDITION_STATE)
    public static final int NET_DEVSTATE_ALARMSUBSYSTEM_STATE  = 0x1565;           // ��ȡ��ϵͳ״̬(��ӦNET_ALARM_SUBSYSTEM_STATE)
    public static final int NET_DEVSTATE_ALARM_FAULT_STATE     = 0x1566;           // ��ȡ����״̬(��Ӧ NET_ALARM_FAULT_STATE_INFO)
    public static final int NET_DEVSTATE_DEFENCE_STATE         = 0x1567;           // ��ȡ����״̬(��Ӧ NET_DEFENCE_STATE_INFO, ����·״̬�仯�¼������ر����¼��������ź�Դ�¼���״̬����������,���ܻ���,�������豸ʹ��)
    public static final int NET_DEVSTATE_CLUSTER_STATE         = 0x1568;           // ��ȡ��Ⱥ״̬(��Ӧ NET_CLUSTER_STATE_INFO)
    public static final int NET_DEVSTATE_SCADA_POINT_LIST      = 0x1569;           // ��ȡ��λ��·����Ϣ(��Ӧ NET_SCADA_POINT_LIST_INFO)
    public static final int NET_DEVSTATE_SCADA_INFO            = 0x156a;           // ��ȡ����λ��Ϣ(��Ӧ NET_SCADA_INFO)
    public static final int NET_DEVSTATE_SCADA_CAPS            = 0X156b;           // ��ȡSCADA������(��Ӧ NET_SCADA_CAPS)
    public static final int NET_DEVSTATE_GET_CODEID_COUNT      = 0x156c;           // ��ȡ����ɹ���������(��Ӧ NET_GET_CODEID_COUNT)
    public static final int NET_DEVSTATE_GET_CODEID_LIST       = 0x156d;           // ��ѯ������Ϣ(��Ӧ NET_GET_CODEID_LIST)
    public static final int NET_DEVSTATE_ANALOGALARM_DATA      = 0x156e;           // ��ѯģ����ͨ������(��Ӧ NET_GET_ANALOGALARM_DATA)
    public static final int NET_DEVSTATE_VTP_CALLSTATE         = 0x156f;           // ��ȡ��Ƶ�绰����״̬(��Ӧ NET_GET_VTP_CALLSTATE)
    public static final int NET_DEVSTATE_SCADA_INFO_BY_ID      = 0x1570;           // ͨ���豸����ȡ����λ��Ϣ(��Ӧ NET_SCADA_INFO_BY_ID)
    public static final int NET_DEVSTATE_SCADA_DEVICE_LIST     = 0x1571;           // ��ȡ��ǰ������������ⲿ�豸ID(��Ӧ NET_SCADA_DEVICE_LIST)
    public static final int NET_DEVSTATE_DEV_RECORDSET_EX      = 0x1572;           // ��ѯ�豸��¼����Ϣ(������������)(��ӦNET_CTRL_RECORDSET_PARAM)
    public static final int NET_DEVSTATE_ACCESS_LOCK_VER       = 0x1573;           // ��ȡ��������汾��(��Ӧ NET_ACCESS_LOCK_VER)
    public static final int NET_DEVSTATE_MONITORWALL_TVINFO    = 0x1574;           // ��ȡ����ǽ��ʾ��Ϣ(��Ӧ NET_CTRL_MONITORWALL_TVINFO)
    public static final int NET_DEVSTATE_GET_ALL_POS           = 0x1575;           // ��ȡ�����û�����Pos�豸������Ϣ(��Ӧ NET_POS_ALL_INFO)
    public static final int NET_DEVSTATE_GET_ROAD_LIST         = 0x1576;           // ��ȡ���м�·�α�����Ϣ,���ױ�����Ŀר��(��Ӧ NET_ROAD_LIST_INFO)
    public static final int NET_DEVSTATE_GET_HEAT_MAP          = 0x1577;           // ��ȡ�ȶ�ͳ����Ϣ(��Ӧ NET_QUERY_HEAT_MAP)
    public static final int NET_DEVSTATE_GET_WORK_STATE        = 0x1578;           // ��ȡ���ӹ���״̬��Ϣ(��Ӧ NET_QUERY_WORK_STATE)
    public static final int NET_DEVSTATE_GET_WIRESSLESS_STATE  = 0x1579;           // ��ȡ�����豸״̬��Ϣ(��Ӧ NET_GET_WIRELESS_DEVICE_STATE)
    public static final int NET_DEVSTATE_GET_REDUNDANCE_POWER_INFO = 0x157a;       // ��ȡ�����Դ��Ϣ(��Ӧ NET_GET_REDUNDANCE_POWER_INFO)

 
    // ��ѯ�豸��Ϣ����, ��Ӧ�ӿ� CLIENT_QueryDevInfo
    // �豸��Ϣ����,��ӦCLIENT_QueryDevInfo�ӿ�
    public static final int NET_QUERY_DEV_STORAGE_NAMES                 = 0x01;                // ��ѯ�豸�Ĵ洢ģ�����б� , pInBuf=NET_IN_STORAGE_DEV_NAMES *, pOutBuf=NET_OUT_STORAGE_DEV_NAMES *
    public static final int NET_QUERY_DEV_STORAGE_INFOS                 = 0x02;                // ��ѯ�豸�Ĵ洢ģ����Ϣ�б�, pInBuf=NET_IN_STORAGE_DEV_INFOS*, pOutBuf= NET_OUT_STORAGE_DEV_INFOS *
    public static final int NET_QUERY_RECENCY_JNNCTION_CAR_INFO         = 0x03;                // ��ѯ����Ŀ��ڳ�����Ϣ�ӿ�, pInBuf=NET_IN_GET_RECENCY_JUNCTION_CAR_INFO*, pOutBuf=NET_OUT_GET_RECENCY_JUNCTION_CAR_INFO*
    public static final int NET_QUERY_LANES_STATE                       = 0x04;                // ��ѯ������Ϣ,pInBuf = NET_IN_GET_LANES_STATE , pOutBuf = NET_OUT_GET_LANES_STATE
    public static final int NET_QUERY_DEV_FISHEYE_WININFO               = 0x05;                // ��ѯ���۴�����Ϣ , pInBuf= NET_IN_FISHEYE_WININFO*, pOutBuf=NET_OUT_FISHEYE_WININFO *
    public static final int NET_QUERY_DEV_REMOTE_DEVICE_INFO            = 0x06;;               // ��ѯԶ���豸��Ϣ , pInBuf= NET_IN_GET_DEVICE_INFO*, pOutBuf= NET_OUT_GET_DEVICE_INFO *
    public static final int NET_QUERY_SYSTEM_INFO                       = 0x07;                // ��ѯ�豸ϵͳ��Ϣ , pInBuf= NET_IN_SYSTEM_INFO*, pOutBuf= NET_OUT_SYSTEM_INFO*
    public static final int NET_QUERY_REG_DEVICE_NET_INFO               = 0x08;                // ��ѯ����ע���豸���������� , pInBuf=NET_IN_REGDEV_NET_INFO * , pOutBuf=NET_OUT_REGDEV_NET_INFO *
    public static final int NET_QUERY_DEV_THERMO_GRAPHY_PRESET          = 0x09;                // ��ѯ�ȳ���Ԥ����Ϣ , pInBuf= NET_IN_THERMO_GET_PRESETINFO*, pOutBuf= NET_OUT_THERMO_GET_PRESETINFO *
    public static final int NET_QUERY_DEV_THERMO_GRAPHY_OPTREGION       = 0x0a;                // ��ѯ�ȳ������Ȥ������Ϣ,pInBuf= NET_IN_THERMO_GET_OPTREGION*, pOutBuf= NET_OUT_THERMO_GET_OPTREGION *
    public static final int NET_QUERY_DEV_THERMO_GRAPHY_EXTSYSINFO      = 0x0b;                // ��ѯ�ȳ����ⲿϵͳ��Ϣ, pInBuf= NET_IN_THERMO_GET_EXTSYSINFO*, pOutBuf= NET_OUT_THERMO_GET_EXTSYSINFO *
    public static final int NET_QUERY_DEV_RADIOMETRY_POINT_TEMPER       = 0x0c;                // ��ѯ���µ�Ĳ���ֵ, pInBuf= NET_IN_RADIOMETRY_GETPOINTTEMPER*, pOutBuf= NET_OUT_RADIOMETRY_GETPOINTTEMPER *
    public static final int NET_QUERY_DEV_RADIOMETRY_TEMPER             = 0x0d;                // ��ѯ������Ĳ���ֵ, pInBuf= NET_IN_RADIOMETRY_GETTEMPER*, pOutBuf= NET_OUT_RADIOMETRY_GETTEMPER *
    public static final int NET_QUERY_GET_CAMERA_STATE                  = 0x0e;                // ��ȡ�����״̬, pInBuf= NET_IN_GET_CAMERA_STATEINFO*, pOutBuf= NET_OUT_GET_CAMERA_STATEINFO *
    public static final int NET_QUERY_GET_REMOTE_CHANNEL_AUDIO_ENCODE   = 0x0f;                // ��ȡԶ��ͨ����Ƶ���뷽ʽ, pInBuf= NET_IN_GET_REMOTE_CHANNEL_AUDIO_ENCODEINFO*, pOutBuf= NET_OUT_GET_REMOTE_CHANNEL_AUDIO_ENCODEINFO *
    public static final int NET_QUERY_GET_COMM_PORT_INFO                = 0x10;                // ��ȡ�豸������Ϣ, pInBuf=NET_IN_GET_COMM_PORT_INFO* , pOutBuf=NET_OUT_GET_COMM_PORT_INFO* 
    public static final int NET_QUERY_GET_LINKCHANNELS                  = 0x11;                // ��ѯĳ��Ƶͨ���Ĺ���ͨ���б�,pInBuf=NET_IN_GET_LINKCHANNELS* , pOutBuf=NET_OUT_GET_LINKCHANNELS*
    public static final int NET_QUERY_GET_VIDEOOUTPUTCHANNELS           = 0x12;                // ��ȡ����ͨ������ͳ����Ϣ, pInBuf=NET_IN_GET_VIDEOOUTPUTCHANNELS*, pOutBuf=NET_OUT_GET_VIDEOOUTPUTCHANNELS*
    public static final int NET_QUERY_GET_VIDEOINFO                     = 0x13;                // ��ȡ����ͨ����Ϣ, pInBuf=NET_IN_GET_VIDEOINFO*, pOutBuf=NET_OUT_GET_VIDEOINFO*
    public static final int NET_QUERY_GET_ALLLINKCHANNELS               = 0x14;                // ��ѯȫ����Ƶ����ͨ���б�,pInBuf=NET_IN_GET_ALLLINKCHANNELS* , pOutBuf=NET_OUT_GET_ALLLINKCHANNELS*
    public static final int NET_QUERY_VIDEOCHANNELSINFO                 = 0x15;                // ��ѯ��Ƶͨ����Ϣ,pInBuf=NET_IN_GET_VIDEOCHANNELSINFO* , pOutBuf=NET_OUT_GET_VIDEOCHANNELSINFO*
    public static final int NET_QUERY_TRAFFICRADAR_VERSION              = 0x16;                // ��ѯ�״��豸�汾,pInBuf=NET_IN_TRAFFICRADAR_VERSION* , pOutBuf=NET_OUT_TRAFFICRADAR_VERSION*
    public static final int NET_QUERY_WORKGROUP_NAMES                   = 0x17;                // ��ѯ���еĹ���Ŀ¼����,pInBuf=NET_IN_WORKGROUP_NAMES* , pOutBuf=NET_OUT_WORKGROUP_NAMES*
    public static final int NET_QUERY_WORKGROUP_INFO                    = 0x18;                // ��ѯ��������Ϣ,pInBuf=NET_IN_WORKGROUP_INFO* , pOutBuf=NET_OUT_WORKGROUP_INFO*
    public static final int NET_QUERY_WLAN_ACCESSPOINT                  = 0x19;                // ��ѯ��������������Ϣ,pInBuf=NET_IN_WLAN_ACCESSPOINT* , pOutBuf=NET_OUT_WLAN_ACCESSPOINT*
    public static final int NET_QUERY_GPS_INFO							= 0x1a;				   // ��ѯ�豸GPS��Ϣ,pInBuf=NET_IN_DEV_GPS_INFO* , pOutBuf=NET_OUT_DEV_GPS_INFO*
    public static final int NET_QUERY_IVS_REMOTE_DEVICE_INFO            = 0x1b;                // ��ѯIVS��ǰ���豸��������Զ���豸��Ϣ, pInBuf = NET_IN_IVS_REMOTE_DEV_INFO*, pOutBuf = NET_OUT_IVS_REMOTE_DEV_INFO*
   
    /////////////////////////////////// ���� ///////////////////////////////////////

    public static final int NET_MATRIX_INTERFACE_LEN          			= 16;          // �źŽӿ����Ƴ���
    public static final int NET_MATRIX_MAX_CARDS             			= 128;         // �����ӿ��������
    public static final int NET_SPLIT_PIP_BASE               			= 1000;        // �ָ�ģʽ���л��Ļ���ֵ
    public static final int NET_MAX_SPLIT_MODE_NUM           			= 64;          // ���ָ�ģʽ��
    public static final int NET_MATRIX_MAX_CHANNEL_IN        			= 1500;        // �����������ͨ����
    public static final int NET_MATRIX_MAX_CHANNEL_OUT       			= 256;         // ����������ͨ����
    public static final int NET_DEVICE_NAME_LEN              			= 64;          // �豸���Ƴ���
    public static final int NET_MAX_CPU_NUM                  			= 16;          // ���CPU����
    public static final int NET_MAX_FAN_NUM                  			= 16;          // ����������
    public static final int NET_MAX_POWER_NUM                			= 16;          // ����Դ����
    public static final int NET_MAX_BATTERY_NUM              			= 16;          // ���������
    public static final int NET_MAX_TEMPERATURE_NUM          			= 256;         // ����¶ȴ���������
    public static final int NET_MAX_ISCSI_NAME_LEN           			= 128;         // ISCSI���Ƴ���
    public static final int NET_VERSION_LEN                  			= 64;          // �汾��Ϣ����
    public static final int NET_MAX_STORAGE_PARTITION_NUM    			= 32;          // �洢�����������
    public static final int NET_STORAGE_MOUNT_LEN            			= 64;          // ���ص㳤��
    public static final int NET_STORAGE_FILE_SYSTEM_LEN      			= 16;          // �ļ�ϵͳ���Ƴ���
    public static final int NET_MAX_MEMBER_PER_RAID          			= 32;          // RAID��Ա�������
    public static final int NET_DEV_ID_LEN_EX                			= 128;         // �豸ID��󳤶�
    public static final int NET_MAX_BLOCK_NUM                			= 32;          // �����������
    public static final int NET_MAX_SPLIT_WINDOW             			= 128;         // ���ָ������
    public static final int NET_FILE_TYPE_LEN                			= 64;          // �ļ����ͳ���
    public static final int NET_DEV_ID_LEN                  			= 128;         // �豸ID��󳤶�
    public static final int NET_DEV_NAME_LEN                 			= 128;         // �豸������󳤶�
    public static final int NET_TSCHE_DAY_NUM                			= 8;           // ʱ����һά��С, ��ʾ����
    public static final int NET_TSCHE_SEC_NUM                			= 6;           // ʱ���ڶ�ά��С, ��ʾʱ����
    public static final int NET_SPLIT_INPUT_NUM              			= 256;         // ˾���豸�����л�ʱ��һ��split֧�ֵ�����ͨ����

    public static final String NET_DEVICE_ID_LOCAL               		= "Local";     // �����豸ID
    public static final String NET_DEVICE_ID_REMOTE              		= "Remote";    // Զ���豸ID
    public static final String NET_DEVICE_ID_UNIQUE             		= "Unique";    // �豸��ͳһ���
    
    //��������
    public static final int NET_MAX_NAME_LEN                    = 16;   // ͨ�������ַ�������
    public static final int NET_MAX_PERSON_ID_LEN               = 32;   // ��Աid��󳤶�
    public static final int NET_MAX_PERSON_IMAGE_NUM            = 48;   // ÿ����Ա��Ӧ���������ͼƬ��
    public static final int NET_MAX_PROVINCE_NAME_LEN           = 64;   // ʡ��������󳤶�
    public static final int NET_MAX_CITY_NAME_LEN               = 64;   // ����������󳤶�
    public static final int NET_MAX_PERSON_NAME_LEN             = 64;   // ��Ա������󳤶�
    public static final int MAX_FACE_AREA_NUM                   = 8;    // ��������������
    public static final int MAX_PATH                            = 260;
    public static final int MAX_FACE_DB_NUM                     = 8;    // ����������ݿ����
    public static final int MAX_GOURP_NUM                       = 128;  // ������������

    public static final int MAX_FIND_COUNT                      = 20;
    public static final int NET_MAX_POLYGON_NUM                 = 16;   // �������󶥵����
    public static final int NET_MAX_CANDIDATE_NUM               = 50;   // ����ʶ�����ƥ����
    public static final int MAX_POLYGON_NUM                     = 20;   // ��Ƶ�����豸���򶥵��������
    public static final int MAX_CALIBRATEBOX_NUM                = 10;   // ���ܷ���У׼���������
    public static final int MAX_NAME_LEN                        = 128;  // ͨ�������ַ�������
    public static final int MAX_EXCLUDEREGION_NUM               = 10;   // ���ܷ��������������Ҫ�ų��������������
    public static final int MAX_OBJECT_LIST_SIZE                = 16;   // ��Ƶ�����豸֧�ֵļ�����������б��������
    public static final int MAX_SPECIALDETECT_NUM               = 10;   // ���ܷ�����������������
    public static final int MAX_OBJECT_ATTRIBUTES_SIZE          = 16;   // ��Ƶ�����豸֧�ֵļ���������������б��������
    public static final int MAX_CATEGORY_TYPE_NUMBER            = 128;  // �����������
    public static final int MAX_ANALYSE_MODULE_NUM              = 16;   // ��Ƶ�����豸�����ģ�����
    public static final int NET_COMMON_STRING_128               = 128;  // ͨ���ַ�������128
    public static final int NET_COMMON_STRING_256               = 256;  // ͨ���ַ�������256
    public static final int MAX_LOG_PATH_LEN                    = 260;  // ��־·������󳤶�
    public static final int MAX_CHANNELNAME_LEN                 = 64;   // ���ͨ�����Ƴ���
    public static final int MAX_VIDEO_CHANNEL_NUM               = 256;  // ���ͨ����256
    public static final int MAX_PSTN_SERVER_NUM                 = 8;    // ��󱨾��绰��������
    public static final int MAX_TIME_SCHEDULE_NUM               = 8;    // ʱ���Ԫ�ظ���
    public static final int MAX_REC_TSECT                       = 6;    // ¼��ʱ��θ���
    public static final int MAX_REC_TSECT_EX                    = 10;   // ¼��ʱ�����չ����
    public static final int MAX_CHANNEL_COUNT                   = 16;
    public static final int MAX_ACCESSCONTROL_NUM               = 8;    // ����Ž������������
    public static final int MAX_DBKEY_NUM                       = 64;   // ���ݿ�ؼ������ֵ
    public static final int MAX_SUMMARY_LEN                     = 1024; // ���ӵ�JPEGͼƬ��ժҪ��Ϣ��󳤶�
    public static final int WEEK_DAY_NUM                        = 7;    // һ�ܵ�����
    public static final int NET_MAX_FACEDETECT_FEATURE_NUM      = 32;   // ��������������
    public static final int NET_MAX_OBJECT_LIST                 = 16;   // ���ܷ����豸��⵽������ID��������    
    public static final int NET_MAX_RULE_LIST                   = 16;   // ���ܷ����豸�����������
    public static final int NET_MAX_DETECT_REGION_NUM           = 20;   // ������������󶥵���
    public static final int NET_MAX_DETECT_LINE_NUM             = 20;   // ����������󶥵���
    public static final int NET_MAX_TRACK_LINE_NUM              = 20;   // �����˶��켣��󶥵���
    public static final int NET_MACADDR_LEN                     = 40;   // MAC��ַ�ַ�������
    public static final int NET_DEV_TYPE_LEN                    = 32;   // �豸�ͺ��ַ�������"IPC-F725"������
    public static final int NET_DEV_SERIALNO_LEN                = 48;   // ���к��ַ�������
    public static final int NET_MAX_URL_LEN                     = 128;  // URL�ַ�������
    public static final int NET_MAX_STRING_LEN                  = 128;
    public static final int NET_MACHINE_NAME_NUM                = 64;   // �������Ƴ���
    public static final int NET_USER_NAME_LENGTH_EX             = 16;   // �û�������
    public static final int NET_USER_NAME_LENGTH                = 8;    // �û�������
    public static final int NET_USER_PSW_LENGTH                 = 8;    // �û����볤��
    public static final int NET_EVENT_NAME_LEN                  = 128;  // �¼����Ƴ���
    public static final int NET_MAX_LANE_NUM                    = 8;    // ��Ƶ�����豸ÿ��ͨ����Ӧ����������
    public static final int MAX_DRIVING_DIR_NUM                 = 16;   // ������ʻ����������
    public static final int FLOWSTAT_ADDR_NAME                  = 16;   // �����еص�����
    public static final int NET_MAX_DRIVINGDIRECTION            = 256;  // ��ʻ�����ַ�������
    public static final int COMMON_SEAT_MAX_NUMBER              = 8;    // Ĭ�ϼ��������ݸ���
    public static final int NET_MAX_ATTACHMENT_NUM              = 8;    // ������������
    public static final int NET_MAX_ANNUUALINSPECTION_NUM       = 8;    // �������ʶλ��
    public static final int NET_MAX_EVENT_PIC_NUM				= 6;    // ���ԭʼͼƬ����
    public static final int NET_COMMON_STRING_32                = 32;   // ͨ���ַ�������32
    public static final int NET_COMMON_STRING_16                = 16;   // ͨ���ַ�������16
    public static final int NET_COMMON_STRING_64                = 64;   // ͨ���ַ�������64
    public static final int MAX_VIDEOSTREAM_NUM                 = 3;    // �����������
    public static final int MAX_VIDEO_COVER_NUM                 = 16;   // ����ڵ��������
    public static final int MAX_VIDEO_IN_ZOOM                   = 32;   // ��ͨ�����������ø���
    public static final int NET_EVENT_CARD_LEN                  = 36;   // ��Ƭ���Ƴ���
    public static final int NET_EVENT_MAX_CARD_NUM              = 16;   // �¼��ϱ���Ϣ�������Ƭ����
    public static final int MAX_STATUS_NUM                      = 16;   // ��ͨ�豸״̬������
    public static final int NET_MAX_CHANMASK 					= 64;   // ͨ���������ֵ
    public static final int NET_CHAN_NAME_LEN                   = 32;   // ͨ��������,DVR DSP��������,���32�ֽ� 

    public static final int NET_NEW_MAX_RIGHT_NUM               = 1024; // �û�Ȩ�޸�������
    public static final int NET_MAX_GROUP_NUM                   = 20;   // �û����������
    public static final int NET_MAX_USER_NUM                    = 200;  // �û���������
    public static final int NET_RIGHT_NAME_LENGTH               = 32;   // Ȩ��������
    public static final int NET_MEMO_LENGTH                     = 32;   // ��ע����
    public static final int NET_NEW_USER_NAME_LENGTH            = 128;  // �û�������
    public static final int NET_NEW_USER_PSW_LENGTH             = 128;  // ����   

    public static final int AV_CFG_Device_ID_Len				= 64;   // �豸ID����
    public static final int AV_CFG_IP_Address_Len				= 32;   // IP ����
    public static final int AV_CFG_Protocol_Len 				= 32;   // Э��������
    public static final int AV_CFG_User_Name_Len 				= 64;   // �û�������
    public static final int	AV_CFG_Password_Len 				= 64;   // ���볤��
    public static final int AV_CFG_Serial_Len					= 32;	// ���кų���
    public static final int AV_CFG_Device_Class_Len				= 16;   // �豸���ͳ���
    public static final int AV_CFG_Device_Type_Len				= 32;	// �豸�����ͺų���
    public static final int AV_CFG_Device_Name_Len				= 128;	// ��������
    public static final int AV_CFG_Address_Len					= 128;	// ��������ص�
    public static final int AV_CFG_Max_Path						= 260;	// ·������
    public static final int AV_CFG_Group_Name_Len               = 64;   // �������Ƴ���
    public static final int AV_CFG_DeviceNo_Len                 = 32;   // �豸��ų���
    public static final int AV_CFG_Group_Memo_Len               = 128;  // ����˵������
    public static final int AV_CFG_Max_Channel_Num              = 1024; // ���ͨ������
    public static final int MAX_DEVICE_NAME_LEN					= 64;   // ��������
    public static final int MAX_DEV_ID_LEN_EX					= 128;  // �豸ID��󳤶�
    public static final int MAX_PATH_STOR                       = 240;  // Զ��Ŀ¼�ĳ���
    public static final int	MAX_REMOTE_DEV_NUM       			= 256;  // ���Զ���豸����
    public static final int NET_MAX_PLATE_NUMBER_LEN            = 32;   // �����ַ�����    
    public static final int NET_MAX_AUTHORITY_LIST_NUM          = 16;   // Ȩ���б�������    
    public static final int NET_MAX_ALARMOUT_NUM_EX 			= 32;   //��������ڸ���������չ
    public static final int NET_MAX_VIDEO_IN_NUM_EX 			= 32;   //��Ƶ����ڸ���������չ
    public static final int NET_MAX_SAERCH_IP_NUM               = 256;  // �������IP����
    
    public static final int CFG_COMMON_STRING_16                = 16;   // ͨ���ַ�������16
    public static final int CFG_COMMON_STRING_32                = 32;   // ͨ���ַ�������16
    public static final int CFG_COMMON_STRING_256               = 256;  // ͨ���ַ�������256
    
    public static final int MAX_VIOLATIONCODE					= 16;   // ���ܽ�ͨΥ�´��볤������
    public static final int MAX_VIOLATIONCODE_DESCRIPT          = 64;   // ���ܽ�ͨΥ�´��볤������
    public static final int MAX_PRIORITY_NUMBER                 = 256;  // Υ�����ȼ�����Υ��������
    public static final int MAX_LANE_CONFIG_NUMBER              = 32;   // ����������
    
    public static final int ECK_SCREEN_NUM_MAX                  = 8;    // ����ͣ��ϵͳ����ڻ����������
    
    // �����ӿ�����, �������Ϳ������
    public static final int NET_MATRIX_CARD_MAIN                = 0x10000000;   // ����
    public static final int NET_MATRIX_CARD_INPUT               = 0x00000001;   // ���뿨
    public static final int NET_MATRIX_CARD_OUTPUT              = 0x00000002;   // �����
    public static final int NET_MATRIX_CARD_ENCODE              = 0x00000004;   // ���뿨
    public static final int NET_MATRIX_CARD_DECODE              = 0x00000008;   // ���뿨
    public static final int NET_MATRIX_CARD_CASCADE             = 0x00000010;   // ������
    public static final int NET_MATRIX_CARD_INTELLIGENT         = 0x00000020;   // ���ܿ�
    public static final int NET_MATRIX_CARD_ALARM               = 0x00000040;   // ������
    public static final int NET_MATRIX_CARD_RAID                = 0x00000080;   // ӲRaid��
    public static final int NET_MATRIX_CARD_NET_DECODE          = 0x00000100;   // ������뿨
    
    /************************************************************************
     ** �ṹ��
     ***********************************************************************/
    // ���õ���ʱ����ز���
    public static class NET_PARAM  extends Structure
    {
        public int                    nWaittime;                // �ȴ���ʱʱ��(����Ϊ��λ)��Ϊ0Ĭ��5000ms
        public int                    nConnectTime;            // ���ӳ�ʱʱ��(����Ϊ��λ)��Ϊ0Ĭ��1500ms
        public int                    nConnectTryNum;            // ���ӳ��Դ�����Ϊ0Ĭ��1��
        public int                    nSubConnectSpaceTime;    // ������֮��ĵȴ�ʱ��(����Ϊ��λ)��Ϊ0Ĭ��10ms
        public int                    nGetDevInfoTime;        // ��ȡ�豸��Ϣ��ʱʱ�䣬Ϊ0Ĭ��1000ms
        public int                    nConnectBufSize;        // ÿ�����ӽ������ݻ����С(�ֽ�Ϊ��λ)��Ϊ0Ĭ��250*1024
        public int                    nGetConnInfoTime;        // ��ȡ��������Ϣ��ʱʱ��(����Ϊ��λ)��Ϊ0Ĭ��1000ms
        public int                     nSearchRecordTime;      // ��ʱ���ѯ¼���ļ��ĳ�ʱʱ��(����Ϊ��λ),Ϊ0Ĭ��Ϊ3000ms
        public int                     nsubDisconnetTime;      // ��������Ӷ��ߵȴ�ʱ��(����Ϊ��λ)��Ϊ0Ĭ��Ϊ60000ms
        public byte                    byNetType;                // ��������, 0-LAN, 1-WAN
        public byte                    byPlaybackBufSize;      // �ط����ݽ��ջ����С��MΪ��λ����Ϊ0Ĭ��Ϊ4M
        public byte[]               byReserved1 = new byte[2];         // ����
        public int                     nPicBufSize;            // ʵʱͼƬ���ջ����С���ֽ�Ϊ��λ����Ϊ0Ĭ��Ϊ2*1024*1024
        public byte[]                bReserved = new byte[4];            // �����ֶ��ֶ�
    }
    
    // �豸��Ϣ
    public static class NET_DEVICEINFO extends Structure {
        public byte[]              sSerialNumber = new byte[NET_SERIALNO_LEN];    // ���к�
        public byte                byAlarmInPortNum;         // DVR�����������
        public byte                byAlarmOutPortNum;        // DVR�����������
        public byte                byDiskNum;                // DVRӲ�̸���
        public byte                byDVRType;                // DVR����, ��ö��NET_DEV_DEVICE_TYPE
        public byte                byChanNum;                // DVRͨ������
    }
    
    // �豸��Ϣ��չ///////////////////////////////////////////////////
    public static class NET_DEVICEINFO_Ex extends Structure {
    	 public byte[]     sSerialNumber = new byte[NET_SERIALNO_LEN];    // ���к�
    	 public int        byAlarmInPortNum;                              // DVR�����������
    	 public int        byAlarmOutPortNum;                             // DVR�����������
    	 public int        byDiskNum;                                     // DVRӲ�̸���
    	 public int        byDVRType;                                     // DVR����,��ö��NET_DEVICE_TYPE
    	 public int        byChanNum;                                     // DVRͨ������
    	 public byte       byLimitLoginTime;                              // ���߳�ʱʱ��,Ϊ0��ʾ�����Ƶ�½,��0��ʾ���Ƶķ�����
    	 public byte       byLeftLogTimes;                                // ����½ʧ��ԭ��Ϊ�������ʱ,ͨ���˲���֪ͨ�û�,ʣ���½����,Ϊ0ʱ��ʾ�˲�����Ч
    	 public byte[]     bReserved = new byte[2];                       // �����ֽ�,�ֽڶ���
    	 public int        byLockLeftTime;                                // ����½ʧ��,�û�����ʣ��ʱ�䣨������, -1��ʾ�豸δ���øò���
    	 public byte[]     Reserved = new byte[24];                       // ����
    }
    
    // ��Ӧ�ӿ� CLIENT_LoginEx2/////////////////////////////////////////////////////////
    public static class EM_LOGIN_SPAC_CAP_TYPE extends Structure {
    	public static final int EM_LOGIN_SPEC_CAP_TCP               = 0;    // TCP��½, Ĭ�Ϸ�ʽ
    	public static final int EM_LOGIN_SPEC_CAP_ANY               = 1;    // ��������½
    	public static final int EM_LOGIN_SPEC_CAP_SERVER_CONN       = 2;    // ����ע��ĵ���
    	public static final int	EM_LOGIN_SPEC_CAP_MULTICAST         = 3;    // �鲥��½
    	public static final int	EM_LOGIN_SPEC_CAP_UDP               = 4;    // UDP��ʽ�µĵ���
    	public static final int	EM_LOGIN_SPEC_CAP_MAIN_CONN_ONLY    = 6;    // ֻ���������µĵ���
    	public static final int	EM_LOGIN_SPEC_CAP_SSL               = 7;    // SSL���ܷ�ʽ��½

    	public static final int	EM_LOGIN_SPEC_CAP_INTELLIGENT_BOX   = 9;    // ��¼���ܺ�Զ���豸
    	public static final int	EM_LOGIN_SPEC_CAP_NO_CONFIG         = 10;   // ��¼�豸����ȡ���ò���
    	public static final int EM_LOGIN_SPEC_CAP_U_LOGIN           = 11;   // ��U���豸�ĵ���
    	public static final int	EM_LOGIN_SPEC_CAP_LDAP              = 12;   // LDAP��ʽ��¼
    	public static final int EM_LOGIN_SPEC_CAP_AD                = 13;   // AD��ActiveDirectory����¼��ʽ
    	public static final int EM_LOGIN_SPEC_CAP_RADIUS            = 14;   // Radius ��¼��ʽ 
    	public static final int EM_LOGIN_SPEC_CAP_SOCKET_5          = 15;   // Socks5��½��ʽ
    	public static final int EM_LOGIN_SPEC_CAP_CLOUD             = 16;   // �Ƶ�½��ʽ
    	public static final int EM_LOGIN_SPEC_CAP_AUTH_TWICE        = 17;   // ���μ�Ȩ��½��ʽ
    	public static final int EM_LOGIN_SPEC_CAP_TS                = 18;   // TS�����ͻ��˵�½��ʽ
    	public static final int	EM_LOGIN_SPEC_CAP_P2P               = 19;   // ΪP2P��½��ʽ
    	public static final int	EM_LOGIN_SPEC_CAP_MOBILE            = 20;   // �ֻ��ͻ��˵�½
    	public int EM_LOGIN_SPEC_CAP_INVALID;                               // ��Ч�ĵ�½��ʽ
    }
    
    // ʱ��
    public static class NET_TIME extends Structure 
    {
        public int                dwYear;                   // ��
        public int                dwMonth;                  // ��
        public int                dwDay;                    // ��
        public int                dwHour;                   // ʱ
        public int                dwMinute;                 // ��
        public int                dwSecond;                 // ��
        
        public NET_TIME()
        {
            this.dwYear = 0;
            this.dwMonth = 0;
            this.dwDay = 0;
            this.dwHour = 0;
            this.dwMinute = 0;
            this.dwSecond = 0;
        }
        
        public NET_TIME(NET_TIME other)
        {
            this.dwYear = other.dwYear;
            this.dwMonth = other.dwMonth;
            this.dwDay = other.dwDay;
            this.dwHour = other.dwHour;
            this.dwMinute = other.dwMinute;
            this.dwSecond = other.dwSecond;
        }
        
        //�����б�����ʾ
        public String toStringTime()
        {
            return  String.format("%02d/%02d/%02d %02d:%02d:%02d", dwYear, dwMonth, dwDay, dwHour, dwMinute, dwSecond);
        }
        
        public String toString() {
        	return String.format("%02d%02d%02d%02d%02d%02d", dwYear, dwMonth, dwDay, dwHour, dwMinute, dwSecond);
        }
    }

    public static class NET_TIME_EX extends Structure 
    {
        public int                dwYear;                    // ��
        public int                dwMonth;                   // ��
        public int                dwDay;                     // ��
        public int                dwHour;                    // ʱ
        public int                dwMinute;                  // ��
        public int                dwSecond;                  // ��
        public int              dwMillisecond;               // ����
        public int[]            dwReserved = new int[2];     // �����ֶ�
        
        public String toString() {
            return "NET_TIME_EX.dwYear: " + dwYear + "\n" + "NET_TIME_EX.dwMonth: " + dwMonth + "\n" + "NET_TIME_EX.dwDay: " + dwDay + "\n" + "NET_TIME_EX.dwHour: " + dwHour + "\n" + "NET_TIME_EX.dwMinute: " + dwMinute + "\n" + "NET_TIME_EX.dwSecond: " + dwSecond;
        }

        //�����б�����ʾ
        public String toStringTime()
        {
            return  String.format("%02d/%02d/%02d%02d:%02d:%02d", dwYear, dwMonth, dwDay, dwHour, dwMinute, dwSecond);
        }

        //�洢�ļ���ʹ��
         public String toStringTitle()
        {
            return  String.format("Time%02d%02d%02d_%02d%02d%02d%03d", dwYear, dwMonth, dwDay, dwHour, dwMinute, dwSecond,dwMillisecond);
        }
    }
    
    // ����
    public static class  NET_CFG_Rect extends Structure
    {
        public int            nStructSize;
        public int            nLeft;
        public int            nTop;
        public int            nRight;
        public int            nBottom;    
        
        public NET_CFG_Rect()
        {
            this.nStructSize = this.size();
        }
    }
    
    // ��ɫ
    public static class  NET_CFG_Color extends Structure
    {
        public int            nStructSize;
        public int            nRed;                            // ��
        public int            nGreen;                            // ��
        public int            nBlue;                            // ��
        public int            nAlpha;                            // ͸��
        
        public NET_CFG_Color()
        {
            this.nStructSize = this.size();
        }
    }

    // �������-ͨ������
    public static class  NET_CFG_VideoWidgetChannelTitle extends Structure
    {
        public int                nStructSize;
        public int            	  bEncodeBlend;                    // ���ӵ�������, ����ΪBOOL, ȡֵ0����1
        public int            	  bEncodeBlendExtra1;              // ���ӵ�������1, ����ΪBOOL, ȡֵ0����1
        public int                bEncodeBlendExtra2;              // ���ӵ�������2, ����ΪBOOL, ȡֵ0����1
        public int            	  bEncodeBlendExtra3;              // ���ӵ�������3, ����ΪBOOL, ȡֵ0����1
        public int            	  bEncodeBlendSnapshot;            // ���ӵ�ץͼ, ����ΪBOOL, ȡֵ0����1
        public NET_CFG_Color      stuFrontColor = new NET_CFG_Color();    // ǰ��ɫ
        public NET_CFG_Color      stuBackColor = new NET_CFG_Color();    // ����ɫ
        public NET_CFG_Rect       stuRect = new NET_CFG_Rect();        // ����, ����ȡֵ0~8191, ��ʹ��left��topֵ, ��(left,top)Ӧ��(right,bottom)���ó�ͬ���ĵ�
        public int            bPreviewBlend;                    // ���ӵ�Ԥ����Ƶ, ����ΪBOOL�� ȡֵ0����1
        
        public NET_CFG_VideoWidgetChannelTitle()
        {
            this.nStructSize = this.size();
        }
    }

    // �������-ʱ�����
    public static class  NET_CFG_VideoWidgetTimeTitle extends Structure
    {
        public int                nStructSize;
        public int            bEncodeBlend;                        // ���ӵ�������, ����ΪBOOL, ȡֵ0����1
        public int            bEncodeBlendExtra1;                    // ���ӵ�������1, ����ΪBOOL, ȡֵ0����1
        public int            bEncodeBlendExtra2;                    // ���ӵ�������2, ����ΪBOOL, ȡֵ0����1
        public int            bEncodeBlendExtra3;                    // ���ӵ�������3, ����ΪBOOL, ȡֵ0����1
        public int            bEncodeBlendSnapshot;                // ���ӵ�ץͼ, ����ΪBOOL, ȡֵ0����1
        public NET_CFG_Color        stuFrontColor = new NET_CFG_Color();    // ǰ��ɫ
        public NET_CFG_Color        stuBackColor = new NET_CFG_Color();    // ����ɫ
        public NET_CFG_Rect        stuRect = new NET_CFG_Rect();        // ����, ����ȡֵ0~8191, ��ʹ��left��topֵ, ��(left,top)Ӧ��(right,bottom)���ó�ͬ���ĵ�
        public int            bShowWeek;                            // �Ƿ���ʾ����, ����ΪBOOL, ȡֵ0����1
        public int            bPreviewBlend;                        // ���ӵ�Ԥ����Ƶ, ����ΪBOOL, ȡֵ0����1
        
        public NET_CFG_VideoWidgetTimeTitle()
        {
            this.nStructSize = this.size();
        }
    }
    
    // �������-���򸲸�����
    public static class  NET_CFG_VideoWidgetCover extends Structure
    {
        public int                nStructSize;    
        public int            bEncodeBlend;                    // ���ӵ�������, ����ΪBOOL, ȡֵ0����1
        public int            bEncodeBlendExtra1;                // ���ӵ�������1, ����ΪBOOL, ȡֵ0����1
        public int            bEncodeBlendExtra2;                // ���ӵ�������2, ����ΪBOOL, ȡֵ0����1
        public int            bEncodeBlendExtra3;                // ���ӵ�������3, ����ΪBOOL, ȡֵ0����1
        public int            bEncodeBlendSnapshot;            // ���ӵ�ץͼ, ����ΪBOOL, ȡֵ0����1
        public NET_CFG_Color        stuFrontColor = new NET_CFG_Color();        // ǰ��ɫ
        public NET_CFG_Color        stuBackColor = new NET_CFG_Color();        // ����ɫ
        public NET_CFG_Rect        stuRect = new NET_CFG_Rect();            // ����, ����ȡֵ0~8191
        public int            bPreviewBlend;                    // ���ӵ�Ԥ����Ƶ, ����ΪBOOL, ȡֵ0����1
        
        public NET_CFG_VideoWidgetCover()
        {
            this.nStructSize = this.size();
        }
    }
    
    public class EM_TITLE_TEXT_ALIGN
    {
        public static final int EM_TEXT_ALIGN_INVALID         = 0;     // ��Ч�Ķ��뷽ʽ
        public static final int EM_TEXT_ALIGN_LEFT            = 1;       // �����
        public static final int EM_TEXT_ALIGN_XCENTER        = 2;    // X�����ж���
        public static final int EM_TEXT_ALIGN_YCENTER        = 3;    // Y�����ж���
        public static final int EM_TEXT_ALIGN_CENTER        = 4;      // ����
        public static final int EM_TEXT_ALIGN_RIGHT            = 5;       // �Ҷ���
        public static final int EM_TEXT_ALIGN_TOP            = 6;       // ���ն�������
        public static final int EM_TEXT_ALIGN_BOTTOM        = 7;     // ���յײ�����
        public static final int EM_TEXT_ALIGN_LEFTTOP        = 8;    // �������ϽǶ���
        public static final int EM_TEXT_ALIGN_CHANGELINE    = 9;      // ���ж���
    }

    // �������-�Զ������
    public static class  NET_CFG_VideoWidgetCustomTitle extends Structure
    {
        public int                nStructSize;
        public int            bEncodeBlend;                        // ���ӵ�������, ����ΪBOOL, ȡֵ0����1 
        public int            bEncodeBlendExtra1;                    // ���ӵ�������1, ����ΪBOOL, ȡֵ0����1
        public int            bEncodeBlendExtra2;                    // ���ӵ�������2, ����ΪBOOL, ȡֵ0����1
        public int            bEncodeBlendExtra3;                    // ���ӵ�������3, ����ΪBOOL, ȡֵ0����1
        public int            bEncodeBlendSnapshot;                // ���ӵ�ץͼ, ����ΪBOOL, ȡֵ0����1
        public NET_CFG_Color        stuFrontColor = new NET_CFG_Color();    // ǰ��ɫ
        public NET_CFG_Color        stuBackColor = new NET_CFG_Color();    // ����ɫ
        public NET_CFG_Rect        stuRect = new NET_CFG_Rect();        // ����, ����ȡֵ0~8191, ��ʹ��left��topֵ, ��(left,top)Ӧ��(right,bottom)���ó�ͬ���ĵ�
        public byte[]            szText = new byte[NET_CFG_Custom_Title_Len];// ��������
        public int            bPreviewBlend;                    // ���ӵ�Ԥ����Ƶ, ����ΪBOOL, ȡֵ0����1
        public byte[]           szType = new byte[NET_CFG_Custom_TitleType_Len];// �������� "Rtinfo" ʵʱ��¼��Ϣ "Custom" �Զ�����ӡ���ʪ�ȵ��� "Title" :Ƭͷ��Ϣ "Check"  У����
                                                                // ������Ϣ "Geography"  ATM������Ϣ "ATMCardInfo" �������� "CameraID" 
        public int                  emTextAlign;                    // ������뷽ʽ (�μ�EM_TITLE_TEXT_ALIGN)
        
        public NET_CFG_VideoWidgetCustomTitle()
        {
            this.nStructSize = this.size();
        }
    }
    
    //  �������-���Ӵ�������Ϣ-������������
    public static class  NET_CFG_VideoWidgetSensorInfo_Description extends Structure
    {
        public int            nStructSize;
        public int            nSensorID;                        // ��Ҫ�����Ĵ�������ID(��ģ��������ͨ����)
        public byte[]         szDevID =  new byte[CFG_COMMON_STRING_32];  // �豸ID
        public byte[]         szPointID = new byte[CFG_COMMON_STRING_32];// ���ID
        public byte[]         szText = new byte[CFG_COMMON_STRING_256];  // ��Ҫ���ӵ�����
        public NET_CFG_VideoWidgetSensorInfo_Description()
        {
            this.nStructSize = this.size();
        }
    }

    // �������-���Ӵ�������Ϣ
    public static class  NET_CFG_VideoWidgetSensorInfo extends Structure
    {
        public int            nStructSize;
        public int        bPreviewBlend;                    // ���ӵ�Ԥ����Ƶ, ����ΪBOOL, ȡֵ0����1
        public int        bEncodeBlend;                    // ���ӵ���������Ƶ����, ����ΪBOOL, ȡֵ0����1
        public NET_CFG_Rect    stuRect = new NET_CFG_Rect();                        // ����, ����ȡֵ0~8191
        public int            nDescriptionNum;                // ��������������Ŀ
        public NET_CFG_VideoWidgetSensorInfo_Description[]  stuDescription = (NET_CFG_VideoWidgetSensorInfo_Description[])new NET_CFG_VideoWidgetSensorInfo_Description().toArray(NET_CFG_Max_Description_Num);// ��������������Ϣ
        
        public NET_CFG_VideoWidgetSensorInfo()
        {
            this.nStructSize = this.size();
        }
    }

    // ��Ƶ�����������
    public static class NET_CFG_VideoWidget extends Structure
    {
        public int                              nStructSize;
        public NET_CFG_VideoWidgetChannelTitle  stuChannelTitle = new NET_CFG_VideoWidgetChannelTitle();        // ͨ������
        public NET_CFG_VideoWidgetTimeTitle     stuTimeTitle = new NET_CFG_VideoWidgetTimeTitle();            // ʱ�����
        public int                              nConverNum;              // ���򸲸�����
        public NET_CFG_VideoWidgetCover[]       stuCovers = new NET_CFG_VideoWidgetCover[NET_CFG_Max_Video_Widget_Cover];                        // ��������
        public int                              nCustomTitleNum;         // �Զ����������
        public NET_CFG_VideoWidgetCustomTitle[] stuCustomTitle = new NET_CFG_VideoWidgetCustomTitle[NET_CFG_Max_Video_Widget_Custom_Title];    // �Զ������
        public int                              nSensorInfo;             // ��������Ϣ����������Ŀ
        public NET_CFG_VideoWidgetSensorInfo[]  stuSensorInfo = new NET_CFG_VideoWidgetSensorInfo[NET_CFG_Max_Video_Widget_Sensor_Info];        // ��������Ϣ����������Ϣ
        public double                           fFontSizeScale;          //���������С�Ŵ����
                                                                         //��fFontSizeScale��0ʱ,nFontSize��������
                                                                         //��fFontSizeScale=0ʱ,nFontSize������
                                                                         //�豸Ĭ��fFontSizeScale=1.0
                                                                         //�����Ҫ�޸ı������޸ĸ�ֵ
                                                                         //�����Ҫ�����������ã����ø�ֵΪ0��nFontSize��ֵ��Ч
        public int                               nFontSize;              //���ӵ��������ϵ�ȫ�������С,��λ px.
                                                                         //��fFontSizeScale��ͬ����
        public int                               nFontSizeExtra1;        //���ӵ�������1�ϵ�ȫ�������С,��λ px
        public int                               nFontSizeExtra2;        //���ӵ�������2�ϵ�ȫ�������С,��λ px
        public int                               nFontSizeExtra3;        //���ӵ�������3�ϵ�ȫ�������С,��λ px
        public int                               nFontSizeSnapshot;      //���ӵ�ץͼ���ϵ�ȫ�������С, ��λ px
        public int                               nFontSizeMergeSnapshot; //���ӵ�ץͼ���Ϻϳ�ͼƬ�������С,��λ px
        
        public NET_CFG_VideoWidget()
        {
            this.nStructSize = this.size();
            for (int i = 0; i < stuCustomTitle.length; i++) {
            	stuCustomTitle[i] = new NET_CFG_VideoWidgetCustomTitle();
			}
            
            for (int i = 0; i < stuCovers.length; i++) {
            	stuCovers[i] = new NET_CFG_VideoWidgetCover();
			}
            
            for (int i = 0; i < stuSensorInfo.length; i++) {
            	stuSensorInfo[i] = new NET_CFG_VideoWidgetSensorInfo();
			}
        }
    }
    
    // �����¼����� NET_EVENT_VIDEOABNORMALDETECTION ��Ӧ������������Ϣ
    public static class ALARM_VIDEOABNORMAL_DETECTION_INFO extends Structure
    {
        public int          dwSize;    
        public int          nChannelID;                     // ͨ����
        public double       PTS;                            // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX  UTC;                            // �¼�������ʱ��
        public int             nEventID;                       // �¼�ID
        public int          nEventAction;                   // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public int          nType;                          // �������,0-��Ƶ��ʧ, 1-��Ƶ�ڵ�, 2-���涳��, 3-����, 4-����, 5-�����仯
                                                            // 6-���Ƽ�� , 7-������� , 8-ƫɫ��� , 9-��Ƶģ����� , 10-�Աȶ��쳣���
                                                            // 11-��Ƶ�˶�, 12-��Ƶ��˸, 13-��Ƶ��ɫ, 14-�齹���, 15-���ؼ��
        public int          nValue;                         // ���ֵ,ֵԽ�߱�ʾ��Ƶ����Խ��, GB30147����
        public int          nOccurrenceCount;               // ���򱻴���������
        
        public ALARM_VIDEOABNORMAL_DETECTION_INFO()
        {
            this.dwSize = this.size();
        }
    }
    
    // ͣ������ˢ������
    public static class NET_PARKING_CARD_TYPE extends Structure
    {
        public static final int NET_PARKING_CARD_TYPE_UNKNOWN = 0;
        public static final int NET_PARKING_CARD_TYPE_SEND = 1;   // ����
        public static final int NET_PARKING_CARD_TYPE_DETECT = 2; // ˢ��
    }
    
    // �����¼����� NET_ALARM_PARKING_CARD (ͣ��ˢ���¼�)��Ӧ������������Ϣ
    public static class ALARM_PARKING_CARD extends Structure {
    	public int                   dwSize;
    	public int   				 emType;                       // ����, �ο� NET_PARKING_CARD_TYPE
        public int                   dwCardNo;                     // ����
        public byte[]                szPlate = new byte[NET_COMMON_STRING_16]; // ����
        
        public ALARM_PARKING_CARD() {
        	this.dwSize = this.size();
        }
    }

    // �����¼����� NET_ALARM_NEW_FILE ��Ӧ������������Ϣ
    public static class ALARM_NEW_FILE_INFO extends Structure
    {
        public int      dwSize;
        public int      nChannel;                           // ץͼͨ����
        public int      nEventID;                           // �¼�ID
        public int      dwEvent;                            // �¼�����
        public int      FileSize;                           // �ļ���С,��λ���ֽ�
        public int      nIndex;                             // �¼�Դͨ��
        public int      dwStorPoint;                        // �洢��
        public byte[]   szFileName = new byte[128];           // �ļ���
        
        public ALARM_NEW_FILE_INFO()
        {
            this.dwSize = this.size();
        }
    }

    // ����Խ��������
    public static class EM_UPPER_LIMIT_TYPE extends Structure
    {
        public static final int EM_UPPER_LIMIT_TYPE_UNKNOWN     = 0;  
        public static final int EM_UPPER_LIMIT_TYPE_ENTER_OVER  = 1; // ����Խ����
        public static final int EM_UPPER_LIMIT_TYPE_EXIT_OVER   = 2; // ����Խ����
        public static final int EM_UPPER_LIMIT_TYPE_INSIDE_OVER = 3; // �ڲ�Խ����    
    }
    

    // �¼����� NET_ALARM_HUMAM_NUMBER_STATISTIC (������/������ͳ���¼�NumberStat��Ӧ������������Ϣ)
    public static class  ALARM_HUMAN_NUMBER_STATISTIC_INFO extends Structure
    {
        public double              PTS;                            // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                            // �¼�������ʱ��
        public int                 nEventAction;                   // �¼�����,0-�¼�����, 1-��ʾ�¼���ʼ, 2-��ʾ�¼�����;
        public int                 nNumber;                        // ����������ĸ���
        public int                 nEnteredNumber;                 // ����������߳�����ڵ��������
        public int                 nExitedNumber;                  // ����������߳�����ڵ��������
        public int                 emUpperLimitType;               // ����Խ��������,�μ�EM_UPPER_LIMIT_TYPE����
        public byte[]              reserved = new byte[512];       // Ԥ��       
    }
    
    /////////////////////////////////����֧��/////////////////////////////////
    //�����ӦͼƬ�ļ���Ϣ
    public static class NET_PIC_INFO extends Structure
    {
        public int dwOffSet;//�ļ��ڶ��������ݿ��е�ƫ��λ��,��λ:�ֽ�
        public int dwFileLenth;//�ļ���С,��λ:�ֽ�
        public short wWidth;//ͼƬ���,��λ:����
        public short wHeight;//ͼƬ�߶�,��λ:����
        public Pointer pszFilePath;//������ʷԭ��,�ó�Աֻ���¼��ϱ�ʱ��Ч�� char *
                                   // �ļ�·��
                                   // �û�ʹ�ø��ֶ�ʱ��Ҫ��������ռ���п�������
        public byte bIsDetected;//ͼƬ�Ƿ��㷨�������ļ������ύʶ�������ʱ,
                                            //����Ҫ��ʱ��ⶨλ��ͼ,1:������,0:û�м���
        public byte[] bReserved = new byte[11];//12<--16
    }

    // ��Ա����
    public static class EM_PERSON_TYPE extends Structure
    {
        public static final int PERSON_TYPE_UNKNOWN = 0;  
        public static final int PERSON_TYPE_NORMAL = 1; //��ͨ��Ա
        public static final int PERSON_TYPE_SUSPICION = 2; //������Ա
    }

    // ֤������
    public static class EM_CERTIFICATE_TYPE extends Structure
    {
        public static final int CERTIFICATE_TYPE_UNKNOWN = 0;  
        public static final int CERTIFICATE_TYPE_IC = 1; //���֤
        public static final int CERTIFICATE_TYPE_PASSPORT = 2; //����
    }
    
    //��Ա��Ϣ
    public static class FACERECOGNITION_PERSON_INFO extends Structure
    {
        public byte[] szPersonName = new byte[NET_MAX_NAME_LEN];//����,�˲�������
        public short wYear;//������,��Ϊ��ѯ����ʱ,�˲�����0,���ʾ�˲�����Ч
        public byte byMonth;//������,��Ϊ��ѯ����ʱ,�˲�����0,���ʾ�˲�����Ч
        public byte byDay;//������,��Ϊ��ѯ����ʱ,�˲�����0,���ʾ�˲�����Ч
        public byte[] szID = new byte[NET_MAX_PERSON_ID_LEN];//��ԱΨһ��ʾ(���֤����,����,���������)
        public byte bImportantRank;//��Ա��Ҫ�ȼ�,1~10,��ֵԽ��Խ��Ҫ,��Ϊ��ѯ����ʱ,�˲�����0,���ʾ�˲�����Ч
        public byte bySex;//�Ա�,1-��,2-Ů,��Ϊ��ѯ����ʱ,�˲�����0,���ʾ�˲�����Ч
        public short wFacePicNum;//ͼƬ����
        public NET_PIC_INFO[] szFacePicInfo =  (NET_PIC_INFO[])new NET_PIC_INFO().toArray(NET_MAX_PERSON_IMAGE_NUM);//��ǰ��Ա��Ӧ��ͼƬ��Ϣ
        public byte byType;//��Ա����,���EM_PERSON_TYPE
        public byte byIDType;//֤������,���EM_CERTIFICATE_TYPE
        public byte[] bReserved1 = new byte[2];//�ֽڶ���
        public byte[] szProvince = new byte[NET_MAX_PROVINCE_NAME_LEN];//ʡ��
        public byte[] szCity = new byte[NET_MAX_CITY_NAME_LEN];//����
        public byte[] szPersonNameEx = new byte[NET_MAX_PERSON_NAME_LEN];//����,�������������,16�ֽ��޷��������,�����Ӵ˲���,
        public byte[] szUID = new byte[NET_MAX_PERSON_ID_LEN];//��ԱΨһ��ʶ��,�״��ɷ��������,������ID�ֶ�
                                                              // �޸�,ɾ������ʱ����
        public byte[] bReserved = new byte[28];
    }

    ///////////////////////////////////����ʶ��ģ����ؽṹ��///////////////////////////////////////
    public static class NET_UID_CHAR extends Structure
    {
        public byte[] szUID = new byte[NET_MAX_PERSON_ID_LEN];//UID����
    }
    
    //����ʶ�����ݿ����
    public static class EM_OPERATE_FACERECONGNITIONDB_TYPE
    {
        public static final int NET_FACERECONGNITIONDB_UNKOWN = 0; 
        public static final int NET_FACERECONGNITIONDB_ADD = 1;             //�����Ա��Ϣ�����������������Ա�Ѿ����ڣ�ͼƬ���ݺ�ԭ�������ݺϲ�
        public static final int NET_FACERECONGNITIONDB_DELETE = 2;             //ɾ����Ա��Ϣ����������
        public static final int NET_FACERECONGNITIONDB_MODIFY = 3;             //�޸���Ա��Ϣ����������,��Ա��UID��ʶ����
        public static final int NET_FACERECONGNITIONDB_DELETE_BY_UID = 4;     //ͨ��UIDɾ����Ա��Ϣ����������
    }
    
    //CLIENT_OperateFaceRecognitionDB�ӿ��������
    public static class NET_IN_OPERATE_FACERECONGNITIONDB extends Structure
    {
        public int dwSize;
        public int emOperateType;//��������, ȡEM_OPERATE_FACERECONGNITIONDB_TYPE�е�ֵ
        public FACERECOGNITION_PERSON_INFO stPersonInfo;//��Ա��Ϣ
                                                        //emOperateType��������ΪET_FACERECONGNITIONDB_DELETE_BY_UIDʱʹ��,stPeronInfo�ֶ���Ч
        public int nUIDNum;//UID����
        public Pointer stuUIDs;//��ԱΨһ��ʶ��,�״��ɷ��������,������ID�ֶ�, NET_UID_CHAR *
        // ͼƬ����������
        public Pointer pBuffer;//�����ַ, char *
        public int nBufferLen;//�������ݳ���
        
        public NET_IN_OPERATE_FACERECONGNITIONDB()
        {
            this.dwSize = this.size();
        }
    }
    
    //CLIENT_OperateFaceRecognitionDB�ӿ��������
    public static class NET_OUT_OPERATE_FACERECONGNITIONDB extends Structure
    {
        public int dwSize;
        
        public NET_OUT_OPERATE_FACERECONGNITIONDB()
        {
            this.dwSize = this.size();
        }
    }
    
    //�����Ա�ģʽ
    public static class EM_FACE_COMPARE_MODE extends Structure
    {
        public static final int NET_FACE_COMPARE_MODE_UNKOWN = 0;
        public static final int NET_FACE_COMPARE_MODE_NORMAL = 1; //����
        public static final int NET_FACE_COMPARE_MODE_AREA = 2; //ָ�����������������
        public static final int  NET_FACE_COMPARE_MODE_AUTO = 3; //����ģʽ,�㷨��������������������Զ�ѡȡ���
    }
    
    //��������
    public static class EM_FACE_AREA_TYPE extends Structure
    {
        public static final int NET_FACE_AREA_TYPE_UNKOWN = 0; 
        public static final int NET_FACE_AREA_TYPE_EYEBROW = 1; //üë
        public static final int NET_FACE_AREA_TYPE_EYE = 2; //�۾�
        public static final int NET_FACE_AREA_TYPE_NOSE= 3; //����
        public static final int NET_FACE_AREA_TYPE_MOUTH = 4; //���
        public static final int NET_FACE_AREA_TYPE_CHEEK =5; //����
    }
    
    public static class NET_FACE_MATCH_OPTIONS extends Structure
    {
        public int dwSize;
        public int nMatchImportant;//��Ա��Ҫ�ȼ�1~10,��ֵԽ��Խ��Ҫ,(��ѯ��Ҫ�ȼ����ڵ��ڴ˵ȼ�����Ա)�� ����Ϊunsigned int
        public int emMode;//�����ȶ�ģʽ,���EM_FACE_COMPARE_MODE, ȡEM_FACE_COMPARE_MODE�е�ֵ
        public int nAreaNum;//�����������
        public int[] szAreas= new int[MAX_FACE_AREA_NUM];//�����������,emModeΪNET_FACE_COMPARE_MODE_AREAʱ��Ч, ����Ԫ��ȡEM_FACE_AREA_TYPE�е�ֵ
        public int nAccuracy;//ʶ�𾫶�(ȡֵ1~10,����ֵ����,��⾫�����,����ٶ��½�����СֵΪ1��ʾ����ٶ�����,���ֵΪ10��ʾ��⾫�����ȡ���ʱֻ�����������Ч)
        public int nSimilarity;//���ƶ�(������ڸ���ʶ�Ȳű���;�ٷֱȱ�ʾ,1~100)
        public int nMaxCandidate;//���������ѡ����(�������ƶȽ�������,ȡ���ƶ����ĺ�ѡ��������)
        
        public NET_FACE_MATCH_OPTIONS()
        {
            this.dwSize = this.size();
        }
    }
    
    //����ʶ����������
    public static class EM_FACERECOGNITION_FACE_TYPE extends Structure
    {
        public static final int  EM_FACERECOGNITION_FACE_TYPE_UNKOWN = 0;
        public static final int  EM_FACERECOGNITION_FACE_TYPE_ALL = 1; //��������
        public static final int  EM_FACERECOGNITION_FACE_TYPE_REC_SUCCESS=  2;//ʶ��ɹ�
        public static final int  EM_FACERECOGNITION_FACE_TYPE_REC_FAIL = 3;//ʶ��ʧ��
    }
    
    public static class NET_FACE_FILTER_CONDTION extends Structure
    {
        public int dwSize;
        public NET_TIME stStartTime;//��ʼʱ��
        public NET_TIME stEndTime;//����ʱ��
        public byte[] szMachineAddress = new byte[MAX_PATH];//�ص�,֧��ģ��ƥ��
        public int nRangeNum;//ʵ�����ݿ����
        public byte[] szRange = new byte[MAX_FACE_DB_NUM];//����ѯ���ݿ�����,���EM_FACE_DB_TYPE
        public int emFaceType;//����ѯ��������,���EM_FACERECOGNITION_FACE_TYPE�� ȡEM_FACERECOGNITION_FACE_TYPE�е�ֵ
        public int nGroupIdNum;//��Ա����
        public byte[] szGroupId = new byte[MAX_GOURP_NUM*NET_COMMON_STRING_64];//��Ա��ID
        
        public NET_FACE_FILTER_CONDTION()
        {
            this.dwSize = this.size();
        }
    }
    
    //CLIENT_StartFindFaceRecognition�ӿ��������
    public static class NET_IN_STARTFIND_FACERECONGNITION extends Structure
    {
        public int dwSize;
        public int bPersonEnable;//��Ա��Ϣ��ѯ�����Ƿ���Ч, BOOL���ͣ�ȡֵ0��1
        public FACERECOGNITION_PERSON_INFO stPerson;//��Ա��Ϣ��ѯ����
        public NET_FACE_MATCH_OPTIONS stMatchOptions;//����ƥ��ѡ��
        public NET_FACE_FILTER_CONDTION stFilterInfo;//��ѯ��������
        // ͼƬ����������
        public Pointer pBuffer;//�����ַ, char *
        public int nBufferLen;//�������ݳ���
        public int nChannelID;//ͨ����
        
        public NET_IN_STARTFIND_FACERECONGNITION()
        {
            this.dwSize = this.size();
        }
    }
    
    //CLIENT_StartFindFaceRecognition�ӿ��������
    public static class NET_OUT_STARTFIND_FACERECONGNITION extends Structure
    {
        public int dwSize;
        public int nTotalCount;//���صķ��ϲ�ѯ�����ļ�¼����
                               // -1��ʾ������δ����,Ҫ�Ƴٻ�ȡ
                               // ʹ��CLIENT_AttachFaceFindState�ӿ�״̬
        public NativeLong lFindHandle;//��ѯ���
        public int nToken;//��ȡ���Ĳ�ѯ����
        
        public NET_OUT_STARTFIND_FACERECONGNITION()
        {
            this.dwSize = this.size();
        }
    }
    
    //CLIENT_DoFindFaceRecognition �ӿ��������
    public static class NET_IN_DOFIND_FACERECONGNITION extends Structure
    {
        public int dwSize;
        public NativeLong lFindHandle;//��ѯ���
        public int nBeginNum;//��ѯ��ʼ���
        public int nCount;//��ǰ���ѯ�ļ�¼����
        
        public NET_IN_DOFIND_FACERECONGNITION()
        {
            this.dwSize = this.size();
        }
    }
    
    //��ѡ��Ա��Ϣ
    public static class CANDIDATE_INFO extends Structure
    {
        public FACERECOGNITION_PERSON_INFO stPersonInfo;//��Ա��Ϣ
                                                        // ���أ�����������,ָ���ؿ�����Ա��Ϣ��
                                                        // ��ʷ��,ָ��ʷ������Ա��Ϣ
                                                        // ������,ָ���ؿ����Ա��Ϣ
        public byte bySimilarity;//�Ͳ�ѯͼƬ�����ƶ�,�ٷֱȱ�ʾ,1~100
        public byte byRange;//��Ա�������ݿⷶΧ,���EM_FACE_DB_TYPE
        public byte[] byReserved1 = new byte[2];
        public NET_TIME stTime;//��byRangeΪ��ʷ���ݿ�ʱ��Ч,��ʾ��ѯ��Ա���ֵ�ʱ��
        public byte[] szAddress = new byte[MAX_PATH];//��byRangeΪ��ʷ���ݿ�ʱ��Ч,��ʾ��ѯ��Ա���ֵĵص�
        public byte[] byReserved = new byte[128];//�����ֽ�
    }
    
    //CLIENT_DoFindFaceRecognition�ӿ��������
    public static class NET_OUT_DOFIND_FACERECONGNITION extends Structure
    {
        public int dwSize;
        public int nCadidateNum;//ʵ�ʷ��صĺ�ѡ��Ϣ�ṹ�����
        public CANDIDATE_INFO[] stCadidateInfo = (CANDIDATE_INFO[])new CANDIDATE_INFO().toArray(MAX_FIND_COUNT);//��ѡ��Ϣ����
        // ͼƬ����������
        public Pointer pBuffer;//�����ַ, char *
        public int nBufferLen;//�������ݳ���
        
        public NET_OUT_DOFIND_FACERECONGNITION()
        {
            this.dwSize = this.size();
        }
    }
    
    /////////////////////////////////����֧��/////////////////////////////////
    //CLIENT_DetectFace�ӿ��������
    public static class NET_IN_DETECT_FACE extends Structure
    {
        public int dwSize;
        public NET_PIC_INFO stPicInfo;//��ͼ��Ϣ
        // ͼƬ����������
        public Pointer pBuffer;//�����ַ, char *
        public int nBufferLen;//�������ݳ���
        
        public NET_IN_DETECT_FACE()
        {
            this.dwSize = this.size();
        }
    }
    
    //CLIENT_DetectFace�ӿ��������
    public static class NET_OUT_DETECT_FACE extends Structure
    {
        public int dwSize;
        public Pointer pPicInfo;//����������ͼƬ��Ϣ,���û�����ռ�, NET_PIC_INFO*
        public int nMaxPicNum;//�������ͼƬ��Ϣ����
        public int nRetPicNum;//ʵ�ʷ��ص�����ͼƬ����
        // ͼƬ����������
        public Pointer pBuffer;//�����ַ,���û�����ռ�,��ż���������ͼƬ����, char *
        public int nBufferLen;//�������ݳ���
        
        public NET_OUT_DETECT_FACE()
        {
            this.dwSize = this.size();
        }
    }
    
    // ����ʶ���¼�����
    public static class EM_FACERECOGNITION_ALARM_TYPE extends Structure
    {
        public static final int NET_FACERECOGNITION_ALARM_TYPE_UNKOWN = 0;
        public static final int NET_FACERECOGNITION_ALARM_TYPE_ALL = 1; //�ڰ�����
        public static final int NET_FACERECOGNITION_ALARM_TYPE_BLACKLIST = 2; //������
        public static final int NET_FACERECOGNITION_ALARM_TYPE_WHITELIST = 3; //������
    }
    
    //NET_FILE_QUERY_FACE��Ӧ������ʶ������ѯ����
    public static class MEDIAFILE_FACERECOGNITION_PARAM extends Structure
    {
        public int dwSize;//�ṹ���С
        // ��ѯ��������
        public NET_TIME stStartTime;//��ʼʱ��
        public NET_TIME stEndTime;//����ʱ��
        public byte[] szMachineAddress = new byte[MAX_PATH];//�ص�,֧��ģ��ƥ��
        public int nAlarmType;//����ѯ��������,���EM_FACERECOGNITION_ALARM_TYPE
        public int abPersonInfo;//��Ա��Ϣ�Ƿ���Ч, BOOL���ͣ�ȡֵ0��1
        public FACERECOGNITION_PERSON_INFO stPersonInfo;//��Ա��Ϣ
        public int nChannelId;//ͨ����
        public int nGroupIdNum;//��Ա����
        public byte[] szGroupId = new byte[MAX_GOURP_NUM*NET_COMMON_STRING_64];//��Ա��ID
        
        public MEDIAFILE_FACERECOGNITION_PARAM()
        {
            this.dwSize = this.size();
        }
    }
    
    // DH_MEDIA_QUERY_TRAFFICCAR��Ӧ�Ĳ�ѯ����
    public static class MEDIA_QUERY_TRAFFICCAR_PARAM extends Structure 
    {
        public int                 nChannelID;                     // ͨ���Ŵ�0��ʼ,-1��ʾ��ѯ����ͨ��
        public NET_TIME            StartTime;                      // ��ʼʱ��    
        public NET_TIME            EndTime;                        // ����ʱ��
        public int                 nMediaType;                     // �ļ�����,0:��������, 1:jpgͼƬ, 2:dav�ļ�
        public int                 nEventType;                     // �¼�����,���"���ܷ����¼�����", 0:��ʾ��ѯ�����¼�,�˲�������,��ʹ��pEventTypes
        public byte[]              szPlateNumber = new byte[32];   // ���ƺ�, "\0"���ʾ��ѯ���⳵�ƺ�
        public int                 nSpeedUpperLimit;               // ��ѯ�ĳ��ٷ�Χ; �ٶ����� ��λ: km/h
        public int                 nSpeedLowerLimit;               // ��ѯ�ĳ��ٷ�Χ; �ٶ����� ��λ: km/h 
        public int                 bSpeedLimit;                    // �Ƿ��ٶȲ�ѯ; TRUE:���ٶȲ�ѯ,nSpeedUpperLimit��nSpeedLowerLimit��Ч��
        public int                 dwBreakingRule;                 // Υ�����ͣ�
                                                            	   // ���¼�����Ϊ EVENT_IVS_TRAFFICGATEʱ
                                                            	   //        ��һλ:����;  �ڶ�λ:ѹ����ʻ; ����λ:������ʻ; 
                                                                   //        ����λ��Ƿ����ʻ; ����λ:�����;
                                                                   // ���¼�����Ϊ EVENT_IVS_TRAFFICJUNCTION
                                                                   //        ��һλ:�����;  �ڶ�λ:�����涨������ʻ;  
                                                                   //        ����λ:����; ����λ��Υ�µ�ͷ;
                                                                   //        ����λ:ѹ����ʻ;

        public byte[]              szPlateType=new byte[32];       // ��������,"Unknown" δ֪,"Normal" ���ƺ���,"Yellow" ����,"DoubleYellow" ˫���β��,"Police" ����"Armed" �侯��,
                                                            	   // "Military" ���Ӻ���,"DoubleMilitary" ����˫��,"SAR" �۰���������,"Trainning" ����������
                                                                   // "Personal" ���Ժ���,"Agri" ũ����,"Embassy" ʹ�ݺ���,"Moto" Ħ�г�����,"Tractor" ����������,"Other" ��������
        public byte[]              szPlateColor = new byte[16];    // ������ɫ, "Blue"��ɫ,"Yellow"��ɫ, "White"��ɫ,"Black"��ɫ
        public byte[]              szVehicleColor = new byte[16];  // ������ɫ:"White"��ɫ, "Black"��ɫ, "Red"��ɫ, "Yellow"��ɫ, "Gray"��ɫ, "Blue"��ɫ,"Green"��ɫ
        public byte[]              szVehicleSize = new byte[16];   // ������С����:"Light-duty":С�ͳ�;"Medium":���ͳ�; "Oversize":���ͳ�; "Unknown": δ֪
        public int                 nGroupID;                       // �¼�����(��ֵ>=0ʱ��Ч)
        public short               byLane;                         // ������(��ֵ>=0ʱ��ʾ���峵��,-1��ʾ���г���,�����·����ֶ�)
        public byte                byFileFlag;                     // �ļ���־, 0xFF-ʹ��nFileFlagEx, 0-��ʾ����¼��, 1-��ʱ�ļ�, 2-�ֶ��ļ�, 3-�¼��ļ�, 4-��Ҫ�ļ�, 5-�ϳ��ļ�
        public byte                byRandomAccess;                 // �Ƿ���Ҫ�ڲ�ѯ������������ת,0-����Ҫ,1-��Ҫ
        public int                 nFileFlagEx;                    // �ļ���־, ��λ��ʾ: bit0-��ʱ�ļ�, bit1-�ֶ��ļ�, bit2-�¼��ļ�, bit3-��Ҫ�ļ�, bit4-�ϳ��ļ�, bit5-������ͼƬ 0xFFFFFFFF-����¼��
        public int                 nDirection;                     // �������򣨳������ķ���    0-�� 1-���� 2-�� 3-���� 4-�� 5-���� 6-�� 7-���� 8-δ֪ -1-���з���
        public Pointer             szDirs;                       // ����Ŀ¼�б�,һ�οɲ�ѯ���Ŀ¼,Ϊ�ձ�ʾ��ѯ����Ŀ¼��Ŀ¼֮���Էֺŷָ�,�硰/mnt/dvr/sda0;/mnt/dvr/sda1��,szDirs==null ��"" ��ʾ��ѯ����
        public Pointer             pEventTypes;                    // ����ѯ���¼���������ָ��,�¼�����,���"���ܷ����¼�����",��ΪNULL����Ϊ��ѯ�����¼������������û����룩
        public int                 nEventTypeNum;                  // �¼����������С
        public Pointer             pszDeviceAddress;               // �豸��ַ, NULL��ʾ���ֶβ�������
        public Pointer             pszMachineAddress;              // ��������ص�, NULL��ʾ���ֶβ�������
        public Pointer             pszVehicleSign;                 // ������ʶ, ���� "Unknown"-δ֪, "Audi"-�µ�, "Honda"-����... NULL��ʾ���ֶβ�������
        public int[]               bReserved = new int[32];       // �����ֶ�
    }
    
    // DH_MEDIA_QUERY_TRAFFICCAR_EX��Ӧ�Ĳ�ѯ����
    public static class MEDIA_QUERY_TRAFFICCAR_PARAM_EX extends Structure
    {
        public int               dwSize;
        public MEDIA_QUERY_TRAFFICCAR_PARAM stuParam;                  // ������ѯ����
        
        public MEDIA_QUERY_TRAFFICCAR_PARAM_EX() {
        	this.dwSize = this.size();
        }
    }
    
    // DH_MEDIA_QUERY_TRAFFICCAR��ѯ������media�ļ���Ϣ
    public static class MEDIAFILE_TRAFFICCAR_INFO extends Structure
    {
        public int				   ch;                                 // ͨ����
        public byte[]              szFilePath = new byte[128];         // �ļ�·��
        public int        		   size;                               // �ļ�����
        public NET_TIME            starttime;                          // ��ʼʱ��
        public NET_TIME            endtime;                            // ����ʱ��
        public int                 nWorkDirSN;                         // ����Ŀ¼���                                    
        public byte                nFileType;                          // �ļ�����  1��jpgͼƬ
        public byte                bHint;                              // �ļ���λ����
        public byte                bDriveNo;                           // ���̺�
        public byte                bReserved2;
        public int                 nCluster;                           // �غ�
        public byte                byPictureType;                      // ͼƬ����, 0-��ͨ, 1-�ϳ�, 2-��ͼ
        public byte[]              bReserved = new byte[3];            // �����ֶ�

        //�����ǽ�ͨ������Ϣ
        public byte[]              szPlateNumber = new byte[32];       // ���ƺ���
        public byte[]              szPlateType = new byte[32];         // ��������"Unknown" δ֪; "Normal" ���ƺ���; "Yellow" ����; "DoubleYellow" ˫���β��
                                                                       // "Police" ����; "Armed" �侯��; "Military" ���Ӻ���; "DoubleMilitary" ����˫��
                                                                       // "SAR" �۰���������; "Trainning" ����������; "Personal" ���Ժ���; "Agri" ũ����
                                                                       // "Embassy" ʹ�ݺ���; "Moto" Ħ�г�����; "Tractor" ����������; "Other" ��������
        public byte[]              szPlateColor = new byte[16];        // ������ɫ:"Blue","Yellow", "White","Black"
        public byte[]              szVehicleColor = new byte[16];      // ������ɫ:"White", "Black", "Red", "Yellow", "Gray", "Blue","Green"
        public int                 nSpeed;                             // ����,��λ Km/H
        public int                 nEventsNum;                         // �������¼�����
        public int[]               nEvents = new int[32];              // �������¼��б�,����ֵ��ʾ��Ӧ���¼�,���"���ܷ����¼�����"        
        public int                 dwBreakingRule;                     // ����Υ����������,��һλ:�����; �ڶ�λ:�����涨������ʻ;
                                                                       // ����λ:����; ����λ��Υ�µ�ͷ;����Ĭ��Ϊ:��ͨ·���¼�
        public byte[]              szVehicleSize = new byte[16];       // ������С����:"Light-duty":С�ͳ�;"Medium":���ͳ�; "Oversize":���ͳ�
        public byte[]              szChannelName = new byte[NET_CHAN_NAME_LEN];    // ���ػ�Զ�̵�ͨ������
        public byte[]              szMachineName = new byte[NET_MAX_NAME_LEN];     // ���ػ�Զ���豸����

        public int                 nSpeedUpperLimit;                   // �ٶ����� ��λ: km/h
        public int                 nSpeedLowerLimit;                   // �ٶ����� ��λ: km/h    
        public int                 nGroupID;                           // �¼��������
        public byte                byCountInGroup;                     // һ���¼����ڵ�ץ������
        public byte                byIndexInGroup;                     // һ���¼����ڵ�ץ�����
        public byte                byLane;                             // ����,�μ�MEDIA_QUERY_TRAFFICCAR_PARAM
        public byte[]              bReserved1 = new byte[21];          // ����
        public NET_TIME            stSnapTime;                         // ץ��ʱ��
        public int                 nDirection;                         // ��������,�μ�MEDIA_QUERY_TRAFFICCAR_PARAM
        public byte[]              szMachineAddress = new byte[MAX_PATH]; // ��������ص�
    }
    
    // DH_MEDIA_QUERY_TRAFFICCAR_EX��ѯ�������ļ���Ϣ
    public static class MEDIAFILE_TRAFFICCAR_INFO_EX extends Structure
    {
        public int               dwSize;
        public MEDIAFILE_TRAFFICCAR_INFO stuInfo;  									 // ������Ϣ
        public byte[]            szDeviceAddr = new byte[NET_COMMON_STRING_256];     // �豸��ַ
        public byte[]            szVehicleSign = new byte[NET_COMMON_STRING_32];     // ������ʶ, ���� "Unknown"-δ֪, "Audi"-�µ�, "Honda"-����...
        public byte[]            szCustomParkNo = new byte[NET_COMMON_STRING_64];    // �Զ��峵λ�ţ�ͣ�����ã�
        
        public MEDIAFILE_TRAFFICCAR_INFO_EX() { 
        	this.dwSize = this.size();
        }
    }

    public static class NET_PIC_INFO_EX extends Structure
    {
        public int dwSize;//�ṹ���С
        public int dwFileLenth;//�ļ���С,��λ:�ֽ�
        public byte[] szFilePath = new byte[MAX_PATH];//�ļ�·��
        
        public NET_PIC_INFO_EX()
        {
            this.dwSize = this.size();
        }
    }
    
    //���򣻸��߾ఴ����8192�ı���
    public static class NET_RECT extends Structure
    {
        public int left;
        public int top;
        public int right;
        public int bottom;
    }
    

	 // ʱ��νṹ                                                                
	 public static class NET_TSECT extends Structure
	 {
	    public int             bEnable;        // ����ʾ¼��ʱ���ʱ,��λ��ʾ�ĸ�ʹ��,�ӵ�λ����λ�ֱ��ʾ����¼�󡢱���¼����ͨ¼�󡢶���ͱ���ͬʱ������¼��
	    public int             iBeginHour;
	    public int             iBeginMin;
	    public int             iBeginSec;
	    public int             iEndHour;
	    public int             iEndMin;
	    public int             iEndSec;
	 } 
    

    public static class DH_RECT extends Structure
    {
    	public NativeLong left;
    	public NativeLong top;
    	public NativeLong right;
    	public NativeLong bottom;
    }
    
    //��ά�ռ��
    public static class NET_POINT extends Structure
    {
        public short nx;
        public short ny;
    }
    
    public static class NET_CANDIDAT_PIC_PATHS extends Structure
    {
        public int dwSize;//�ṹ���С
        public int nFileCount;//ʵ���ļ�����
        public NET_PIC_INFO_EX[] stFiles = (NET_PIC_INFO_EX[])new NET_PIC_INFO_EX().toArray(NET_MAX_PERSON_IMAGE_NUM);//�ļ���Ϣ
        
        public NET_CANDIDAT_PIC_PATHS()
        {
            this.dwSize = this.size();
        }
    }
    
    //��ɫ����
    public static class EM_COLOR_TYPE extends Structure
    {   
        public static final int NET_COLOR_TYPE_RED = 0;//��ɫ
        public static final int NET_COLOR_TYPE_YELLOW = 1;//��ɫ
        public static final int NET_COLOR_TYPE_GREEN = 2; //��ɫ
        public static final int NET_COLOR_TYPE_CYAN = 3; //��ɫ
        public static final int NET_COLOR_TYPE_BLUE = 4; //��ɫ
        public static final int NET_COLOR_TYPE_PURPLE = 5; //��ɫ
        public static final int NET_COLOR_TYPE_BLACK = 6; //��ɫ
        public static final int NET_COLOR_TYPE_WHITE = 7; //��ɫ
        public static final int NET_COLOR_TYPE_MAX = 8; 
    }
    
    //��Ƶ����������Ϣ�ṹ��
    public static class NET_MSG_OBJECT extends Structure
    {
        public int nObjectID;//����ID,ÿ��ID��ʾһ��Ψһ������
        public byte[] szObjectType = new byte[128];//��������
        public int nConfidence;//���Ŷ�(0~255),ֵԽ���ʾ���Ŷ�Խ��
        public int nAction;//���嶯��:1:Appear2:Move3:Stay
        public DH_RECT BoundingBox;//��Χ��
        public NET_POINT Center;//��������
        public int nPolygonNum;//����ζ������
        public NET_POINT[] Contour = (NET_POINT[])new NET_POINT().toArray(NET_MAX_POLYGON_NUM);//�Ͼ�ȷ�����������
        public int rgbaMainColor;//��ʾ���ơ������������Ҫ��ɫ�����ֽڱ�ʾ,�ֱ�Ϊ�졢�̡�����͸����,����:RGBֵΪ(0,255,0),͸����Ϊ0ʱ,��ֵΪ0x00ff0000.
        public byte[] szText = new byte[128];//��������صĴ�0�������ı�,���糵��,��װ��ŵȵ�
                                                                // "ObjectType"Ϊ"Vehicle"����"Logo"ʱ������ʹ��Logo��Vehicle��Ϊ�˼����ϲ�Ʒ����ʾ����,֧�֣�
                                                                // "Unknown"δ֪ 
                                                                // "Audi" �µ�
                                                                // "Honda" ����
                                                                // "Buick" ���
                                                                // "Volkswagen" ����
                                                                // "Toyota" ����
                                                                // "BMW" ����
                                                                // "Peugeot" ����
                                                                // "Ford" ����
                                                                // "Mazda" ���Դ�
                                                                // "Nissan" ��ɣ
                                                                // "Hyundai" �ִ�
                                                                // "Suzuki" ��ľ
                                                                // "Citroen" ѩ����
                                                                // "Benz" ����
                                                                // "BYD" ���ǵ�
                                                                // "Geely" ����
                                                                // "Lexus" �׿���˹
                                                                // "Chevrolet" ѩ����
                                                                // "Chery" ����
                                                                // "Kia" ����
                                                                // "Charade" ����
                                                                // "DF" ����
                                                                // "Naveco" ��ά��
                                                                // "SGMW" ����
                                                                // "Jinbei" ��
                                                                // "JAC" ����
                                                                // "Emgrand" �ۺ�
                                                                // "ChangAn" ����
                                                                // "Great Wall" ����
                                                                // "Skoda" ˹�´�
                                                                // "BaoJun" ����
                                                                // "Subaru" ˹��³
                                                                // "LandWind" ½��
                                                                // "Luxgen" ���ǽ�
                                                                // "Renault" ��ŵ
                                                                // "Mitsubishi" ����
                                                                // "Roewe" ����
                                                                // "Cadillac" ��������
                                                                // "MG" ����
                                                                // "Zotye" ��̩
                                                                // "ZhongHua" �л�
                                                                // "Foton" ����
                                                                // "SongHuaJiang" �ɻ���
                                                                // "Opel" ŷ��
                                                                // "HongQi" һ������
                                                                // "Fiat" ������
                                                                // "Jaguar" �ݱ�
                                                                // "Volvo" �ֶ���
                                                                // "Acura" ک��
                                                                // "Porsche" ��ʱ��
                                                                // "Jeep" ����
                                                                // "Bentley" ����
                                                                // "Bugatti" ���ӵ�
                                                                // "ChuanQi" ����
                                                                // "Daewoo" ����
                                                                // "DongNan" ����
                                                                // "Ferrari" ������
                                                                // "Fudi" ����
                                                                // "Huapu" ����
                                                                // "HawTai" ��̩
                                                                // "JMC" ����
                                                                // "JingLong" �����ͳ�
                                                                // "JoyLong" ����
                                                                // "Karry" ����
                                                                // "Chrysler" ����˹��
                                                                // "Lamborghini" ��������
                                                                // "RollsRoyce" ��˹��˹
                                                                // "Linian" ����
                                                                // "LiFan" ����
                                                                // "LieBao" �Ա�
                                                                // "Lincoln" �ֿ�
                                                                // "LandRover" ·��
                                                                // "Lotus" ·��˹
                                                                // "Maserati" ��ɯ����
                                                                // "Maybach" ���ͺ�
                                                                // "Mclaren" ������
                                                                // "Youngman" ����ͳ�
                                                                // "Tesla" ��˹��
                                                                // "Rely" ����
                                                                // "Lsuzu" ��ʮ��
                                                                // "Yiqi" һ��
                                                                // "Infiniti" Ӣ�����
                                                                // "YuTong" ��ͨ�ͳ�
                                                                // "AnKai" �����ͳ�
                                                                // "Canghe" ����
                                                                // "HaiMa" ����
                                                                // "Crown" ����ʹ�
                                                                // "HuangHai" �ƺ�
                                                                // "JinLv" ���ÿͳ�
                                                                // "JinNing" ����
                                                                // "KuBo" �Ჩ
                                                                // "Europestar" ����
                                                                // "MINI" ����
                                                                // "Gleagle" ȫ��ӥ
                                                                // "ShiDai" ʱ��
                                                                // "ShuangHuan" ˫��
                                                                // "TianYe" ��Ұ
                                                                // "WeiZi" ����
                                                                // "Englon" Ӣ��
                                                                // "ZhongTong" ��ͨ�ͳ�
                                                                // "Changan" �����γ�
                                                                // "Yuejin" Ծ��
                                                                // "Taurus" ��ţ��
                                                                // "Alto" ����
                                                                // "Weiwang" ����
                                                                // "Chenglong" ����
                                                                // "Haige" ����
                                                                // "Shaolin" ���ֿͳ�
                                                                // "Beifang" �����ͳ�
                                                                // "Beijing" ��������
                                                                // "Hafu" ����
        public byte[] szObjectSubType = new byte[64];//���������,���ݲ�ͬ����������,����ȡ���������ͣ�
                                                                             // Vehicle Category:"Unknown"  δ֪,"Motor" ������,"Non-Motor":�ǻ�����,"Bus": ������,"Bicycle" ���г�,"Motorcycle":Ħ�г�,"PassengerCar":�ͳ�,
                                                                             // "LargeTruck":�����,    "MidTruck":�л���,"SaloonCar":�γ�,"Microbus":�����,"MicroTruck":С����,"Tricycle":���ֳ�,    "Passerby":����                                                    
                                                                             // Plate Category��"Unknown" δ֪,"Normal" ���ƺ���,"Yellow" ����,"DoubleYellow" ˫���β��,"Police" ����"Armed" �侯��,
                                                                             // "Military" ���Ӻ���,"DoubleMilitary" ����˫��,"SAR" �۰���������,"Trainning" ����������
                                                                             // "Personal" ���Ժ���,"Agri" ũ����,"Embassy" ʹ�ݺ���,"Moto" Ħ�г�����,"Tractor" ����������,"Other" ��������
                                                                             // HumanFace Category:"Normal" ��ͨ����,"HideEye" �۲��ڵ�,"HideNose" �����ڵ�,"HideMouth" �첿�ڵ�
        public byte[] byReserved1 = new byte[3];
        public byte bPicEnble;//�Ƿ��������ӦͼƬ�ļ���Ϣ, ����Сbool, ȡֵ0����1
        public NET_PIC_INFO stPicInfo;//�����ӦͼƬ��Ϣ
        public byte bShotFrame;//�Ƿ���ץ���ŵ�ʶ����,  ����Сbool, ȡֵ0����1
        public byte bColor;//������ɫ(rgbaMainColor)�Ƿ����, ����Сbool, ȡֵ0����1
        public byte byReserved2;
        public byte byTimeType;//ʱ���ʾ����,���EM_TIME_TYPE˵��
        public NET_TIME_EX stuCurrentTime;//�����ƵŨ��,��ǰʱ���������ץ�Ļ�ʶ��ʱ,�Ὣ��ʶ������֡����һ����Ƶ֡��jpegͼƬ��,��֡����ԭʼ��Ƶ�еĳ���ʱ�䣩
        public NET_TIME_EX stuStartTime;//��ʼʱ��������忪ʼ����ʱ��
        public NET_TIME_EX stuEndTime;//����ʱ���������������ʱ��
        public DH_RECT stuOriginalBoundingBox;//��Χ��(��������)
        public DH_RECT stuSignBoundingBox;//���������Χ��
        public int dwCurrentSequence;//��ǰ֡��ţ�ץ���������ʱ��֡��
        public int dwBeginSequence;//��ʼ֡��ţ����忪ʼ����ʱ��֡��ţ�
        public int dwEndSequence;//����֡��ţ���������ʱ��֡��ţ�
        public long nBeginFileOffse;//��ʼʱ�ļ�ƫ��,��λ:�֣����忪ʼ����ʱ,��Ƶ֡��ԭʼ��Ƶ�ļ���������ļ���ʼ����ƫ�ƣ�
        public long nEndFileOffset;//����ʱ�ļ�ƫ��,��λ:�ֽڣ���������ʱ,��Ƶ֡��ԭʼ��Ƶ�ļ���������ļ���ʼ����ƫ�ƣ�
        public byte[] byColorSimilar = new byte[EM_COLOR_TYPE.NET_COLOR_TYPE_MAX];//������ɫ���ƶ�,ȡֵ��Χ��0-100,�����±�ֵ����ĳ����ɫ,��� EM_COLOR_TYPE
        public byte[] byUpperBodyColorSimilar = new byte[EM_COLOR_TYPE.NET_COLOR_TYPE_MAX];//�ϰ���������ɫ���ƶ�(��������Ϊ��ʱ��Ч)
        public byte[] byLowerBodyColorSimilar = new byte[EM_COLOR_TYPE.NET_COLOR_TYPE_MAX];//�°���������ɫ���ƶ�(��������Ϊ��ʱ��Ч)
        public int nRelativeID;//�������ID
        public byte[] szSubText = new byte[20];//"ObjectType"Ϊ"Vehicle"����"Logo"ʱ,��ʾ�����µ�ĳһ��ϵ,����µ�A6L,���ڳ�ϵ�϶�,SDKʵ��ʱ͸�����ֶ�,�豸��ʵ��д��
        public byte[] byReserved = new byte[2];
        
        public NET_MSG_OBJECT()
        {
            // ǿ�Ʋ���������ֽڶ���
            setAlignType(ALIGN_GNUC);
        }
    }
    
    //NET_FILE_QUERY_FACE��Ӧ������ʶ�����FINDNEXT��ѯ���ز���
    public static class MEDIAFILE_FACERECOGNITION_INFO extends Structure
    {
        public int dwSize;//�ṹ���С
        public int bGlobalScenePic;//ȫ��ͼ�Ƿ����, BOOL���ͣ�ȡֵ0��1
        public NET_PIC_INFO_EX stGlobalScenePic;//ȫ��ͼƬ�ļ�·��
        public NET_MSG_OBJECT stuObject;//Ŀ������������Ϣ
        public NET_PIC_INFO_EX stObjectPic;//Ŀ�������ļ�·��
        public int nCandidateNum;//��ǰ����ƥ�䵽�ĺ�ѡ��������
        public CANDIDATE_INFO[] stuCandidates = (CANDIDATE_INFO[])new CANDIDATE_INFO().toArray(NET_MAX_CANDIDATE_NUM);//��ǰ����ƥ�䵽�ĺ�ѡ������Ϣ
        public NET_CANDIDAT_PIC_PATHS[] stuCandidatesPic = (NET_CANDIDAT_PIC_PATHS[])new NET_CANDIDAT_PIC_PATHS().toArray(NET_MAX_CANDIDATE_NUM);//��ǰ����ƥ�䵽�ĺ�ѡ����ͼƬ�ļ�·��
        public NET_TIME stTime;//��������ʱ��
        public byte[] szAddress = new byte[MAX_PATH];//���������ص�
        public int nChannelId;//ͨ����
        
        public MEDIAFILE_FACERECOGNITION_INFO()
        {
            this.dwSize = this.size();
        }
    }
    
    //ÿ����Ƶ����ͨ����Ӧ�������¼����򣺻�����pRuleBuf������¼�������Ϣ��ÿ���¼�������Ϣ����ΪCFG_RULE_INFO+"�¼����Ͷ�Ӧ�Ĺ������ýṹ��"��
    public static class CFG_ANALYSERULES_INFO extends Structure
    {
        public int nRuleCount;//�¼��������
        public Pointer pRuleBuf;//ÿ����Ƶ����ͨ����Ӧ����Ƶ�����¼��������û���, char *
        public int nRuleLen;//�����С
    }
    
    // ����ͨ����Ϣ
    public static class CFG_RULE_COMM_INFO extends Structure
    {
    	public byte 		bRuleId;							// ������
    	public int   		emClassType;						// ���������ĳ���, EM_SCENE_TYPE
    	public byte[] 		bReserved = new byte[512];			// �����ֽ�
    }
    
    public static class CFG_RULE_INFO extends Structure
    {
        public int dwRuleType;//�¼����ͣ����dhnetsdk.h��"���ܷ����¼�����"
        public int nRuleSize;//���¼����͹������ýṹ���С
    	public CFG_RULE_COMM_INFO  stuRuleCommInfo;					// ����ͨ����Ϣ
    }
    
    //���򶥵���Ϣ
    public static class CFG_POLYGON extends Structure
    {
        public int nX;//0~8191
        public int nY;
    }
    
    //������Ϣ
    public static class CFG_REGION extends Structure
    {
        public int nPointNum;
        public CFG_POLYGON[] stuPolygon = (CFG_POLYGON[])new CFG_POLYGON().toArray(MAX_POLYGON_NUM);
    }
    
    public static class CFG_SIZE_Attribute extends Union
    {
        public float nWidth;//��
        public float nArea;//���
    }
    
    //Size
    public static class CFG_SIZE extends Structure
    {
        public CFG_SIZE_Attribute attr;
        public float nHeight;//��
    }
    
    //У׼����Ϣ
    public static class CFG_CALIBRATEBOX_INFO extends Structure
    {
        public CFG_POLYGON stuCenterPoint;//У׼�����ĵ�����(��������һ����[0,8191]����)
        public float fRatio;//��Ի�׼У׼��ı���(����1��ʾ��׼���С��0.5��ʾ��׼���С��һ��)
    }
    
    //�ߴ������
    public static class CFG_SIZEFILTER_INFO extends Structure
    {
        public int nCalibrateBoxNum;//У׼�����
        public CFG_CALIBRATEBOX_INFO[] stuCalibrateBoxs = (CFG_CALIBRATEBOX_INFO[])new CFG_CALIBRATEBOX_INFO().toArray(MAX_CALIBRATEBOX_NUM);//У׼��(Զ�˽��˱궨ģʽ����Ч)
        public byte bMeasureModeEnable;//������ʽ�����Ƿ���Ч�� ����bool, ȡֵ0��1
        public byte bMeasureMode;//������ʽ,0-���أ�����ҪԶ�ˡ����˱궨,1-ʵ�ʳ��ȣ���λ����,2-Զ�˽��˱궨�������
        public byte bFilterTypeEnable;//�������Ͳ����Ƿ���Ч�� ����bool, ȡֵ0��1
        // ByArea,ByRatio�������ݣ�ʹ�ö�����ByArea��ByRatioѡ����� 2012/03/06
        public byte bFilterType;//��������:0:"ByLength",1:"ByArea",2"ByWidthHeight"
        public byte[] bReserved = new byte[2];//�����ֶ�
        public byte bFilterMinSizeEnable;//������С�ߴ�����Ƿ���Ч�� ����bool, ȡֵ0��1
        public byte bFilterMaxSizeEnable;//�������ߴ�����Ƿ���Ч�� ����bool, ȡֵ0��1
        public CFG_SIZE stuFilterMinSize;//������С�ߴ�"ByLength"ģʽ�±�ʾ��ߵĳߴ磬"ByArea"ģʽ�¿��ʾ���������Ч(Զ�˽��˱궨ģʽ�±�ʾ��׼��Ŀ�߳ߴ�)��
        public CFG_SIZE stuFilterMaxSize;//�������ߴ�"ByLength"ģʽ�±�ʾ��ߵĳߴ磬"ByArea"ģʽ�¿��ʾ���������Ч(Զ�˽��˱궨ģʽ�±�ʾ��׼��Ŀ�߳ߴ�)��
        public byte abByArea;//����bool, ȡֵ0��1
        public byte abMinArea;//����bool, ȡֵ0��1
        public byte abMaxArea;//����bool, ȡֵ0��1
        public byte abMinAreaSize;//����bool, ȡֵ0��1
        public byte abMaxAreaSize;//����bool, ȡֵ0��1
        public byte bByArea;//�Ƿ��������ͨ������ComplexSizeFilter�ж��Ƿ���ã� ����bool, ȡֵ0��1
        public byte[] bReserved1 = new byte[2];
        public float nMinArea;//��С���
        public float nMaxArea;//������
        public CFG_SIZE stuMinAreaSize;//��С������ο�ߴ�"������ʽ"Ϊ"����"ʱ����ʾ��С������ο�Ŀ�߳ߴ磻"������ʽ"Ϊ"Զ�˽��˱궨ģʽ"ʱ����ʾ��׼�����С��߳ߴ磻
        public CFG_SIZE stuMaxAreaSize;//���������ο�ߴ�,ͬ��
        public byte abByRatio;//����bool, ȡֵ0��1
        public byte abMinRatio;//����bool, ȡֵ0��1
        public byte abMaxRatio;//����bool, ȡֵ0��1
        public byte abMinRatioSize;//����bool, ȡֵ0��1
        public byte abMaxRatioSize;//����bool, ȡֵ0��1
        public byte bByRatio;//�Ƿ񰴿�߱ȹ���ͨ������ComplexSizeFilter�ж��Ƿ���ã� ����bool, ȡֵ0��1
        public byte[] bReserved2 = new byte[2];
        public double dMinRatio;//��С��߱�
        public double dMaxRatio;//����߱�
        public CFG_SIZE stuMinRatioSize;//��С��߱Ⱦ��ο�ߴ磬��С��߱ȶ�Ӧ���ο�Ŀ�߳ߴ硣
        public CFG_SIZE stuMaxRatioSize;//����߱Ⱦ��ο�ߴ磬ͬ��
        public int nAreaCalibrateBoxNum;//���У׼�����
        public CFG_CALIBRATEBOX_INFO[] stuAreaCalibrateBoxs = (CFG_CALIBRATEBOX_INFO[])new CFG_CALIBRATEBOX_INFO().toArray(MAX_CALIBRATEBOX_NUM);//���У׼��
        public int nRatioCalibrateBoxs;//���У׼�����
        public CFG_CALIBRATEBOX_INFO[] stuRatioCalibrateBoxs = (CFG_CALIBRATEBOX_INFO[])new CFG_CALIBRATEBOX_INFO().toArray(MAX_CALIBRATEBOX_NUM);//���У׼��
        public byte abBySize;//�������ʹ�ܲ����Ƿ���Ч�� ����bool, ȡֵ0��1
        public byte bBySize;//�������ʹ�ܣ� ����bool, ȡֵ0��1
    }
    
    //���������ض��Ĺ�����
    public static class CFG_OBJECT_SIZEFILTER_INFO extends Structure
    {
        public byte[] szObjectType = new byte[MAX_NAME_LEN];//��������
        public CFG_SIZEFILTER_INFO stSizeFilter;//��Ӧ�ĳߴ������
    }
    
    //�����������������
    public static class EM_SEPCIALREGION_PROPERTY_TYPE extends Structure
    {
         public static final int EM_SEPCIALREGION_PROPERTY_TYPE_HIGHLIGHT = 1;//���������̼��������д�����
         public static final int EM_SEPCIALREGION_PROPERTY_TYPE_REGULARBLINK = 2; //���ɵ���˸���忨������д�����
         public static final int EM_SEPCIALREGION_PROPERTY_TYPE_IREGULARBLINK = 3; //�����ɵ���˸����Ļ������д�����
         public static final int EM_SEPCIALREGION_PROPERTY_TYPE_NUM = 4; 
    }
    
    //������������ָ�Ӽ���������ֳ����������������Ե�����
    public static class CFG_SPECIALDETECT_INFO extends Structure
    {
        public int nDetectNum;//������򶥵���
        public CFG_POLYGON[] stDetectRegion = (CFG_POLYGON[])new CFG_POLYGON().toArray(MAX_POLYGON_NUM);//�������
        public int nPropertyNum;//�����������Ը���
        public int[] nPropertys = new int[EM_SEPCIALREGION_PROPERTY_TYPE.EM_SEPCIALREGION_PROPERTY_TYPE_NUM];//������������
    }
    
    //���������������
    public static class CFG_CATEGORY_TYPE extends Structure
    {
        public static final int CFG_CATEGORY_TYPE_UNKNOW = 0; //δ֪����
         //������������
         public static final int CFG_CATEGORY_VEHICLE_TYPE_MOTOR = 1; //"Motor"������
         public static final int CFG_CATEGORY_VEHICLE_TYPE_NON_MOTOR = 2; //"Non-Motor"�ǻ�����
         public static final int CFG_CATEGORY_VEHICLE_TYPE_BUS = 3; //"Bus"������
         public static final int CFG_CATEGORY_VEHICLE_TYPE_BICYCLE = 4; //"Bicycle"���г�
         public static final int CFG_CATEGORY_VEHICLE_TYPE_MOTORCYCLE = 5; //"Motorcycle"Ħ�г�
         public static final int CFG_CATEGORY_VEHICLE_TYPE_UNLICENSEDMOTOR = 6; //"UnlicensedMotor":���ƻ�����
         public static final int CFG_CATEGORY_VEHICLE_TYPE_LARGECAR = 7; //"LargeCar"��������
         public static final int CFG_CATEGORY_VEHICLE_TYPE_MICROCAR = 8; //"MicroCar"С������
         public static final int CFG_CATEGORY_VEHICLE_TYPE_EMBASSYCAR = 9; //"EmbassyCar"ʹ������
         public static final int CFG_CATEGORY_VEHICLE_TYPE_MARGINALCAR = 10; //"MarginalCar"�������
         public static final int CFG_CATEGORY_VEHICLE_TYPE_AREAOUTCAR = 11; //"AreaoutCar"��������
         public static final int CFG_CATEGORY_VEHICLE_TYPE_FOREIGNCAR = 12; //"ForeignCar"�⼮����
         public static final int CFG_CATEGORY_VEHICLE_TYPE_DUALTRIWHEELMOTORCYCLE = 13; //"DualTriWheelMotorcycle"��������Ħ�г�
         public static final int CFG_CATEGORY_VEHICLE_TYPE_LIGHTMOTORCYCLE = 14; //"LightMotorcycle"���Ħ�г�
         public static final int CFG_CATEGORY_VEHICLE_TYPE_EMBASSYMOTORCYCLE = 15 ; //"EmbassyMotorcycle"ʹ��Ħ�г�
         public static final int CFG_CATEGORY_VEHICLE_TYPE_MARGINALMOTORCYCLE = 16; //"MarginalMotorcycle"���Ħ�г�
         public static final int CFG_CATEGORY_VEHICLE_TYPE_AREAOUTMOTORCYCLE = 17; //"AreaoutMotorcycle"����Ħ�г�
         public static final int CFG_CATEGORY_VEHICLE_TYPE_FOREIGNMOTORCYCLE = 18; //"ForeignMotorcycle"�⼮Ħ�г�
         public static final int CFG_CATEGORY_VEHICLE_TYPE_FARMTRANSMITCAR = 19; //"FarmTransmitCar"ũ�����䳵
         public static final int CFG_CATEGORY_VEHICLE_TYPE_TRACTOR = 20; //"Tractor"������
         public static final int CFG_CATEGORY_VEHICLE_TYPE_TRAILER = 21; //"Trailer"�ҳ�
         public static final int CFG_CATEGORY_VEHICLE_TYPE_COACHCAR = 22; //"CoachCar"��������
         public static final int CFG_CATEGORY_VEHICLE_TYPE_COACHMOTORCYCLE = 23; //"CoachMotorcycle"����Ħ�г�
         public static final int CFG_CATEGORY_VEHICLE_TYPE_TRIALCAR = 24; //"TrialCar"��������
         public static final int CFG_CATEGORY_VEHICLE_TYPE_TRIALMOTORCYCLE = 25; //"TrialMotorcycle"����Ħ�г�
         public static final int CFG_CATEGORY_VEHICLE_TYPE_TEMPORARYENTRYCAR = 26; //"TemporaryEntryCar"��ʱ�뾳����
         public static final int CFG_CATEGORY_VEHICLE_TYPE_TEMPORARYENTRYMOTORCYCLE = 27; //"TemporaryEntryMotorcycle"��ʱ�뾳Ħ�г�
         public static final int CFG_CATEGORY_VEHICLE_TYPE_TEMPORARYSTEERCAR = 28; //"TemporarySteerCar"��ʱ��ʻ��
         public static final int CFG_CATEGORY_VEHICLE_TYPE_PASSENGERCAR = 29; //"PassengerCar"�ͳ�
         public static final int CFG_CATEGORY_VEHICLE_TYPE_LARGETRUCK = 30; //"LargeTruck"�����
         public static final int CFG_CATEGORY_VEHICLE_TYPE_MIDTRUCK =31 ; //"MidTruck"�л���
         public static final int CFG_CATEGORY_VEHICLE_TYPE_SALOONCAR = 32; //"SaloonCar"�γ�
         public static final int CFG_CATEGORY_VEHICLE_TYPE_MICROBUS = 33; //"Microbus"�����
         public static final int CFG_CATEGORY_VEHICLE_TYPE_MICROTRUCK = 34; //"MicroTruck"С����
         public static final int CFG_CATEGORY_VEHICLE_TYPE_TRICYCLE = 35; //"Tricycle"���ֳ�
         public static final int CFG_CATEGORY_VEHICLE_TYPE_PASSERBY = 36; //"Passerby"����
         //������������
         public static final int CFG_CATEGORY_PLATE_TYPE_NORMAL = 37; //"Normal"���ƺ���
         public static final int CFG_CATEGORY_PLATE_TYPE_YELLOW = 38; //"Yellow"����
         public static final int CFG_CATEGORY_PLATE_TYPE_DOUBLEYELLOW = 39; //"DoubleYellow"˫���β��
         public static final int CFG_CATEGORY_PLATE_TYPE_POLICE = 40; //"Police"����
         public static final int CFG_CATEGORY_PLATE_TYPE_ARMED = 41; //"Armed"�侯��
         public static final int CFG_CATEGORY_PLATE_TYPE_MILITARY = 42; //"Military"���Ӻ���
         public static final int CFG_CATEGORY_PLATE_TYPE_DOUBLEMILITARY = 43; //"DoubleMilitary"����˫��
         public static final int CFG_CATEGORY_PLATE_TYPE_SAR = 44; //"SAR"�۰���������
         public static final int CFG_CATEGORY_PLATE_TYPE_TRAINNING = 45; //"Trainning"����������
         public static final int CFG_CATEGORY_PLATE_TYPE_PERSONAL = 46; //"Personal"���Ժ���
         public static final int CFG_CATEGORY_PLATE_TYPE_AGRI = 47; //"Agri"ũ����
         public static final int CFG_CATEGORY_PLATE_TYPE_EMBASSY = 48; //"Embassy"ʹ�ݺ���
         public static final int CFG_CATEGORY_PLATE_TYPE_MOTO = 49; //"Moto"Ħ�г�����
         public static final int CFG_CATEGORY_PLATE_TYPE_TRACTOR = 50; //"Tractor"����������
         public static final int CFG_CATEGORY_PLATE_TYPE_OFFICIALCAR = 51; //"OfficialCar"����
         public static final int CFG_CATEGORY_PLATE_TYPE_PERSONALCAR = 52; //"PersonalCar"˽�ҳ�
         public static final int CFG_CATEGORY_PLATE_TYPE_WARCAR = 53; //"WarCar"����
         public static final int CFG_CATEGORY_PLATE_TYPE_OTHER = 54; //"Other"��������
         public static final int CFG_CATEGORY_PLATE_TYPE_CIVILAVIATION = 55; //"Civilaviation"�񺽺���
         public static final int CFG_CATEGORY_PLATE_TYPE_BLACK = 56; //"Black"����
    }
    
    //��ͬ���������������ļ��ģ������
    public static class CFG_MODULE_INFO extends Structure
    {
        // ��Ϣ
        public byte[] szObjectType = new byte[MAX_NAME_LEN];//Ĭ����������,���"֧�ֵļ�����������б�"
        public byte bSnapShot;//�Ƿ��ʶ������ץͼ������bool��ȡֵ0��1
        public byte bSensitivity;//������,ȡֵ1-10��ֵԽС������Խ��
        public byte bMeasureModeEnable;//������ʽ�����Ƿ���Ч������bool��ȡֵ0��1
        public byte bMeasureMode;//������ʽ,0-���أ�����ҪԶ�ˡ����˱궨,1-ʵ�ʳ��ȣ���λ����,2-Զ�˽��˱궨�������
        public int nDetectRegionPoint;//������򶥵���
        public CFG_POLYGON[] stuDetectRegion = (CFG_POLYGON[])new CFG_POLYGON().toArray(MAX_POLYGON_NUM);//�������
        public int nTrackRegionPoint;//�������򶥵���
        public CFG_POLYGON[] stuTrackRegion = (CFG_POLYGON[])new CFG_POLYGON().toArray(MAX_POLYGON_NUM);//��������
        public byte bFilterTypeEnable;//�������Ͳ����Ƿ���Ч������bool��ȡֵ0��1
        // ByArea,ByRatio��������ʹö�����ByArea��ByRatioѡ����� 2012/03/06
        public byte nFilterType;//��������:0:"ByLength",1:"ByArea",2:"ByWidthHeight",3:"ByRatio":
        public byte bBackgroudEnable;//����ı������Ͳ����Ƿ���Ч������bool��ȡֵ0��1
        public byte bBackgroud;//����ı�������,0-��ͨ����,1-�߹�����
        public byte abBySize;//�������ʹ�ܲ����Ƿ���Ч������bool��ȡֵ0��1
        public byte bBySize;//�������ʹ�ܣ�����bool��ȡֵ0��1
        public byte bFilterMinSizeEnable;//������С�ߴ�����Ƿ���Ч������bool��ȡֵ0��1
        public byte bFilterMaxSizeEnable;//�������ߴ�����Ƿ���Ч������bool��ȡֵ0��1
        public CFG_SIZE stuFilterMinSize;//������С�ߴ�"ByLength"ģʽ�±�ʾ��ߵĳߴ磬"ByArea"ģʽ�¿��ʾ���������Ч��
        public CFG_SIZE stuFilterMaxSize;//�������ߴ�"ByLength"ģʽ�±�ʾ��ߵĳߴ磬"ByArea"ģʽ�¿��ʾ���������Ч��
        public int nExcludeRegionNum;//�ų�������
        public CFG_REGION[] stuExcludeRegion = (CFG_REGION[])new CFG_REGION().toArray(MAX_EXCLUDEREGION_NUM);//�ų�����
        public int nCalibrateBoxNum;//У׼�����
        public CFG_CALIBRATEBOX_INFO[] stuCalibrateBoxs = (CFG_CALIBRATEBOX_INFO[])new CFG_CALIBRATEBOX_INFO().toArray(MAX_CALIBRATEBOX_NUM);//У׼��(Զ�˽��˱궨ģʽ����Ч)
        public byte bAccuracy;//��⾫���Ƿ���Ч������bool��ȡֵ0��1
        public byte byAccuracy;//��⾫��
        public byte bMovingStep;//�㷨�ƶ������Ƿ���Ч������bool��ȡֵ0��1
        public byte byMovingStep;//�㷨�ƶ�����
        public byte bScalingFactor;//�㷨���������Ƿ���Ч������bool��ȡֵ0��1
        public byte byScalingFactor;//�㷨��������
        public byte[] bReserved2 = new byte[1];//�����ֶ�
        public byte abDetectBalance;//©������ƽ������Ƿ���Ч������bool��ȡֵ0��1
        public int nDetectBalance;//©������ƽ��0-����ģʽ(Ĭ��)1-©�����2-������
        public byte abByRatio;//����bool��ȡֵ0��1
        public byte abMinRatio;;//����bool��ȡֵ0��1
        public byte abMaxRatio;;//����bool��ȡֵ0��1
        public byte abMinAreaSize;;//����bool��ȡֵ0��1
        public byte abMaxAreaSize;;//����bool��ȡֵ0��1
        public byte bByRatio;//�Ƿ񰴿�߱ȹ���ͨ������ComplexSizeFilter�ж��Ƿ���ÿ��Ժ�nFilterType���ã�����bool��ȡֵ0��1
        public byte[] bReserved1 = new byte[2];
        public double dMinRatio;//��С��߱�
        public double dMaxRatio;//����߱�
        public CFG_SIZE stuMinAreaSize;//��С������ο�ߴ�"������ʽ"Ϊ"����"ʱ����ʾ��С������ο�Ŀ�߳ߴ磻"������ʽ"Ϊ"Զ�˽��˱궨ģʽ"ʱ����ʾ��׼�����С��߳ߴ磻
        public CFG_SIZE stuMaxAreaSize;//���������ο�ߴ�,ͬ��
        public byte abByArea;//����bool��ȡֵ0��1
        public byte abMinArea;//����bool��ȡֵ0��1
        public byte abMaxArea;//����bool��ȡֵ0��1
        public byte abMinRatioSize;//����bool��ȡֵ0��1
        public byte abMaxRatioSize;//����bool��ȡֵ0��1
        public byte bByArea;//�Ƿ��������ͨ������ComplexSizeFilter�ж��Ƿ���ÿ��Ժ�nFilterType���ã�����bool��ȡֵ0��1
        public byte[] bReserved3 = new byte[2];
        public float nMinArea;//��С���
        public float nMaxArea;//������
        public CFG_SIZE stuMinRatioSize;//��С��߱Ⱦ��ο�ߴ磬��С��߱ȶ�Ӧ���ο�Ŀ�߳ߴ硣
        public CFG_SIZE stuMaxRatioSize;//����߱Ⱦ��ο�ߴ磬ͬ��
        public int nAreaCalibrateBoxNum;//���У׼�����
        public CFG_CALIBRATEBOX_INFO[] stuAreaCalibrateBoxs = (CFG_CALIBRATEBOX_INFO[])new CFG_CALIBRATEBOX_INFO().toArray(MAX_CALIBRATEBOX_NUM);//���У׼��
        public int nRatioCalibrateBoxs;//����У׼�����
        public CFG_CALIBRATEBOX_INFO[] stuRatioCalibrateBoxs = (CFG_CALIBRATEBOX_INFO[])new CFG_CALIBRATEBOX_INFO().toArray(MAX_CALIBRATEBOX_NUM);//����У׼�����
        public byte bAntiDisturbance;//�Ƿ���ȥ�Ŷ�ģ�飬����bool��ȡֵ0��1
        public byte bBacklight;//�Ƿ�����⣬����bool��ȡֵ0��1
        public byte bShadow;//�Ƿ�����Ӱ������bool��ȡֵ0��1
        public byte bContourAssistantTrack;//�Ƿ��������������٣�����������ʶ��ʱ����ͨ����������������ʶ����������bool��ȡֵ0��1
        public int nPtzPresetId;//��̨Ԥ�õ㣬0~255��0��ʾ�̶�����������Ԥ�õ㡣����0��ʾ�ڴ�Ԥ�õ�ʱģ����Ч
        public int nObjectFilterNum;//�����ض��Ĺ���������
        public CFG_OBJECT_SIZEFILTER_INFO[] stObjectFilter= (CFG_OBJECT_SIZEFILTER_INFO[])new CFG_OBJECT_SIZEFILTER_INFO().toArray(MAX_OBJECT_LIST_SIZE);//�����ض��Ĺ�������Ϣ
        public int abObjectImageSize; //BOOL���ͣ�ȡֵ0��1
        public CFG_SIZE stObjectImageSize;//��֤����ͼ��ߴ���ͬ,��λ������,��֧��С����ȡֵ��>=0,0��ʾ�Զ�������С
        public int nSpecailDetectNum;//�������������
        public CFG_SPECIALDETECT_INFO[] stSpecialDetectRegions= (CFG_SPECIALDETECT_INFO[])new CFG_SPECIALDETECT_INFO().toArray(MAX_SPECIALDETECT_NUM);//����������Ϣ
        public int nAttribute;//��Ҫʶ����������Ը����� ����Ϊunsigned int
        public byte[] szAttributes = new byte[MAX_OBJECT_ATTRIBUTES_SIZE*MAX_NAME_LEN];//��Ҫʶ������������б���Category��
        public int abPlateAnalyseMode;//nPlateAnalyseMode�Ƿ���Ч, BOOL���ͣ�ȡֵ0��1
        public int nPlateAnalyseMode;//����ʶ��ģʽ��0-ֻʶ��ͷ����1-ֻʶ��β����2-��ͷ�������ȣ������д󲿷ֳ����ǳ�ͷ���գ�3-��β�������ȣ������д󲿷ֳ����ǳ�β���գ�
        //szAttributes���Դ���"Category"ʱ��Ч
        public int nCategoryNum;//��Ҫʶ�����������������
        public int[] emCategoryType= new int[MAX_CATEGORY_TYPE_NUMBER];//��������Ϣ, Ԫ��ȡCFG_CATEGORY_TYPE�е�ֵ
        public byte[] szSceneType = new byte[CFG_COMMON_STRING_16];		// ������������ڵĳ�������
    }
    
    public static class CFG_ANALYSEMODULES_INFO extends Structure
    {
        public int nMoudlesNum;//���ģ����
        public CFG_MODULE_INFO[] stuModuleInfo= (CFG_MODULE_INFO[])new CFG_MODULE_INFO().toArray(MAX_ANALYSE_MODULE_NUM);//ÿ����Ƶ����ͨ����Ӧ�ĸ�����������ļ��ģ������
    }
    
    // CLIENT_FindGroupInfo�ӿ��������
    public static class NET_IN_FIND_GROUP_INFO extends Structure
    {
        public int dwSize;
        public byte[] szGroupId = new byte[NET_COMMON_STRING_64];//��Ա��ID,Ψһ��ʶһ����Ա,Ϊ�ձ�ʾ��ѯȫ����Ա����Ϣ
        
        public NET_IN_FIND_GROUP_INFO()
        {
            this.dwSize = this.size();
        }
    }
    
    // ������������
    public static class EM_FACE_DB_TYPE extends Structure
    {
        public static final int NET_FACE_DB_TYPE_UNKOWN = 0; 
        public static final int NET_FACE_DB_TYPE_HISTORY = 1; //��ʷ���ݿ�,��ŵ��Ǽ�����������Ϣ,һ��û�а���������Ӧ��Ա��Ϣ
        public static final int NET_FACE_DB_TYPE_BLACKLIST = 2;//���������ݿ�
        public static final int NET_FACE_DB_TYPE_WHITELIST = 3; //���������ݿ�,����
        public static final int NET_FACE_DB_TYPE_ALARM = 4;//������
    }
    
    // ��Ա����Ϣ
    public static class NET_FACERECONGNITION_GROUP_INFO extends Structure
    {
        public int dwSize;
        public int emFaceDBType;//��Ա������,���EM_FACE_DB_TYPE, ȡֵΪEM_FACE_DB_TYPE�е�ֵ
        public byte[] szGroupId = new byte[NET_COMMON_STRING_64];//��Ա��ID,Ψһ��ʶһ����Ա(�����޸�,��Ӳ���ʱ��Ч)
        public byte[] szGroupName = new byte[NET_COMMON_STRING_128];//��Ա������
        public byte[] szGroupRemarks = new byte[NET_COMMON_STRING_256];//��Ա�鱸ע��Ϣ
        public int nGroupSize;//��ǰ������Ա��
        
        public NET_FACERECONGNITION_GROUP_INFO()
        {
            this.dwSize = this.size();
        }
    }
    
    // CLIENT_FindGroupInfo�ӿ��������
    public static class NET_OUT_FIND_GROUP_INFO extends Structure
    {
        public int dwSize;
        public Pointer pGroupInfos;//��Ա����Ϣ,���û�����ռ䣬 ָ��NET_FACERECONGNITION_GROUP_INFO��ָ��
        public int nMaxGroupNum;//��ǰ����������С
        public int nRetGroupNum;//�豸���ص���Ա�����
        
        public NET_OUT_FIND_GROUP_INFO()
        {
            this.dwSize = this.size();
        }
    }

    // ��Ա�����ö��
    public static class EM_OPERATE_FACERECONGNITION_GROUP_TYPE extends Structure
    {
        public static final int NET_FACERECONGNITION_GROUP_UNKOWN = 0;
        public static final int NET_FACERECONGNITION_GROUP_ADD = 1; //�����Ա����Ϣ
        public static final int NET_FACERECONGNITION_GROUP_MODIFY = 2; //�޸���Ա����Ϣ
        public static final int NET_FACERECONGNITION_GROUP_DELETE = 3; //ɾ����Ա����Ϣ
    }
    
    // CLIENT_OperateFaceRecognitionGroup�ӿ��������
    public static class NET_IN_OPERATE_FACERECONGNITION_GROUP extends Structure
    {
        public int dwSize;
        public int emOperateType;//��������, ȡֵΪEM_OPERATE_FACERECONGNITION_GROUP_TYPE�е�ֵ
        public Pointer pOPerateInfo;//��ز�����Ϣ��ָ��void *
        
        public NET_IN_OPERATE_FACERECONGNITION_GROUP()
        {
            this.dwSize = this.size();
        }
    }
    
    // CLIENT_OperateFaceRecognitionGroup�ӿ��������
    public static class NET_OUT_OPERATE_FACERECONGNITION_GROUP extends Structure
    {
        public int dwSize;
        public byte[] szGroupId = new byte[NET_COMMON_STRING_64];//������¼����Ա��ID,Ψһ��ʶһ����Ա
        
        public NET_OUT_OPERATE_FACERECONGNITION_GROUP()
        {
            this.dwSize = this.size();
        }
    }
    
    // CLIENT_SetGroupInfoForChannel�ӿ��������
    public static class NET_IN_SET_GROUPINFO_FOR_CHANNEL extends Structure
    {
        public int dwSize;
        public int nChannelID;//ͨ����
        public int nGroupIdNum;//��Ա����
        public byte[] szGroupId = new byte[MAX_GOURP_NUM*NET_COMMON_STRING_64];//��Ա��ID
        
        public NET_IN_SET_GROUPINFO_FOR_CHANNEL()
        {
            this.dwSize = this.size();
        }
    }
    
    // CLIENT_SetGroupInfoForChannel�ӿ��������
    public static class NET_OUT_SET_GROUPINFO_FOR_CHANNEL extends Structure
    {
        public int dwSize;
        
        public NET_OUT_SET_GROUPINFO_FOR_CHANNEL()
        {
            this.dwSize = this.size();
        }
    }
    
    // ������ѯ״̬��Ϣ�ص�����, lAttachHandle��CLIENT_AttachFaceFindState�ķ���ֵ
    public static class NET_CB_FACE_FIND_STATE extends Structure
    {
        public int dwSize;
        public int nToken;//��ƵŨ���������ݿ�����ID
        public int nProgress;//����ȡֵ��Χ��0-100,-1,��ʾ��ѯtoken������(������һ�������ڻ�����Ĳ�ѯʱ)
        public int nCurrentCount;//Ŀǰ���ϲ�ѯ��������������
        
        public NET_CB_FACE_FIND_STATE()
        {
            this.dwSize = this.size();
        }
    }
    
    //CLIENT_AttachFaceFindState�ӿ��������
    public static class NET_IN_FACE_FIND_STATE extends Structure
    {
        public int dwSize;//�ṹ���С,������д
        public int nTokenNum;//��ѯ������,Ϊ0ʱ,��ʾ�������еĲ�ѯ����
        public Pointer nTokens;//��ѯ����, ָ��int��ָ��
        public fFaceFindState cbFaceFindState;//�ص�����
        public NativeLong dwUser;//�û�����
        
        public NET_IN_FACE_FIND_STATE()
        {
            this.dwSize = this.size();
        }
    }
    
    //CLIENT_AttachFaceFindState�ӿ��������
    public static class NET_OUT_FACE_FIND_STATE extends Structure
    {
        public int dwSize;
        
        public NET_OUT_FACE_FIND_STATE()
        {
            this.dwSize = this.size();
        }
    }
    
    // SDKȫ����־��ӡ��Ϣ
    public static class LOG_SET_PRINT_INFO extends Structure
    {
        public int dwSize;
        public int bSetFilePath;//�Ƿ�������־·��, BOOL���ͣ�ȡֵ0��1
        public byte[] szLogFilePath = new byte[MAX_LOG_PATH_LEN];//��־·��(Ĭ��"./sdk_log/sdk_log.log")
        public int bSetFileSize;//�Ƿ�������־�ļ���С, BOOL���ͣ�ȡֵ0��1
        public int nFileSize;//ÿ����־�ļ��Ĵ�С(Ĭ�ϴ�С10240),��λ:����, ����Ϊunsigned int
        public int bSetFileNum;//�Ƿ�������־�ļ�����, BOOL���ͣ�ȡֵ0��1
        public int nFileNum;//�ƽ���־�ļ�����(Ĭ�ϴ�С10), ����Ϊunsigned int
        public int bSetPrintStrategy;//�Ƿ�������־��ӡ�������, BOOL���ͣ�ȡֵ0��1
        public int nPrintStrategy;//��־�������,0:������ļ�(Ĭ��); 1:���������, ����Ϊunsigned int
        
        public LOG_SET_PRINT_INFO()
        {
            this.dwSize = this.size();
        }
    }
    
    // media�ļ���ѯ����
    public static class EM_FILE_QUERY_TYPE extends Structure
    {
        public static final int NET_FILE_QUERY_TRAFFICCAR = 0; //��ͨ������Ϣ
        public static final int NET_FILE_QUERY_ATM = 1; //ATM��Ϣ
        public static final int NET_FILE_QUERY_ATMTXN = 2; //ATM������Ϣ
        public static final int NET_FILE_QUERY_FACE = 3; //������Ϣ MEDIAFILE_FACERECOGNITION_PARAM��MEDIAFILE_FACERECOGNITION_INFO
        public static final int NET_FILE_QUERY_FILE = 4; //�ļ���Ϣ��ӦNET_IN_MEDIA_QUERY_FILE��NET_OUT_MEDIA_QUERY_FILE
        public static final int NET_FILE_QUERY_TRAFFICCAR_EX = 5; //��ͨ������Ϣ,��չNET_FILE_QUERY_TRAFFICCAR,֧�ָ�����ֶ�
        public static final int NET_FILE_QUERY_FACE_DETECTION = 6; //��������¼���ϢMEDIAFILE_FACE_DETECTION_PARAM�� MEDIAFILE_FACE_DETECTION_INFO
    }
    
    // ��ѯ��ת����
    public static class NET_FINDING_JUMP_OPTION_INFO extends Structure
    {
        public int dwSize;
        public int nOffset;//��ѯ���ƫ����,������ڵ�ǰ��ѯ�ĵ�һ����ѯ�����λ��ƫ��
        
        public NET_FINDING_JUMP_OPTION_INFO()
        {
            this.dwSize = this.size();
        }
    }
    
    // ��̨��������
    public static class CFG_LINK_TYPE extends Structure
    {
        public static final int LINK_TYPE_NONE = 0; //������
        public static final int LINK_TYPE_PRESET = 1; //����Ԥ�õ�
        public static final int LINK_TYPE_TOUR = 2; //����Ѳ��
        public static final int LINK_TYPE_PATTERN = 3; //�����켣
    }

    // ������̨��Ϣ
    public static class CFG_PTZ_LINK extends Structure
    {
        public int emType;//��������, ȡֵΪCFG_LINK_TYPE�е�ֵ
        public int nValue;//����ȡֵ�ֱ��ӦԤ�õ�ţ�Ѳ���ŵȵ�
    }

    // ������̨��Ϣ��չ
    public static class CFG_PTZ_LINK_EX extends Structure
    {
        public int emType;//��������, ȡֵΪCFG_LINK_TYPE�е�ֵ
        public int nParam1;//��������1
        public int nParam2;//��������2
        public int nParam3;//��������3
        public int nChannelID;//��������̨ͨ��
    }

    // RGBA��Ϣ
    public static class CFG_RGBA extends Structure
    {
        public int nRed;
        public int nGreen;
        public int nBlue;
        public int nAlpha;
    }

    // �¼��������ݽṹ��
    public static class CFG_EVENT_TITLE extends Structure
    {
        public byte[] szText = new byte[MAX_CHANNELNAME_LEN];
        public CFG_POLYGON stuPoint;//�������Ͻ�����,����0-8191�������ϵ
        public CFG_SIZE stuSize;//����Ŀ�Ⱥ͸߶�,����0-8191�������ϵ��ĳ���������Ϊ0��ʾ������������Ӧ���
        public CFG_RGBA stuFrontColor;//ǰ����ɫ
        public CFG_RGBA stuBackColor;//������ɫ
    }

    // �ʼ���������
    public static class CFG_ATTACHMENT_TYPE extends Structure
    {
        public static final int ATTACHMENT_TYPE_PIC = 0; //ͼƬ����
        public static final int ATTACHMENT_TYPE_VIDEO = 1; //��Ƶ����
        public static final int ATTACHMENT_TYPE_NUM = 2; //������������
    }

    // �ָ�ģʽ
    public static class CFG_SPLITMODE extends Structure
    {
        public static final int SPLITMODE_1 =1;//1����
        public static final int SPLITMODE_2 =2;//2����
        public static final int SPLITMODE_4 =4;//4����
        public static final int SPLITMODE_6 =6;//6����
        public static final int SPLITMODE_8 =8;//8����
        public static final int SPLITMODE_9 =9;//9����
        public static final int SPLITMODE_12 =12;//12����
        public static final int SPLITMODE_16 =16;//16����
        public static final int SPLITMODE_20 =20;//20����
        public static final int SPLITMODE_25 =25;//25����
        public static final int SPLITMODE_36 =36;//36����
        public static final int SPLITMODE_64 =64;//64����
        public static final int SPLITMODE_144 =144;//144����
        public static final int SPLITMODE_PIP =1000;//���л��ָ�ģʽ����ֵ
        public static final int SPLITMODE_PIP1 =SPLITMODE_PIP+1;//���л�ģʽ, 1��ȫ������+1��С���洰��
        public static final int SPLITMODE_PIP3 =SPLITMODE_PIP+3;//���л�ģʽ, 1��ȫ������+3��С���洰��
        public static final int SPLITMODE_FREE =SPLITMODE_PIP*2;//���ɿ���ģʽ���������ɴ������رմ��ڣ��������ô���λ�ú�Z�����
        public static final int SPLITMODE_COMPOSITE_1 = SPLITMODE_PIP * 3 + 1;  // �ں�����Ա1�ָ�
        public static final int SPLITMODE_COMPOSITE_4 = SPLITMODE_PIP * 3 + 4;  // �ں�����Ա4�ָ�
        public static final int SPLITMODE_EOF = SPLITMODE_COMPOSITE_4+1; //������ʶ
    }

    // ��Ѳ��������
    public static class CFG_TOURLINK extends Structure
    {
        public int bEnable;//��Ѳʹ��, BOOL���ͣ�ȡֵ0��1
        public int emSplitMode;//��Ѳʱ�ķָ�ģʽ,ȡֵ��ΧΪCFG_SPLITMODE�е�ֵ
        public int[] nChannels = new int[MAX_VIDEO_CHANNEL_NUM];//��Ѳͨ�����б�
        public int nChannelCount;//��Ѳͨ������
    }

    // �Ž���������
    public static class EM_CFG_ACCESSCONTROLTYPE extends Structure
    {
        public static final int EM_CFG_ACCESSCONTROLTYPE_NULL = 0;//��������
        public static final int EM_CFG_ACCESSCONTROLTYPE_AUTO = 1; //�Զ�
        public static final int EM_CFG_ACCESSCONTROLTYPE_OPEN = 2; //����
        public static final int EM_CFG_ACCESSCONTROLTYPE_CLOSE = 3; //����
        public static final int EM_CFG_ACCESSCONTROLTYPE_OPENALWAYS = 4; //��Զ����
        public static final int EM_CFG_ACCESSCONTROLTYPE_CLOSEALWAYS = 5; //��Զ�ر�
    }

    // �ʼ���ϸ����
    public static class CFG_MAIL_DETAIL extends Structure
    {
        public int emAttachType;//��������, ȡֵ��ΧΪCFG_ATTACHMENT_TYPE�е�ֵ
        public int nMaxSize;//�ļ���С���ޣ���λkB
        public int nMaxTimeLength;//���¼��ʱ�䳤�ȣ���λ�룬��video��Ч
    }

    // �������з���
    public static class EM_CALLER_TYPE extends Structure
    {
        public static final int EM_CALLER_DEVICE = 0;//�豸����
    }

    // ����Э��
    public static class EM_CALLER_PROTOCOL_TYPE extends Structure
    {
        public static final int EM_CALLER_PROTOCOL_CELLULAR = 0;//�ֻ���ʽ
    }

    // ��������������Ϣ
    public static class CFG_TALKBACK_INFO extends Structure
    {
        public int bCallEnable;//��������ʹ��, BOOL���ͣ�ȡֵ0��1
        public int emCallerType;//�������з���, ȡֵ��ΧΪEM_CALLER_TYPE�е�ֵ
        public int emCallerProtocol;//��������Э��, ȡֵ��ΧΪEM_CALLER_PROTOCOL_TYPE�е�ֵ
    }

    // �绰��������������Ϣ
    public static class CFG_PSTN_ALARM_SERVER extends Structure
    {
        public int bNeedReport;//�Ƿ��ϱ����绰��������, BOOL���ͣ�ȡֵ0��1
        public int nServerCount;//�绰��������������
        public byte[] byDestination = new byte[MAX_PSTN_SERVER_NUM];//�ϱ��ı��������±�,�������CFG_PSTN_ALARM_CENTER_INFO
    }

    // ʱ�����Ϣ
    public static class CFG_TIME_SCHEDULE extends Structure
    {
        public int bEnableHoliday;//�Ƿ�֧�ֽڼ������ã�Ĭ��Ϊ��֧�֣����ǻ�ȡ���ú󷵻�ΪTRUE����Ҫʹ�ܼ�������, BOOL���ͣ�ȡֵ0��1
        public CFG_TIME_SECTION[] stuTimeSection = (CFG_TIME_SECTION[])new CFG_TIME_SECTION().toArray(MAX_TIME_SCHEDULE_NUM*MAX_REC_TSECT);//��һάǰ7��Ԫ�ض�Ӧÿ��7�죬��8��Ԫ�ض�Ӧ�ڼ��գ�ÿ�����6��ʱ���
    }

    // ����������Ϣ
    public static class CFG_ALARM_MSG_HANDLE extends Structure
    {
        //����
        public byte abRecordMask;//����bool, ȡֵ0����1
        public byte abRecordEnable;//����bool, ȡֵ0����1
        public byte abRecordLatch;//����bool, ȡֵ0����1
        public byte abAlarmOutMask;//����bool, ȡֵ0����1
        public byte abAlarmOutEn;//����bool, ȡֵ0����1
        public byte abAlarmOutLatch;//����bool, ȡֵ0����1
        public byte abExAlarmOutMask;//����bool, ȡֵ0����1
        public byte abExAlarmOutEn;//����bool, ȡֵ0����1
        public byte abPtzLinkEn;//����bool, ȡֵ0����1
        public byte abTourMask;//����bool, ȡֵ0����1
        public byte abTourEnable;//����bool, ȡֵ0����1
        public byte abSnapshot;//����bool, ȡֵ0����1
        public byte abSnapshotEn;//����bool, ȡֵ0����1
        public byte abSnapshotPeriod;//����bool, ȡֵ0����1
        public byte abSnapshotTimes;//����bool, ȡֵ0����1
        public byte abTipEnable;//����bool, ȡֵ0����1
        public byte abMailEnable;//����bool, ȡֵ0����1
        public byte abMessageEnable;//����bool, ȡֵ0����1
        public byte abBeepEnable;//����bool, ȡֵ0����1
        public byte abVoiceEnable;//����bool, ȡֵ0����1
        public byte abMatrixMask;//����bool, ȡֵ0����1
        public byte abMatrixEnable;//����bool, ȡֵ0����1
        public byte abEventLatch;//����bool, ȡֵ0����1
        public byte abLogEnable;//����bool, ȡֵ0����1
        public byte abDelay;//����bool, ȡֵ0����1
        public byte abVideoMessageEn;//����bool, ȡֵ0����1
        public byte abMMSEnable;//����bool, ȡֵ0����1
        public byte abMessageToNetEn;//����bool, ȡֵ0����1
        public byte abTourSplit;//����bool, ȡֵ0����1
        public byte abSnapshotTitleEn;//����bool, ȡֵ0����1
        public byte abChannelCount;//����bool, ȡֵ0����1
        public byte abAlarmOutCount;//����bool, ȡֵ0����1
        public byte abPtzLinkEx;//����bool, ȡֵ0����1
        public byte abSnapshotTitle;//����bool, ȡֵ0����1
        public byte abMailDetail;//����bool, ȡֵ0����1
        public byte abVideoTitleEn;//����bool, ȡֵ0����1
        public byte abVideoTitle;//����bool, ȡֵ0����1
        public byte abTour;//����bool, ȡֵ0����1
        public byte abDBKeys;//����bool, ȡֵ0����1
        public byte abJpegSummary;//����bool, ȡֵ0����1
        public byte abFlashEn;//����bool, ȡֵ0����1
        public byte abFlashLatch;//����bool, ȡֵ0����1
        //��Ϣ
        public int nChannelCount;//�豸����Ƶͨ����
        public int nAlarmOutCount;//�豸�ı����������
        public int[] dwRecordMask = new int[MAX_CHANNEL_COUNT];//¼��ͨ������(��λ)
        public int bRecordEnable;//¼��ʹ��, BOOL���ͣ�ȡֵ0��1
        public int nRecordLatch;//¼����ʱʱ��(��)
        public int[] dwAlarmOutMask = new int[MAX_CHANNEL_COUNT];//�������ͨ������
        public int bAlarmOutEn;//�������ʹ��, BOOL���ͣ�ȡֵ0��1
        public int nAlarmOutLatch;//���������ʱʱ��(��)
        public int[] dwExAlarmOutMask = new int[MAX_CHANNEL_COUNT];//��չ�������ͨ������
        public int bExAlarmOutEn;//��չ�������ʹ��, BOOL���ͣ�ȡֵ0��1
        public CFG_PTZ_LINK [] stuPtzLink = (CFG_PTZ_LINK [])new CFG_PTZ_LINK().toArray(MAX_VIDEO_CHANNEL_NUM);//��̨������
        public int bPtzLinkEn;//��̨����ʹ��, BOOL���ͣ�ȡֵ0��1
        public int[] dwTourMask = new int[MAX_CHANNEL_COUNT];//��ѯͨ������
        public int bTourEnable;//��ѯʹ��, BOOL���ͣ�ȡֵ0��1
        public int[] dwSnapshot = new int[MAX_CHANNEL_COUNT];//����ͨ��������
        public int bSnapshotEn;//����ʹ��, BOOL���ͣ�ȡֵ0��1
        public int nSnapshotPeriod;//��������(��)
        public int nSnapshotTimes;//���Ĵ���
        public int bTipEnable;//������Ϣ����ʾ, BOOL���ͣ�ȡֵ0��1
        public int bMailEnable;//�����ʼ��������ͼƬ����Ϊ����, BOOL���ͣ�ȡֵ0��1
        public int bMessageEnable;//�ϴ�������������, BOOL���ͣ�ȡֵ0��1
        public int bBeepEnable;//����, BOOL���ͣ�ȡֵ0��1
        public int bVoiceEnable;//������ʾ, BOOL���ͣ�ȡֵ0��1
        public int[] dwMatrixMask = new int[MAX_CHANNEL_COUNT];//������Ƶ����ͨ������
        public int bMatrixEnable;//������Ƶ����, BOOL���ͣ�ȡֵ0��1
        public int nEventLatch;//������ʼ��ʱʱ��(��)��0��15
        public int bLogEnable;//�Ƿ��¼��־, BOOL���ͣ�ȡֵ0��1
        public int nDelay;//����ʱ����ʱ����Ч����λΪ��
        public int bVideoMessageEn;//������ʾ��Ļ����Ƶ�����ӵ���Ļ�����¼����ͣ�ͨ���ţ����ʱ��BOOL���ͣ�ȡֵ0��1
        public int bMMSEnable;//���Ͳ���ʹ��, BOOL���ͣ�ȡֵ0��1
        public int bMessageToNetEn;//��Ϣ�ϴ�������ʹ��, BOOL���ͣ�ȡֵ0��1
        public int nTourSplit;//��Ѳʱ�ķָ�ģʽ0:1����;
        public int bSnapshotTitleEn;//�Ƿ����ͼƬ����, BOOL���ͣ�ȡֵ0��1
        public int nPtzLinkExNum;//��̨������
        public CFG_PTZ_LINK_EX[] stuPtzLinkEx = (CFG_PTZ_LINK_EX[])new CFG_PTZ_LINK_EX().toArray(MAX_VIDEO_CHANNEL_NUM);//��չ��̨��Ϣ
        public int nSnapTitleNum;//ͼƬ����������
        public CFG_EVENT_TITLE[] stuSnapshotTitle = (CFG_EVENT_TITLE[])new CFG_EVENT_TITLE().toArray(MAX_VIDEO_CHANNEL_NUM);//ͼƬ��������
        public CFG_MAIL_DETAIL stuMailDetail;//�ʼ���ϸ����
        public int bVideoTitleEn;//�Ƿ������Ƶ���⣬��Ҫָ������, BOOL���ͣ�ȡֵ0��1
        public int nVideoTitleNum;//��Ƶ����������Ŀ
        public CFG_EVENT_TITLE[] stuVideoTitle = (CFG_EVENT_TITLE[])new CFG_EVENT_TITLE().toArray(MAX_VIDEO_CHANNEL_NUM);//��Ƶ��������
        public int nTourNum;//��ѯ������Ŀ
        public CFG_TOURLINK[] stuTour = (CFG_TOURLINK[])new CFG_TOURLINK().toArray(MAX_VIDEO_CHANNEL_NUM);//��ѯ��������
        public int nDBKeysNum;//ָ�����ݿ�ؼ��ֵ���Ч��
        public byte[] szDBKeys = new byte[MAX_DBKEY_NUM*MAX_CHANNELNAME_LEN];//ָ���¼���ϸ��Ϣ����Ҫд�����ݿ�Ĺؼ���
        public byte[] byJpegSummary = new byte[MAX_SUMMARY_LEN];//���ӵ�JPEGͼƬ��ժҪ��Ϣ
        public int bFlashEnable;//�Ƿ�ʹ�ܲ����, BOOL���ͣ�ȡֵ0��1
        public int nFlashLatch;//�������ʱʱ��(��),��ʱʱ�䷶Χ��10,300]
        public byte abAudioFileName;//bool���ͣ�ȡֵ0��1
        public byte abAlarmBellEn;//bool���ͣ�ȡֵ0��1
        public byte abAccessControlEn;//bool���ͣ�ȡֵ0��1
        public byte abAccessControl;//bool���ͣ�ȡֵ0��1
        public byte[] szAudioFileName = new byte[MAX_PATH];//���������ļ�����·��
        public int bAlarmBellEn;//����ʹ��, BOOL���ͣ�ȡֵ0��1
        public int bAccessControlEn;//�Ž�ʹ��, BOOL���ͣ�ȡֵ0��1
        public int dwAccessControl;//�Ž�����
        public int[] emAccessControlType = new int[MAX_ACCESSCONTROL_NUM];//�Ž�����������Ϣ, Ԫ��ȡֵ��ΧΪEM_CFG_ACCESSCONTROLTYPE�е�ֵ
        public byte abTalkBack;//bool���ͣ�ȡֵ0��1
        public CFG_TALKBACK_INFO stuTalkback;//��������������Ϣ
        public byte abPSTNAlarmServer;//bool���ͣ�ȡֵ0��1
        public CFG_PSTN_ALARM_SERVER stuPSTNAlarmServer;//�绰��������������Ϣ
        public CFG_TIME_SCHEDULE stuTimeSection;//�¼���Ӧʱ���
        public byte abAlarmBellLatch;//bool���ͣ�ȡֵ0��1
        public int nAlarmBellLatch;//���������ʱʱ��(10-300��)
    }

    // ʱ�����Ϣ
    public static class CFG_TIME_SECTION extends Structure
    {
        public int dwRecordMask;//¼�����룬��λ�ֱ�Ϊ��̬���¼�񡢱���¼�񡢶�ʱ¼��Bit3~Bit15������Bit16��̬���ץͼ��Bit17����ץͼ��Bit18��ʱץͼ
        public int nBeginHour;
        public int nBeginMin;
        public int nBeginSec;
        public int nEndHour;
        public int nEndMin;
        public int nEndSec;
    }

    // �¼�����EVENT_IVS_FACERECOGNITION(����ʶ��)��Ӧ�Ĺ�������
    public static class CFG_FACERECOGNITION_INFO extends Structure
    {
    // ��Ϣ
        public byte[] szRuleName = new byte[MAX_NAME_LEN];//��������,��ͬ����������
        public byte bRuleEnable;//����ʹ��,bool���ͣ�ȡֵ0��1
        public byte[] bReserved = new byte[2];//�����ֶ�
        public int nObjectTypeNum;//��Ӧ�������͸���
        public byte[] szObjectTypes = new byte[MAX_OBJECT_LIST_SIZE*MAX_NAME_LEN];//��Ӧ���������б�
        public int nPtzPresetId;//��̨Ԥ�õ���0~65535
        public byte bySimilarity;//���ƶȣ�������ڸ���ʶ�Ȳű���(1~100)
        public byte byAccuracy;//ʶ�𾫶�(ȡֵ1~10������ֵ���󣬼�⾫����ߣ�����ٶ��½�����СֵΪ1��ʾ����ٶ����ȣ����ֵΪ10��ʾ��⾫������)
        public byte byMode;//�Ա�ģʽ,0-����,1-ָ�������������,
        public byte byImportantRank;//��ѯ��Ҫ�ȼ����ڵ��ڴ˵ȼ�����Ա(1~10,��ֵԽ��Խ��Ҫ)
        public int nAreaNum;//������
        public byte[] byAreas = new byte[8];//�����������,0-üë��1-�۾���2-���ӣ�3-��ͣ�4-����(�˲����ڶԱ�ģʽΪ1ʱ��Ч)
        public int nMaxCandidate;//��������ƥ��ͼƬ����
        public CFG_ALARM_MSG_HANDLE stuEventHandler;//��������
        public CFG_TIME_SECTION[] stuTimeSection = (CFG_TIME_SECTION[])new CFG_TIME_SECTION().toArray(WEEK_DAY_NUM*MAX_REC_TSECT_EX);//�¼���Ӧʱ���
    }
    
    // ����ҵ�񷽰�    
    public static class EM_CLASS_TYPE extends Structure
    {
        public static final int EM_CLASS_UNKNOWN =0;//δ֪ҵ��
        public static final int EM_CLASS_VIDEO_SYNOPSIS =1;//��ƵŨ��
        public static final int EM_CLASS_TRAFFIV_GATE =2;//����
        public static final int EM_CLASS_ELECTRONIC_POLICE =3;//�羯
        public static final int EM_CLASS_SINGLE_PTZ_PARKING =4;//����Υͣ
        public static final int EM_CLASS_PTZ_PARKINBG =5;//����Υͣ
        public static final int EM_CLASS_TRAFFIC =6;//��ͨ�¼�"Traffic"
        public static final int EM_CLASS_NORMAL =7;//ͨ����Ϊ����"Normal"
        public static final int EM_CLASS_PRISON =8;//������Ϊ����"Prison"
        public static final int EM_CLASS_ATM =9;//������Ϊ����"ATM"
        public static final int EM_CLASS_METRO =10;//������Ϊ����
        public static final int EM_CLASS_FACE_DETECTION =11;//�������"FaceDetection"
        public static final int EM_CLASS_FACE_RECOGNITION =12;//����ʶ��"FaceRecognition"
        public static final int EM_CLASS_NUMBER_STAT =13;//����ͳ��"NumberStat"
        public static final int EM_CLASS_HEAT_MAP =14;//�ȶ�ͼ"HeatMap"
        public static final int EM_CLASS_VIDEO_DIAGNOSIS =15;//��Ƶ���"VideoDiagnosis"
        public static final int EM_CLASS_VIDEO_ENHANCE =16;//��Ƶ��ǿ
        public static final int EM_CLASS_SMOKEFIRE_DETECT =17;//�̻���
        public static final int EM_CLASS_VEHICLE_ANALYSE =18;//��������ʶ��"VehicleAnalyse"
        public static final int EM_CLASS_PERSON_FEATURE =19;//��Ա����ʶ��
    }
    
    // ���ܱ����¼�������Ϣ
    public static class EVENT_INTELLI_COMM_INFO extends Structure
    {
        public int emClassType;//�����¼��������࣬ ȡֵΪ  EM_CLASS_TYPE �е�ֵ
        public int					nPresetID;									// ���¼�������Ԥ�õ㣬��Ӧ�����ù����Ԥ�õ�
    	public byte[]               bReserved = new byte[124];                  // �����ֽ�,������չ.
    }
    
    // �¼�����EVENT_IVS_FACERECOGNITION(����ʶ��)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_FACERECOGNITION_INFO extends Structure
    {
        public int nChannelID;//ͨ����
        public byte[] szName = new byte[128];//�¼�����
        public int nEventID;//�¼�ID
        public NET_TIME_EX UTC;//�¼�������ʱ��
        public NET_MSG_OBJECT stuObject;//��⵽������
        public int nCandidateNum;//��ǰ����ƥ�䵽�ĺ�ѡ��������
        public CANDIDATE_INFO[] stuCandidates = (CANDIDATE_INFO[])new CANDIDATE_INFO().toArray(NET_MAX_CANDIDATE_NUM);//��ǰ����ƥ�䵽�ĺ�ѡ������Ϣ
        public byte bEventAction;//�¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����; 
        public byte byImageIndex;//ͼƬ�����,ͬһʱ����(��ȷ����)�����ж���ͼƬ,��0��ʼ
        public byte[] byReserved1 = new byte[2];//����
        public int bGlobalScenePic;//ȫ��ͼ�Ƿ����, ����ΪBOOL, ȡֵΪ0����1
        public NET_PIC_INFO stuGlobalScenePicInfo;//ȫ��ͼƬ��Ϣ
        public byte[] szSnapDevAddress = new byte[MAX_PATH];//ץ�ĵ�ǰ�������豸��ַ,�磺����·37��
        public int nOccurrenceCount;//�¼������ۼƴ����� ����Ϊunsigned int
        public EVENT_INTELLI_COMM_INFO stuIntelliCommInfo;//�����¼�������Ϣ
        public byte[] bReserved = new byte[592];//�����ֽ�,������չ. 
    }
    
    //��������Ӧ�Ա�����
    public static class EM_DEV_EVENT_FACEDETECT_SEX_TYPE extends Structure
    {
        public static final int EM_DEV_EVENT_FACEDETECT_SEX_TYPE_UNKNOWN = 0; //δ֪
        public static final int EM_DEV_EVENT_FACEDETECT_SEX_TYPE_MAN = 1; //����
        public static final int EM_DEV_EVENT_FACEDETECT_SEX_TYPE_WOMAN = 2; //Ů��
    }

    //��������Ӧ������������
    public static class EM_DEV_EVENT_FACEDETECT_FEATURE_TYPE extends Structure
    {
        public static final int EM_DEV_EVENT_FACEDETECT_FEATURE_TYPE_UNKNOWN = 0; //δ֪
        public static final int EM_DEV_EVENT_FACEDETECT_FEATURE_TYPE_WEAR_GLASSES = 1; //���۾�
        public static final int EM_DEV_EVENT_FACEDETECT_FEATURE_TYPE_SMILE = 2; //΢Ц
        public static final int EM_DEV_EVENT_FACEDETECT_FEATURE_TYPE_ANGER = 3; //��ŭ
        public static final int EM_DEV_EVENT_FACEDETECT_FEATURE_TYPE_SADNESS = 4; //����
        public static final int EM_DEV_EVENT_FACEDETECT_FEATURE_TYPE_DISGUST = 5; //���
        public static final int EM_DEV_EVENT_FACEDETECT_FEATURE_TYPE_FEAR = 6; //����
        public static final int EM_DEV_EVENT_FACEDETECT_FEATURE_TYPE_SURPRISE = 7; //����
        public static final int EM_DEV_EVENT_FACEDETECT_FEATURE_TYPE_NEUTRAL = 8; //����
        public static final int EM_DEV_EVENT_FACEDETECT_FEATURE_TYPE_LAUGH = 9; //��Ц
    }

    // �¼��ļ����ļ���ǩ����
    public static class EM_EVENT_FILETAG extends Structure
    {
        public static final int NET_ATMBEFOREPASTE = 1; //ATM����ǰ
        public static final int NET_ATMAFTERPASTE = 2;  //ATM������
    }

    // �¼���Ӧ�ļ���Ϣ
    public static class NET_EVENT_FILE_INFO extends Structure
    {
        public byte bCount;//��ǰ�ļ������ļ����е��ļ�����
        public byte bIndex;//��ǰ�ļ����ļ����е��ļ����(���1��ʼ)
        public byte bFileTag;//�ļ���ǩ,����˵����ö������ EM_EVENT_FILETAG
        public byte bFileType;//�ļ�����,0-��ͨ1-�ϳ�2-��ͼ
        public NET_TIME_EX stuFileTime;//�ļ�ʱ��
        public int nGroupId;//ͬһ��ץ���ļ���Ψһ��ʶ
    }

    // �����������Ϣ
    public static class NET_FACE_INFO extends Structure
    {
        public int nObjectID;//����ID,ÿ��ID��ʾһ��Ψһ������
        public byte[] szObjectType = new byte[128];//��������
        public int nRelativeID;//Relative����һ��ͼƬID��ͬ,��ʾ����������ͼ�ǴӴ�ͼ��ȡ����
        public DH_RECT BoundingBox;//��Χ��
        public NET_POINT Center;//��������
    }

    //�¼�����EVENT_IVS_FACEDETECT(��������¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_FACEDETECT_INFO extends Structure
    {
        public int nChannelID;//ͨ����
        public byte[] szName = new byte[128];//�¼�����
        public byte[] bReserved1 = new byte[4];//�ֽڶ���
        public double PTS;//ʱ���(��λ�Ǻ���)
        public NET_TIME_EX UTC;//�¼�������ʱ��
        public int nEventID;//�¼�ID
        public NET_MSG_OBJECT stuObject;//��⵽������
        public NET_EVENT_FILE_INFO stuFileInfo;//�¼���Ӧ�ļ���Ϣ
        public byte bEventAction;//�¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[] reserved = new byte[2];//�����ֽ�
        public byte byImageIndex;//ͼƬ�����,ͬһʱ����(��ȷ����)�����ж���ͼƬ,��0��ʼ
        public int nDetectRegionNum;//���������򶥵���
        public NET_POINT[] DetectRegion = (NET_POINT[])new NET_POINT().toArray(NET_MAX_DETECT_REGION_NUM);//����������
        public int dwSnapFlagMask;//ץͼ��־(��λ),�����NET_RESERVED_COMMON
        public byte[] szSnapDevAddress = new byte[MAX_PATH];//ץ�ĵ�ǰ�������豸��ַ,�磺����·37��
        public int nOccurrenceCount;//�¼������ۼƴ���, ����Ϊunsigned int
        public int emSex;//�Ա�, ȡֵΪEM_DEV_EVENT_FACEDETECT_SEX_TYPE�е�ֵ
        public int nAge;//����,-1��ʾ���ֶ�������Ч
        public int nFeatureValidNum;//��������������Ч����,��emFeature���ʹ��, ����Ϊunsigned int
        public int[] emFeature = new int[NET_MAX_FACEDETECT_FEATURE_NUM];//������������,��nFeatureValidNum, ȡֵΪEM_DEV_EVENT_FACEDETECT_FEATURE_TYPE�е�ֵ
        public int nFacesNum;//ָʾstuFaces��Ч����
        public NET_FACE_INFO[] stuFaces = (NET_FACE_INFO[])new NET_FACE_INFO().toArray(10);//��������ʱʹ��,��ʱû��Object
        public EVENT_INTELLI_COMM_INFO stuIntelliCommInfo;//�����¼�������Ϣ
        public byte[] bReserved = new byte[892];//�����ֽ�,������չ
    }
    
    // �¼�����EVENT_IVS_TRAFFICJAM(��ͨӵ���¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFICJAM_INFO extends Structure {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[128];                     // �¼�����
        public byte[]              bReserved1 = new byte[4];                   // �ֽڶ���
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public int                 nLane;                                      // ��Ӧ������
        public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ               
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte                bJamLenght;                                 // ��ʾӵ�³���(�ܳ������Ȱٷֱȣ�0-100
        public byte                reserved;                                   // �����ֽ�
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public NET_TIME_EX         stuStartJamTime;                            // ��ʼͣ��ʱ��
        public int                 nSequence;                                  // ��ʾץ�����,��3,2,1,1��ʾץ�Ľ���,0��ʾ�쳣����(bEventAction=2ʱ�˲�����Ч)
        public int                 nAlarmIntervalTime;                         // ����ʱ����,��λ:�롣(���¼�Ϊ�������¼�,���յ���һ�����¼�֮��,���ڳ������ʱ���δ�յ����¼��ĺ����¼�,����Ϊ���¼��쳣������)
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public NET_RESOLUTION_INFO stuResolution;                              // ��ӦͼƬ�ķֱ���
        public int                 nJamRealLength;                             // ��ʵ�ʵ�ӵ�³���,��λ��
        public byte[]              bReserved = new byte[1008];                 // �����ֽ�,������չ.
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ͨ������Ϣ  
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    }
    
    // ������ʻ����
    public static class NET_FLOWSTAT_DIRECTION extends Structure
    {
        public static final int DRIVING_DIR_UNKNOW   = 0 ;     //����֮ǰ
        public static final int DRIVING_DIR_APPROACH = 1 ;     //����,���������豸�����Խ��Խ��
        public static final int DRIVING_DIR_LEAVE    = 2 ;     //����,���������豸�����Խ��ԽԶ
    }
    
    //��������ͳ�Ƴ�����ʻ������Ϣ 
    public static class NET_TRAFFIC_FLOWSTAT_INFO_DIR extends Structure
    {
        public int                         emDrivingDir;      //��ʻ���� (�μ�NET_FLOWSTAT_DIRECTION)
        public byte[]                      szUpGoing = new byte[FLOWSTAT_ADDR_NAME];      //���еص� 
        public byte[]                      szDownGoing = new byte[FLOWSTAT_ADDR_NAME];    //���еص� 
        public byte[]                      reserved= new byte[32];                       //�����ֽ�
        
    }
    
    public static class NET_TRAFFIC_JAM_STATUS extends Structure
    {
         public static final int JAM_STATUS_UNKNOW = 0; //δ֪
         public static final int JAM_STATUS_CLEAR  = 1; //ͨ��
         public static final int JAM_STATUS_JAMMED = 2; //ӵ��
    }


    public static class  NET_TRAFFIC_FLOW_STATE  extends Structure
    {
        public int                             nLane;                          // ������
        public int                             dwState;                        // ״ֵ̬
                                                                               // 1- ��������
                                                                               // 2- ��������ָ�
                                                                               // 3- ����
                                                                               // 4- ������С
                                                                               // 5- ������С�ָ�
        public int                             dwFlow;                         // ����ֵ, ��λ: ��
        public int                             dwPeriod;                       // ����ֵ��Ӧ��ͳ��ʱ��
        public NET_TRAFFIC_FLOWSTAT_INFO_DIR   stTrafficFlowDir = new NET_TRAFFIC_FLOWSTAT_INFO_DIR();               // ����������Ϣ
        public int                             nVehicles;                      // ͨ����������
        public float                           fAverageSpeed;                  // ƽ������,��λkm/h
        public float                           fAverageLength;                 // ƽ������,��λ��
        public float                           fTimeOccupyRatio;               // ʱ��ռ����,����λʱ����ͨ������ĳ�������ʱ����ܺ�ռ��λʱ��ı���
        public float                           fSpaceOccupyRatio;              // �ռ�ռ����,�����ٷ��ʼ����ĳ��������ܺͳ���ʱ�����ڳ���ƽ����ʻ����
        public float                           fSpaceHeadway;                  // ��ͷ���,���ڳ���֮��ľ���,��λ��/��
        public float                           fTimeHeadway;                   // ��ͷʱ��,��λ��/��
        public float                           fDensity;                       // �����ܶ�,ÿ����ĳ�����,��λ��/km
        public int                             nOverSpeedVehicles;             // ���ٳ�����
        public int                             nUnderSpeedVehicles;            // ���ٳ�����
        public int                             nLargeVehicles;                 // �󳵽�ͨ��(9��<����<12��),��/��λʱ��
        public int                             nMediumVehicles;                // ���ͳ���ͨ��(6��<����<9��),��/��λʱ��
        public int                             nSmallVehicles;                 // С����ͨ��(4��<����<6��),��/��λʱ��,
        public int                             nMotoVehicles;                  // Ħ�н�ͨ��(΢�ͳ�,����<4��),��/��λʱ��,
        public int                             nLongVehicles;                  // ������ͨ��(����>=12��),��/��λʱ��,
        public int                             nVolume;                        // ��ͨ��, ��/��λʱ��, ĳʱ����ͨ����������·������ͨ����һ��ĳ�����,����1Сʱ��, 
        public int                             nFlowRate;                      // ����С������,��/Сʱ, ����ͨ����������·ĳһ�����ĳһ·�εĵ���Сʱ����
        public int                             nBackOfQueue;                   // �Ŷӳ���,��λ����, ���źŽ����ͣ���ߵ������Ŷӳ���ĩ��֮��ľ���
        public int                             nTravelTime;                    // ����ʱ��,��λ����, ����ͨ��ĳһ����·����ʱ�䡣��������ͣ������
        public int                             nDelay;                         // ����,��λ����,��ʻԱ���˿ͻ����˻��ѵĶ�����г�ʱ��
        public byte[]                          byDirection = new byte[MAX_DRIVING_DIR_NUM]; // ��������,���NET_ROAD_DIRECTION
        public byte                            byDirectionNum;                 // ������ʻ�������
        public byte[]                          reserved1 = new byte[3];        // �ֽڶ���
        public int          emJamState;                                        // ��·ӵ��״�� (�μ�NET_TRAFFIC_JAM_STATUS)
        //  ����������ͳ�ƽ�ͨ��
        public int                             nPassengerCarVehicles;                      // �ͳ���ͨ��(��/��λʱ��)
        public int                             nLargeTruckVehicles;                        // �������ͨ��(��/��λʱ��)
        public int                             nMidTruckVehicles;                          // �л�����ͨ��(��/��λʱ��)
        public int                             nSaloonCarVehicles;                         // �γ���ͨ��(��/��λʱ��)    
        public int                             nMicrobusVehicles;                          // �������ͨ��(��/��λʱ��)
        public int                             nMicroTruckVehicles;                        // С������ͨ��(��/��λʱ��)
        public int                             nTricycleVehicles;                          // ���ֳ���ͨ��(��/��λʱ��)
        public int                             nMotorcycleVehicles;                        // Ħ�г���ͨ��(��/��λʱ��)
        public int                             nPasserbyVehicles;                          // ���˽�ͨ��(��/��λʱ��)
        public byte[]                          reserved = new byte[816];                   // �����ֽ�
    }

    //�¼����� EVENT_IVS_TRAFFIC_FLOWSTATE(��ͨ�����¼�)��Ӧ���ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_FLOW_STATE extends Structure
    {
        public int                       nChannelID;                             // ͨ����
        public byte[]                    szName = new byte[NET_EVENT_NAME_LEN];   // �¼�����
        public byte[]                    bReserved1 = new byte[8];                          // �ֽڶ���
        public int                       PTS;                                    // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX               UTC;                                    // �¼�������ʱ��
        public int                       nEventID;                               // �¼�ID
        public int                       nSequence;                              // ���
        public int                       nStateNum;                              // ����״̬����
        public NET_TRAFFIC_FLOW_STATE[]  stuStates = (NET_TRAFFIC_FLOW_STATE[])new NET_TRAFFIC_FLOW_STATE().toArray(NET_MAX_LANE_NUM);             // ����״̬, ÿ��������Ӧ������һ��Ԫ��
        public byte[]                    bReserved = new byte[1024];               // �����ֽ�
    }
    
    // ͼƬ�ֱ���
    public static class NET_RESOLUTION_INFO extends Structure
    {
        public short snWidth;//��
        public short snHight;//��
    }

    public static class EM_COMMON_SEAT_TYPE extends Structure
    {
        public static final int COMMON_SEAT_TYPE_UNKNOWN = 0;//δʶ��
        public static final int COMMON_SEAT_TYPE_MAIN = 1;//����ʻ
        public static final int COMMON_SEAT_TYPE_SLAVE = 2;//����ʻ
    }

    // Υ��״̬
    public static class EVENT_COMM_STATUS extends Structure
    {
        public byte bySmoking;//�Ƿ����
        public byte byCalling;//�Ƿ��绰
        public byte[] szReserved = new byte[14];//Ԥ���ֶ�
    }

    public static class NET_SAFEBELT_STATE extends Structure
    {
        public static final int SS_NUKNOW = 0;//δ֪
        public static final int SS_WITH_SAFE_BELT = 1;//��ϵ��ȫ��
        public static final int SS_WITHOUT_SAFE_BELT = 2;//δϵ��ȫ��
    }

    //������״̬
    public static class NET_SUNSHADE_STATE extends Structure
    {
        public static final int SS_NUKNOW_SUN_SHADE = 0;//δ֪
        public static final int SS_WITH_SUN_SHADE = 1;//��������
        public static final int SS_WITHOUT_SUN_SHADE = 2;//��������
    }

    // ��ʻλΥ����Ϣ
    public static class EVENT_COMM_SEAT extends Structure
    {
        public int bEnable;//�Ƿ��⵽������Ϣ, ����BOOL, ȡֵ0����1
        public int emSeatType;//��������,0:δʶ��;1:����ʻ; ȡֵΪEM_COMMON_SEAT_TYPE�е�ֵ
        public EVENT_COMM_STATUS stStatus;//Υ��״̬
        public int emSafeBeltStatus;//��ȫ��״̬, ȡֵΪNET_SAFEBELT_STATE�е�ֵ
        public int emSunShadeStatus;//������״̬, ȡֵΪNET_SUNSHADE_STATE�е�ֵ
        public byte[] szReserved = new byte[24];//Ԥ���ֽ�
    }

    public static class EM_COMM_ATTACHMENT_TYPE extends Structure
    {       
        public static final int COMM_ATTACHMENT_TYPE_UNKNOWN = 0;// δ֪����
        public static final int COMM_ATTACHMENT_TYPE_FURNITURE = 1;// �ڼ�   
        public static final int COMM_ATTACHMENT_TYPE_PENDANT = 2;// �Ҽ�    
        public static final int COMM_ATTACHMENT_TYPE_TISSUEBOX = 3;// ֽ���
        public static final int COMM_ATTACHMENT_TYPE_DANGER = 4;// Σ��Ʒ
    }

    // �������
    public static class EVENT_COMM_ATTACHMENT extends Structure
    {       
        public int emAttachmentType;//�������, ȡֵΪEM_COMM_ATTACHMENT_TYPE�е�ֵ
        public NET_RECT stuRect;//����
        public byte[] bReserved = new byte[20];//Ԥ���ֽ�
    }
    
    //NTPУʱ״̬
    public static class EM_NTP_STATUS extends Structure
    {
        public static final int NET_NTP_STATUS_UNKNOWN = 0;
        public static final int NET_NTP_STATUS_DISABLE = 1;
        public static final int NET_NTP_STATUS_SUCCESSFUL = 2;
        public static final int NET_NTP_STATUS_FAILED = 3;
    }
    
    // ��ͨץͼͼƬ��Ϣ
    public static class EVENT_PIC_INFO extends Structure
    {
    	public int                       nOffset;                // ԭʼͼƬƫ�ƣ���λ�ֽ�
    	public int                       nLength;                // ԭʼͼƬ���ȣ���λ�ֽ�
    }

    public static class EVENT_COMM_INFO extends Structure
    {
        public int emNTPStatus;//NTPУʱ״̬, ȡֵΪEM_NTP_STATUS�е�ֵ
        public int nDriversNum;//��ʻԱ��Ϣ��
        public Pointer pstDriversInfo;//��ʻԱ��Ϣ���ݣ�����ΪNET_MSG_OBJECT_EX*
        public Pointer pszFilePath;//����Ӳ�̻���sd���ɹ�д��·��,ΪNULLʱ,·�������ڣ� ����Ϊchar *
        public Pointer pszFTPPath;//�豸�ɹ�д��ftp��������·���� ����Ϊchar *
        public Pointer pszVideoPath;//��ǰ������Ҫ��ȡ��ǰΥ�µĹ�����Ƶ��FTP�ϴ�·���� ����Ϊchar *
        public EVENT_COMM_SEAT[] stCommSeat = (EVENT_COMM_SEAT[])new EVENT_COMM_SEAT().toArray(COMMON_SEAT_MAX_NUMBER);//��ʻλ��Ϣ
        public int nAttachmentNum;//�����������
        public EVENT_COMM_ATTACHMENT[] stuAttachment = (EVENT_COMM_ATTACHMENT[])new EVENT_COMM_ATTACHMENT().toArray(NET_MAX_ATTACHMENT_NUM);//���������Ϣ
        public int nAnnualInspectionNum;//����־����
        public NET_RECT[] stuAnnualInspection = (NET_RECT[])new NET_RECT().toArray(NET_MAX_ANNUUALINSPECTION_NUM);//����־
        public float fHCRatio; // HC��ռ��������λ��% 
        public float fNORatio; // NO��ռ��������λ��% 
        public float fCOPercent; // CO��ռ�ٷֱȣ���λ��% ȡֵ0~100
        public float fCO2Percent; // CO2��ռ�ٷֱȣ���λ��% ȡֵ0~100     
        public float fLightObscuration; // ��͸��ȣ���λ��% ȡֵ0~100
        public int nPictureNum;// ԭʼͼƬ����
        public EVENT_PIC_INFO[] stuPicInfos = (EVENT_PIC_INFO[])new EVENT_PIC_INFO().toArray(NET_MAX_EVENT_PIC_NUM);// ԭʼͼƬ��Ϣ
        public byte[] bReserved = new byte[1028];//Ԥ���ֽ�
        public byte[] szCountry = new byte[20];//����
    }
    
    // ������������Ϣ
    public static class NET_SIG_CARWAY_INFO_EX extends Structure
    {
        public byte[] byRedundance = new byte[8];//�ɳ���������ץ���ź�������Ϣ
        public byte[] bReserved = new byte[120];//�����ֶ�
    }
    
    // ��ɫRGBA
    public static class NET_COLOR_RGBA extends Structure
    {
        public int nRed;//��
        public int nGreen;//��
        public int nBlue;//��
        public int nAlpha;//͸��
    }

    // TrafficCar ��ͨ������Ϣ
    public static class DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO extends Structure
    {
        public byte[] szPlateNumber = new byte[32];//���ƺ���
        public byte[] szPlateType = new byte[32];//�������Ͳμ�VideoAnalyseRule�г������Ͷ���
        public byte[] szPlateColor = new byte[32];//������ɫ"Blue","Yellow",
        public byte[] szVehicleColor = new byte[32];//������ɫ"White",
        public int nSpeed;//�ٶȵ�λKm/H
        public byte[] szEvent = new byte[64];//����������¼��μ��¼��б�Event
        public byte[] szViolationCode = new byte[32];//Υ�´������TrafficGlobal.ViolationCode
        public byte[] szViolationDesc = new byte[64];//Υ������
        public int nLowerSpeedLimit;//�ٶ�����
        public int nUpperSpeedLimit;//�ٶ�����
        public int nOverSpeedMargin;//�޸��ٿ���ֵ��λ��km/h
        public int nUnderSpeedMargin;//�޵��ٿ���ֵ��λ��km/h
        public int nLane;//�����μ��¼��б�EventList�п��ں�·���¼���
        public int nVehicleSize;//������С,-1��ʾδ֪,����λ
        // ��0λ:"Light-duty", С�ͳ�
        // ��1λ:"Medium", ���ͳ�
        // ��2λ:"Oversize", ���ͳ�
        // ��3λ:"Minisize", ΢�ͳ�
        // ��4λ:"Largesize", ����
        public float fVehicleLength;//�������ȵ�λ��
        public int nSnapshotMode;//ץ�ķ�ʽ0-δ����,1-ȫ��,2-����,4-ͬ��ץ��,8-����ץ��,16-����ͼ��
        public byte[] szChannelName = new byte[32];//���ػ�Զ�̵�ͨ������,�����ǵص���Ϣ��Դ��ͨ����������ChannelTitle.Name
        public byte[] szMachineName = new byte[256];//���ػ�Զ���豸������Դ����ͨ����General.MachineName
        public byte[] szMachineGroup = new byte[256];//�����������豸������λĬ��Ϊ��,�û����Խ���ͬ���豸��Ϊһ��,���ڹ���,���ظ���
        public byte[] szRoadwayNo = new byte[64];//��·���
        public byte[] szDrivingDirection = new byte[3*NET_MAX_DRIVINGDIRECTION];//
                                                                                // ��ʻ���� , "DrivingDirection" : ["Approach", "�Ϻ�", "����"],
                                                                                // "Approach"-����,���������豸�����Խ��Խ����"Leave"-����,
                                                                                // ���������豸�����Խ��ԽԶ,�ڶ��͵����������ֱ�������к�
                                                                                // ���е������ص�
        public Pointer szDeviceAddress;//�豸��ַ,OSD���ӵ�ͼƬ�ϵ�,��Դ������TrafficSnapshot.DeviceAddress,'\0'����
        public byte[] szVehicleSign = new byte[32];//������ʶ,����
        public NET_SIG_CARWAY_INFO_EX stuSigInfo;//�ɳ���������ץ���ź�������Ϣ
        public Pointer szMachineAddr;//�豸����ص�
        public float fActualShutter;//��ǰͼƬ�ع�ʱ��,��λΪ����
        public byte byActualGain;//��ǰͼƬ����,��ΧΪ0~100
        public byte byDirection;//��������,0-����1-�����򶫱�2-����
        public byte[] byReserved = new byte[2];
        public Pointer szDetailedAddress;//��ϸ��ַ,��ΪszDeviceAddress�Ĳ���
        public byte[] szDefendCode = new byte[NET_COMMON_STRING_64];//ͼƬ��α��
        public int nTrafficBlackListID;//�������������ݿ��¼Ĭ������ID,0,��Ч��>0,���������ݼ�¼
        public NET_COLOR_RGBA stuRGBA;//������ɫRGBA
        public NET_TIME stSnapTime;//ץ��ʱ��
        public int nRecNo;//��¼���
        public byte[] szCustomParkNo= new byte[NET_COMMON_STRING_32+1];// �Զ��峵λ�ţ�ͣ�����ã�
        public byte[] byReserved1 = new byte[3];
        public int nDeckNo;//����λ��
        public int nFreeDeckCount;//���г�������
        public int nFullDeckCount;//ռ�ó�������
        public int nTotalDeckCount;//�ܹ���������
        public byte[] szViolationName = new byte[64];//Υ������
        public int nWeight;//����(��λKg), ����Ϊunsigned int
       
        public byte[]      szCustomRoadwayDirection = new byte[32];		// �Զ��峵������,byDirectionΪ9ʱ��Ч
        public byte        byPhysicalLane; // ��������,ȡֵ0��5
        public int 		   emMovingDirection; // ������ʻ���� EM_TRAFFICCAR_MOVE_DIRECTION
        public byte[]      bReserved = new byte[579]; // �����ֽ�,������չ.
    }

    // �¼�����EVENT_IVS_TRAFFIC_PARKING(��ͨΥ��ͣ���¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_PARKING_INFO extends Structure
    {
        public int nChannelID;//ͨ����
        public byte[] szName = new byte[128];//�¼�����
        public byte[] bReserved1 = new byte[4];//�ֽڶ���
        public double PTS;//ʱ���(��λ�Ǻ���)
        public NET_TIME_EX UTC;//�¼�������ʱ��
        public int nEventID;//�¼�ID
        public NET_MSG_OBJECT stuObject;//��⵽������
        public NET_MSG_OBJECT stuVehicle;//������Ϣ
        public int nLane;//��Ӧ������
        public NET_EVENT_FILE_INFO stuFileInfo;//�¼���Ӧ�ļ���Ϣ
        public byte bEventAction;//�¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[] reserved = new byte[2];//�����ֽ�
        public byte byImageIndex;//ͼƬ�����,ͬһʱ����(��ȷ����)�����ж���ͼƬ,��0��ʼ
        public NET_TIME_EX stuStartParkingTime;//��ʼͣ��ʱ��
        public int nSequence;//��ʾץ�����,��3,2,1,1��ʾץ�Ľ���,0��ʾ�쳣����(bEventAction=2ʱ�˲�����Ч)
        public int nAlarmIntervalTime;//����ʱ����,��λ:�롣(���¼�Ϊ�������¼�,���յ���һ�����¼�֮��,���ڳ������ʱ���δ�յ����¼��ĺ����¼�,����Ϊ���¼��쳣������)
        public int nParkingAllowedTime;//����ͣ��ʱ��,��λ���롣
        public int nDetectRegionNum;//���������򶥵���
        public NET_POINT[] DetectRegion = (NET_POINT[])new NET_POINT().toArray(NET_MAX_DETECT_REGION_NUM);//����������
        public int dwSnapFlagMask;//ץͼ��־(��λ),�����NET_RESERVED_COMMON
        public NET_RESOLUTION_INFO stuResolution;//��ӦͼƬ�ķֱ���
        public int bIsExistAlarmRecord;//true:�ж�Ӧ�ı���¼��;false:�޶�Ӧ�ı���¼��, ����ΪBOOL, ȡֵΪ0��1
        public int dwAlarmRecordSize;//¼���С
        public byte[] szAlarmRecordPath = new byte[NET_COMMON_STRING_256];//¼��·��
        public byte[] szFTPPath = new byte[NET_COMMON_STRING_256];//FTP·��
        public EVENT_INTELLI_COMM_INFO stuIntelliCommInfo;//�����¼�������Ϣ
        public byte[] bReserved = new byte[272];//�����ֽ�,������չ.
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;//��ͨ������Ϣ
        public EVENT_COMM_INFO stCommInfo;//������Ϣ
    }
    
    //ͣ������Ϣ
    public static class DEV_TRAFFIC_PARKING_INFO extends Structure
    {
        public int           nFeaturePicAreaPointNum;                                                        // ����ͼƬ�������
        public NET_POINT[]   stFeaturePicArea = (NET_POINT[])new NET_POINT().toArray(NET_MAX_POLYGON_NUM);   // ����ͼƬ����Ϣ
        public byte[]        bReserved = new byte[572];                                                      // �����ֽ�
    }
    
    //�¼����� EVENT_IVS_TRAFFIC_PARKINGSPACEPARKING(��λ�г��¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_PARKINGSPACEPARKING_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[NET_EVENT_NAME_LEN];      // �¼�����
        public byte[]              bReserved1 = new byte[8];                   // �ֽڶ���
        public int                 PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public int                 nLane;                                      // ��Ӧ������
        public NET_MSG_OBJECT      stuObject;                                  // ��⵽������
        public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
        public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ
        
        public int                 nSequence;                                  // ��ʾץ�����,��3,2,1,1��ʾץ�Ľ���,0��ʾ�쳣����    
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved = new byte[2];
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public NET_RESOLUTION_INFO  stuResolution;                             // ��ӦͼƬ�ķֱ���
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ͨ������Ϣ
        public int                 nParkingSpaceStatus;                        // ��λ״̬,0-ռ��,1-����,2-ѹ��
        public DEV_TRAFFIC_PARKING_INFO stTrafficParingInfo;                   // ͣ������Ϣ
        public byte[]              bReserved = new byte[380];                  // �����ֽ� 
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ

    }

    // �¼����� EVENT_IVS_TRAFFIC_PARKINGSPACENOPARKING(��λ�޳��¼�)��Ӧ�����ݿ�������Ϣ
    // ������ʷԭ��,���Ҫ�������¼�,DEV_EVENT_TRAFFICJUNCTION_INFO��EVENT_IVS_TRAFFICGATEҪһ����,�Է�ֹ����Ƶ�羯����Ȧ�羯ͬʱ����ƽ̨���������
    // ����EVENT_IVS_TRAFFIC_TOLLGATEֻ֧���¿����¼�������
    public static class DEV_EVENT_TRAFFIC_PARKINGSPACENOPARKING_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[NET_EVENT_NAME_LEN];      // �¼�����
        public byte[]              bReserved1 = new byte[8];                   // �ֽڶ���
        public int                 PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public int                 nLane;                                      // ��Ӧ������
        public NET_MSG_OBJECT       stuObject;                                 // ��⵽������
        public NET_MSG_OBJECT       stuVehicle;                                // ������Ϣ
        public NET_EVENT_FILE_INFO  stuFileInfo;                               // �¼���Ӧ�ļ���Ϣ
        
        public int                 nSequence;                                  // ��ʾץ�����,��3,2,1,1��ʾץ�Ľ���,0��ʾ�쳣����
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved = new byte[2];
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),����� NET_RESERVED_COMMON    
        public NET_RESOLUTION_INFO  stuResolution;                             // ��ӦͼƬ�ķֱ���
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ͨ������Ϣ
        public DEV_TRAFFIC_PARKING_INFO stTrafficParingInfo;                   // ͣ������Ϣ
        public byte[]              bReserved = new byte[384];                  // �����ֽ�
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    }
    
  //�¼����� EVENT_IVS_TRAFFIC_PEDESTRAIN(��ͨ�����¼�)��Ӧ���ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_PEDESTRAIN_INFO extends Structure {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[NET_EVENT_NAME_LEN];      // �¼�����
        public byte[]              bReserved1 = new byte[8];                   // �ֽڶ���
        public int                 PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ
        public NET_RESOLUTION_INFO stuResolution;                              // ��ӦͼƬ�ķֱ���
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),0λ:"*",1λ:"Timing",2λ:"Manual",3λ:"Marked",4λ:"Event",5λ:"Mosaic",6λ:"Cutout" 
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              bReserved2 = new byte[2];
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 nLane;                                      // ��Ӧ������
        public NET_MSG_OBJECT      stuObject;                                  // ��⵽������
        public EVENT_INTELLI_COMM_INFO     stuIntelliCommInfo;                 // �����¼�������Ϣ
        public byte[]              bReserved = new byte[892];                 // �����ֽ�
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    }

    //�¼����� EVENT_IVS_TRAFFIC_THROW(��ͨ������Ʒ�¼�)��Ӧ���ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_THROW_INFO extends Structure {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[NET_EVENT_NAME_LEN];      // �¼�����
        public byte[]              bReserved1 = new byte[8];                   // �ֽڶ���
        public int                 PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ
        public NET_RESOLUTION_INFO stuResolution;                              // ��ӦͼƬ�ķֱ���
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),0λ:"*",1λ:"Timing",2λ:"Manual",3λ:"Marked",4λ:"Event",5λ:"Mosaic",6λ:"Cutout" 
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              bReserved2 = new byte[2];
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 nLane;                                      // ��Ӧ������
        public NET_MSG_OBJECT      stuObject;                                  // ��⵽������
        public EVENT_INTELLI_COMM_INFO     stuIntelliCommInfo;                 // �����¼�������Ϣ
        public byte[]              bReserved = new byte[892];                  // �����ֽ�
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    }
    
    // �¼��ϱ�Я����Ƭ��Ϣ
    public static class EVENT_CARD_INFO extends Structure
    {
        public byte[]              szCardNumber = new byte[NET_EVENT_CARD_LEN];// ��Ƭ����ַ���
        public byte[]              bReserved = new byte[32];                   // �����ֽ�,������չ.
    }
    
    // ����������Ϣ
    public static class EM_VEHICLE_DIRECTION extends Structure
    {
        public static final int    NET_VEHICLE_DIRECTION_UNKOWN = 0;           // δ֪
        public static final int    NET_VEHICLE_DIRECTION_HEAD   = 1;           // ��ͷ    
        public static final int    NET_VEHICLE_DIRECTION_TAIL   = 2;           // ��β  
    }
    
    // ��բ״̬
    public static class EM_OPEN_STROBE_STATE extends Structure
    {
        public static final int    NET_OPEN_STROBE_STATE_UNKOWN = 0;           // δ֪״̬
        public static final int    NET_OPEN_STROBE_STATE_CLOSE  = 1;           // ��բ
        public static final int    NET_OPEN_STROBE_STATE_AUTO   = 2;           // �Զ���բ    
        public static final int    NET_OPEN_STROBE_STATE_MANUAL = 3;           // �ֶ���բ
    }
    
    // �¼����� EVENT_IVS_TRAFFICJUNCTION ��ͨ·���Ϲ����¼�/��Ƶ�羯�ϵĽ�ͨ�����Ϲ����¼���Ӧ�����ݿ�������Ϣ
    // ������ʷԭ��,���Ҫ�������¼�,DEV_EVENT_TRAFFICJUNCTION_INFO��EVENT_IVS_TRAFFICGATEҪһ����
    // �Է�ֹ����Ƶ�羯����Ȧ�羯ͬʱ����ƽ̨���������, ����EVENT_IVS_TRAFFIC_TOLLGATEֻ֧���¿����¼�������
    public static class DEV_EVENT_TRAFFICJUNCTION_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[NET_EVENT_NAME_LEN];      // �¼�����
        public byte                byMainSeatBelt;                             // ����ʻ��,ϵ��ȫ��״̬,1-ϵ��ȫ��,2-δϵ��ȫ��
        public byte                bySlaveSeatBelt;                            // ����ʻ��,ϵ��ȫ��״̬,1-ϵ��ȫ��,2-δϵ��ȫ��
        public byte                byVehicleDirection;                         // ��ǰ��ץ�ĵ��ĳ����ǳ�ͷ���ǳ�β,������� EM_VEHICLE_DIRECTION
        public byte                byOpenStrobeState;                          // ��բ״̬,������� EM_OPEN_STROBE_STATE 
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public NET_MSG_OBJECT      stuObject;                                  // ��⵽������
        public int                 nLane;                                      // ��Ӧ������
        public int                 dwBreakingRule;                             // Υ����������,��һλ:�����; 
                                                                               // �ڶ�λ:�����涨������ʻ;
                                                                               // ����λ:����; ����λ��Υ�µ�ͷ;
                                                                               // ����λ:��ͨ����; ����λ:��ͨ�쳣����
                                                                               // ����λ:ѹ����ʻ; ����Ĭ��Ϊ:��ͨ·���¼�
        public NET_TIME_EX         RedLightUTC;                                // ��ƿ�ʼUTCʱ��
        public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ
        public int                 nSequence;                                  // ��ʾץ�����,��3,2,1,1��ʾץ�Ľ���,0��ʾ�쳣����
        public int                 nSpeed;                                     // ����ʵ���ٶ�Km/h                 
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte                byDirection;                                // ·�ڷ���,1-��ʾ����,2-��ʾ����
        public byte                byLightState;                               // LightState��ʾ���̵�״̬:0 δ֪,1 �̵�,2 ���,3 �Ƶ�
        public byte                byReserved;                                 // �����ֽ�
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),����� NET_RESERVED_COMMON, 0λ:"*",1λ:"Timing",2λ:"Manual",3λ:"Marked",4λ:"Event",5λ:"Mosaic",6λ:"Cutout"   
        public NET_RESOLUTION_INFO stuResolution;                              // ��ӦͼƬ�ķֱ���
        public byte[]              szRecordFile = new byte[NET_COMMON_STRING_128];// ������Ӧ��ԭʼ¼���ļ���Ϣ
        public byte[]              bReserved = new byte[340];                  // �����ֽ�,������չ.
        public int                 nTriggerType;                               // TriggerType:��������,0������,1�״�,2��Ƶ
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ͨ������Ϣ
        public int                 dwRetCardNumber;                            // ��Ƭ����
        public EVENT_CARD_INFO[]   stuCardInfo = (EVENT_CARD_INFO[])new EVENT_CARD_INFO().toArray(NET_EVENT_MAX_CARD_NUM);// ��Ƭ��Ϣ   
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    }    
    
    // �¼����� EVENT_IVS_TRAFFICGATE(��ͨ�����Ϲ����¼�/��Ȧ�羯�ϵĽ�ͨ�����Ϲ����¼�)��Ӧ�����ݿ�������Ϣ
    // ������ʷԭ��,���Ҫ�������¼�,DEV_EVENT_TRAFFICJUNCTION_INFO��EVENT_IVS_TRAFFICGATEҪһ����,�Է�ֹ����Ƶ�羯����Ȧ�羯ͬʱ����ƽ̨���������
    // ���� EVENT_IVS_TRAFFIC_TOLLGATE ֻ֧���¿����¼�������
    public static class DEV_EVENT_TRAFFICGATE_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[NET_EVENT_NAME_LEN];      // �¼�����
        public byte                byOpenStrobeState;                          // ��բ״̬,�������EM_OPEN_STROBE_STATE
        public byte                bReserved1[] = new byte[3];                 // �ֽڶ���
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public NET_MSG_OBJECT      stuObject;                                  // ��⵽������
        public int                 nLane;                                      // ��Ӧ������
        public int                 nSpeed;                                     // ����ʵ���ٶ�Km/h
        public int                 nSpeedUpperLimit;                           // �ٶ����� ��λ��km/h
        public int                 nSpeedLowerLimit;                           // �ٶ����� ��λ��km/h 
        public int                 dwBreakingRule;                             // Υ����������,��һλ:����; 
                                                                               // �ڶ�λ:ѹ����ʻ; ����λ:������ʻ; 
                                                                               // ����λ��Ƿ����ʻ; ����λ:�����;����λ:����·��(�����¼�)
                                                                               // ����λ: ѹ����; �ڰ�λ: �г�ռ��; �ھ�λ: ����ռ��;����Ĭ��Ϊ:��ͨ�����¼�
        public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ
        public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
        public byte                szManualSnapNo[] = new byte[64];            // �ֶ�ץ�����
        public int                 nSequence;                                  // ��ʾץ�����,��3,2,1,1��ʾץ�Ľ���,0��ʾ�쳣����
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����; 
        public byte[]              byReserved = new byte[3];                   // �����ֽ�
        public byte[]              szSnapFlag = new byte[16];                  // �豸������ץ�ı�ʶ
        public byte                bySnapMode;                                 // ץ�ķ�ʽ,0-δ���� 1-ȫ�� 2-���� 4-ͬ��ץ�� 8-����ץ�� 16-����ͼ��
        public byte                byOverSpeedPercentage;                      // ���ٰٷֱ�
        public byte                byUnderSpeedingPercentage;                  // Ƿ�ٰٷֱ�
        public byte                byRedLightMargin;                           // ���������ʱ��,��λ����
        public byte                byDriveDirection;                           // ��ʻ����,0-����(���������豸�����Խ��Խ��),1-����(���������豸�����Խ��ԽԶ)
        public byte[]              szRoadwayNo = new byte[32];                 // ��·���
        public byte[]              szViolationCode = new byte[16];             // Υ�´���
        public byte[]              szViolationDesc = new byte[128];            // Υ������
        public NET_RESOLUTION_INFO stuResolution;                              // ��ӦͼƬ�ķֱ���
        public byte[]              szVehicleType= new byte[32];                // ������С���� Minisize"΢�ͳ�,"Light-duty"С�ͳ�,"Medium"���ͳ�,
                                                                               // "Oversize"���ͳ�,"Huge"����,"Largesize"���� "Unknown"δ֪
        public byte                byVehicleLenth;                             // ��������, ��λ��
        public byte                byLightState;                               // LightState��ʾ���̵�״̬:0 δ֪,1 �̵�,2 ���,3 �Ƶ�
        public byte                byReserved1;                                // �����ֽ�,������չ
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 nOverSpeedMargin;                           // �޸��ٿ���ֵ    ��λ��km/h 
        public int                 nUnderSpeedMargin;                          // �޵��ٿ���ֵ    ��λ��km/h 
        public byte[]              szDrivingDirection = new byte[3*NET_MAX_DRIVINGDIRECTION]; //
                                                                               // "DrivingDirection" : ["Approach", "�Ϻ�", "����"],��ʻ����
                                                                               // "Approach"-����,���������豸�����Խ��Խ����"Leave"-����,
                                                                               // ���������豸�����Խ��ԽԶ,�ڶ��͵����������ֱ�������к�
                                                                               // ���е������ص�,UTF-8����
        public byte[]              szMachineName = new byte[256];              // ���ػ�Զ���豸����
        public byte[]              szMachineAddress = new byte[256];           // ��������ص㡢��·����
        public byte[]              szMachineGroup = new byte[256];             // �������顢�豸������λ
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public NET_SIG_CARWAY_INFO_EX stuSigInfo;                              // �ɳ���������ץ���ź�������Ϣ
        public byte[]              szFilePath = new byte[MAX_PATH];            // �ļ�·��
        public NET_TIME_EX         RedLightUTC;                                // ��ƿ�ʼUTCʱ��
        public Pointer             szDeviceAddress;                            // �豸��ַ,OSD���ӵ�ͼƬ�ϵ�,��Դ������TrafficSnapshot.DeviceAddress,'\0'����
        public float               fActualShutter;                             // ��ǰͼƬ�ع�ʱ��,��λΪ����
        public byte                byActualGain;                               // ��ǰͼƬ����,��ΧΪ0~100
        public byte                byDirection;                                // 0-���� 1-�����򶫱� 2-���� 3-�������� 4-������ 5-���������� 6-������ 7-���������� 8-δ֪
        public byte                bReserve;                                   // �����ֽ�, �ֽڶ���
        public byte                bRetCardNumber;                             // ��Ƭ����
        public EVENT_CARD_INFO[]   stuCardInfo = (EVENT_CARD_INFO[])new EVENT_CARD_INFO().toArray(NET_EVENT_MAX_CARD_NUM);// ��Ƭ��Ϣ
        public byte[]              szDefendCode = new byte[NET_COMMON_STRING_64];           // ͼƬ��α��
        public int                 nTrafficBlackListID;                         // �������������ݿ��¼Ĭ������ID, 0,��Ч��> 0,���������ݼ�¼
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
        public byte[]              bReserved = new byte[452];                  // �����ֽ�,������չ.
    }
    
    //�¼�����EVENT_IVS_TRAFFIC_RUNREDLIGHT(��ͨ-������¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_RUNREDLIGHT_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[128];                     // �¼�����
        public byte[]              bReserved1 = new byte[4];                   // �ֽڶ���
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public int                 nLane;                                      // ��Ӧ������
        public NET_MSG_OBJECT      stuObject;                                  // ������Ϣ
        public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
        public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ 
        public int                 nLightState;                                // ���̵�״̬ 0:δ֪ 1���̵� 2:��� 3:�Ƶ�
        public int                 nSpeed;                                     // ����,km/h
        public int                 nSequence;                                  // ��ʾץ�����,��3,2,1,1��ʾץ�Ľ���,0��ʾ�쳣����
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved = new byte[2];
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public NET_TIME_EX         stRedLightUTC;                              // ��ƿ�ʼʱ��
        public NET_RESOLUTION_INFO stuResolution;                              // ��ӦͼƬ�ķֱ���
        public byte                byRedLightMargin;                           // ���������ʱ��,��λ����
        public byte[]              byAlignment = new byte[3];                  // �ֽڶ���
        public int                 nRedLightPeriod;                            // ��ʾ�������ʱ��,��λ����
        public byte[]              bReserved = new byte[968];                  // �����ֽ�
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ͨ������Ϣ
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    }
    
    //�¼�����EVENT_IVS_TRAFFIC_OVERLINE(��ͨ-ѹ���¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_OVERLINE_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[128];                     // �¼�����
        public byte[]              bReserved1 = new byte[4];                   // �ֽڶ���
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public int                 nLane;                                      // ��Ӧ������
        public NET_MSG_OBJECT      stuObject;                                  // ������Ϣ
        public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
        public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ
        public int                 nSequence;                                  // ��ʾץ�����,��3,2,1,1��ʾץ�Ľ���,0��ʾ�쳣����
        public int                 nSpeed;                                     // ����ʵ���ٶ�,Km/h
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved = new byte[2];
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public NET_RESOLUTION_INFO stuResolution;                              // ��ӦͼƬ�ķֱ���
        public byte[]              bReserved = new byte[1008];                 // �����ֽ�
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ͨ������Ϣ
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    } 
    
    // �¼�����EVENT_IVS_TRAFFIC_RETROGRADE(��ͨ-�����¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_RETROGRADE_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[128];                     // �¼�����
        public byte[]              bReserved1 = new byte[4];                   // �ֽڶ���
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public int                 nLane;                                      // ��Ӧ������
        public NET_MSG_OBJECT      stuObject;                                  // ������Ϣ
        public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
        public NET_EVENT_FILE_INFO  stuFileInfo;                               // �¼���Ӧ�ļ���Ϣ
        public int                 nSequence;                                  // ��ʾץ�����,��3,2,1,1��ʾץ�Ľ���,0��ʾ�쳣����
        public int                 nSpeed;                                     // ����ʵ���ٶ�,Km/h
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved = new byte[2];
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public NET_RESOLUTION_INFO  stuResolution;                             // ��ӦͼƬ�ķֱ���
        public int                bIsExistAlarmRecord;                         // rue:�ж�Ӧ�ı���¼��; false:�޶�Ӧ�ı���¼��
        public int                dwAlarmRecordSize;                           // ¼���С
        public byte[]             szAlarmRecordPath = new byte[NET_COMMON_STRING_256];    // ¼��·��
        public EVENT_INTELLI_COMM_INFO      intelliCommInfo;                   // �����¼�������Ϣ
        public byte[]              bReserved = new byte[524];                  // �����ֽ�
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ͨ������Ϣ
        public int                 nDetectNum;                                 // ���������򶥵���
        public NET_POINT[]         DetectRegion = (NET_POINT[])new NET_POINT().toArray(NET_MAX_DETECT_REGION_NUM); // ����������     
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    }
    
    //�¼�����EVENT_IVS_TRAFFIC_OVERSPEED(��ͨ�����¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_OVERSPEED_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[128];                     // �¼�����
        public byte[]              bReserved1 = new byte[4];                   // �ֽڶ���
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public int                 nLane;                                      // ��Ӧ������
        public NET_MSG_OBJECT      stuObject;                                  // ��⵽������
        public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
        public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ
        public int                 nSpeed;                                     // ����ʵ���ٶ�Km/h
        public int                 nSpeedUpperLimit;                           // �ٶ����� ��λ��km/h
        public int                 nSpeedLowerLimit;                           // �ٶ����� ��λ��km/h 
        public int                 nSequence;                                  // ��ʾץ�����,��3,2,1,1��ʾץ�Ľ���,0��ʾ�쳣����
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;    
        public byte[]              byReserved = new byte[2];
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public NET_RESOLUTION_INFO stuResolution;                              // ��ӦͼƬ�ķֱ���
        public byte[]              szFilePath = new byte[MAX_PATH];            // �ļ�·��
        public EVENT_INTELLI_COMM_INFO     stuIntelliCommInfo;                 // �����¼�������Ϣ
        public byte[]                bReserved = new byte[616];                // �����ֽ�
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ͨ������Ϣ
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    }
    
    //�¼�����EVENT_IVS_TRAFFIC_UNDERSPEED(��ͨǷ���¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_UNDERSPEED_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[128];                     // �¼�����
        public byte[]              bReserved2 = new byte[4];                   // �ֽڶ���
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public int                 nLane;                                      // ��Ӧ������
        public NET_MSG_OBJECT      stuObject;                                  // ��⵽������
        public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
        public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ
        public int                 nSpeed;                                     // ����ʵ���ٶ�Km/h
        public int                 nSpeedUpperLimit;                           // �ٶ����� ��λ��km/h
        public int                 nSpeedLowerLimit;                           // �ٶ����� ��λ��km/h 
        public int                 nSequence;                                  // ��ʾץ�����,��3,2,1,1��ʾץ�Ľ���,0��ʾ�쳣����
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              bReserved1 = new byte[2];                   // ����
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 nUnderSpeedingPercentage;                   // Ƿ�ٰٷֱ�
        public int               dwSnapFlagMask;                               // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public NET_RESOLUTION_INFO  stuResolution;                             // ��ӦͼƬ�ķֱ���
        public EVENT_INTELLI_COMM_INFO     stuIntelliCommInfo;                 // �����¼�������Ϣ
        public byte[]              bReserved = new byte[872];                  // �����ֽ�
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ͨ������Ϣ
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    }
    
    //�¼�����EVENT_IVS_TRAFFIC_WRONGROUTE(��ͨΥ��-����������ʻ)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_WRONGROUTE_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[128];                     // �¼�����
        public byte[]              bReserved1 = new byte[4];                   // �ֽڶ���
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public NET_MSG_OBJECT      stuObject;                                  // ��⵽������
        public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
        public int                 nLane;                                      // ��Ӧ������
        public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ               
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved = new byte[2];
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 nSpeed;                                     // ����ʵ���ٶ�,km/h
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public NET_RESOLUTION_INFO stuResolution;                              // ��ӦͼƬ�ķֱ���
        public byte[]              bReserved = new byte[1012];                 // �����ֽ�,������չ.
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ͨ������Ϣ
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    }
    
    //�¼�����EVENT_IVS_TRAFFIC_OVERYELLOWLINE(��ͨΥ��-ѹ����)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_OVERYELLOWLINE_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[128];                     // �¼�����
        public byte[]              bReserved1 = new byte[4];                   // �ֽڶ���
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public NET_MSG_OBJECT      stuObject;                                  // ��⵽������
        public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
        public int                 nLane;                                      // ��Ӧ������
        public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ 
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved = new byte[2];
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 nSpeed;                                     // ����ʵ���ٶ�,km/h
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public NET_RESOLUTION_INFO stuResolution;                              // ��ӦͼƬ�ķֱ���
        public int                 bIsExistAlarmRecord;                        // bool ���ͣ� 1:�ж�Ӧ�ı���¼��; 0:�޶�Ӧ�ı���¼��
        public int                 dwAlarmRecordSize;                          // ¼���С
        public byte[]              szAlarmRecordPath = new byte[NET_COMMON_STRING_256]; // ¼��·��
        public EVENT_INTELLI_COMM_INFO     stuIntelliCommInfo;                 // �����¼�������Ϣ
        public byte[]              bReserved = new byte[532];                  // �����ֽ�,������չ.
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ͨ������Ϣ

        public int                 nDetectNum;                                 // ���������򶥵���
        public NET_POINT[]         DetectRegion = (NET_POINT[])new NET_POINT().toArray(NET_MAX_DETECT_REGION_NUM); // ����������    
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    }
    
    //�¼�����EVENT_IVS_TRAFFIC_YELLOWPLATEINLANE(��ͨΥ��-���Ƴ�ռ���¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_YELLOWPLATEINLANE_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[128];                     // �¼�����
        public byte[]              bReserved1 = new byte[4];                   // �ֽڶ���
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public NET_MSG_OBJECT      stuObject;                                  // ��⵽������
        public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
        public int                 nLane;                                      // ��Ӧ������
        public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ               
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved = new byte[2];     
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 nSpeed;                                     // ����ʵ���ٶ�,km/h
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public NET_RESOLUTION_INFO stuResolution;                              // ��ӦͼƬ�ķֱ���
        public byte[]              bReserved = new byte[1016];                 // �����ֽ�,������չ.
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ͨ������Ϣ
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    }

    //�¼����� EVENT_IVS_TRAFFIC_VEHICLEINROUTE(�г�ռ���¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_VEHICLEINROUTE_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[128];                     // �¼�����
        public byte[]              bReserved1 = new byte[4];                   // �ֽڶ���
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public NET_MSG_OBJECT      stuObject;                                  // ��⵽������
        public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
        public int                 nLane;                                      // ��Ӧ������
        public int                 nSequence;                                  // ץ�����,��3-2-1/0,1��ʾץ����������,0��ʾץ���쳣����
        public int                 nSpeed;                                     // ����
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ʾ��ͨ���������ݿ��¼
        public NET_EVENT_FILE_INFO  stuFileInfo;                               // �¼���Ӧ�ļ���Ϣ               
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved0 = new byte[2];
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public NET_RESOLUTION_INFO  stuResolution;                             // ��ӦͼƬ�ķֱ���
        public EVENT_INTELLI_COMM_INFO     stuIntelliCommInfo;                 // �����¼�������Ϣ
        public byte[]              byReserved = new byte[884];           
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    }
    
    //�¼�����EVENT_IVS_TRAFFIC_CROSSLANE(��ͨΥ��-Υ�±��)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_CROSSLANE_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[128];                                // �¼�����
        public byte[]              bReserved1 = new byte[4];                              // �ֽڶ���
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public NET_MSG_OBJECT      stuObject;                                  // ��⵽������
        public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
        public int                 nLane;                                      // ��Ӧ������
        public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ               
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved = new byte[2];       
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
         public int                nSpeed;                                     // ����ʵ���ٶ�,km/h
        public int               dwSnapFlagMask;                               // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public NET_RESOLUTION_INFO  stuResolution;                             // ��ӦͼƬ�ķֱ���
    	public EVENT_INTELLI_COMM_INFO     stuIntelliCommInfo;                 // �����¼�������Ϣ
    	public byte[]             bReserved = new byte[876];                   // �����ֽ�,������չ.������չ.
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stuTrafficCar;                // ��ͨ������Ϣ
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    }
     
    // �¼�����EVENT_IVS_TRAFFIC_NOPASSING(��ͨΥ��-��ֹͨ���¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_NOPASSING_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[NET_EVENT_NAME_LEN];      // �¼�����
        public int                 nTriggerType;                               // TriggerType:��������,0������,1�״�,2��Ƶ
        public int                 PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public int                 UTCMS;                                      // 
        public int                 nMark;                                      // �ײ�����Ĵ���ץ��֡���
        public int                 nSequence;                                  // ��ʾץ�����,��3,2,1,1��ʾץ�Ľ���,0��ʾ�쳣����
        public NET_EVENT_FILE_INFO  stuFileInfo;                               // �¼���Ӧ�ļ���Ϣ
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ͨ������Ϣ
        public int               dwSnapFlagMask;                               // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public NET_RESOLUTION_INFO  stuResolution;                             // ��ӦͼƬ�ķֱ���
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public byte[]              byReserved1 = new byte[3];
        public int                 nLane;                                      // ��Ӧ������
        public NET_MSG_OBJECT      stuObject;                                  // ��⵽������
        public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
        public int                 nFrameSequence;                             // ��Ƶ����֡���
        public int                 nSource;                                    // ��Ƶ����������Դ��ַ   
        public byte[]              byReserved = new byte[1024];                // �����ֽ�
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    }
    
    //�¼����� EVENT_IVS_TRAFFIC_PEDESTRAINPRIORITY(���������������¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_PEDESTRAINPRIORITY_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[128];                     // �¼�����
        public byte[]              bReserved1 = new byte[4];                   // �ֽڶ���
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public NET_MSG_OBJECT       stuObject;                                 // ��⵽������
        public NET_MSG_OBJECT       stuVehicle;                                // ������Ϣ
        public NET_EVENT_FILE_INFO  stuFileInfo;                               // �¼���Ӧ�ļ���Ϣ
        public int                 nLane;                                      // ��Ӧ������
        public double              dInitialUTC;                                // �¼���ʼUTCʱ��    UTCΪ�¼���UTC (1970-1-1 00:00:00)������
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved = new byte[2];
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ʾ��ͨ���������ݿ��¼
        public NET_RESOLUTION_INFO  stuResolution;                             // ��ӦͼƬ�ķֱ���
        public byte[]              bReserved = new byte[1024];                 // �����ֽ�,������չ.
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ

    }
    
    //�¼����� EVENT_IVS_TRAFFIC_VEHICLEINBUSROUTE(ռ�ù��������¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_VEHICLEINBUSROUTE_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[128];                     // �¼�����
        public byte[]              bReserved1 = new byte[4];                   // �ֽڶ���
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public NET_MSG_OBJECT       stuObject;                                 // ��⵽������
        public NET_MSG_OBJECT       stuVehicle;                                // ������Ϣ
        public NET_EVENT_FILE_INFO  stuFileInfo;                               // �¼���Ӧ�ļ���Ϣ
        public int                 nLane;                                      // ��Ӧ������
        public int                 nSequence;                                  // ץ�����,��3-2-1/0,1��ʾץ����������,0��ʾץ���쳣����
        public int                 nSpeed;                                     // ����,km/h
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved = new byte[2];
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int               dwSnapFlagMask;                               // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ʾ��ͨ���������ݿ��¼
        public NET_RESOLUTION_INFO  stuResolution;                             // ��ӦͼƬ�ķֱ���
        public byte[]              bReserved = new byte[1020];                 // �����ֽ�,������չ.
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ

    }
    
   //�¼����� EVENT_IVS_TRAFFIC_BACKING(Υ�µ����¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_IVS_TRAFFIC_BACKING_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[128];                     // �¼�����
        public byte[]              bReserved1 = new byte[4];                   // �ֽڶ���
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public NET_MSG_OBJECT       stuObject;                                 // ��⵽������
        public NET_MSG_OBJECT       stuVehicle;                                // ������Ϣ
        public NET_EVENT_FILE_INFO  stuFileInfo;                               // �¼���Ӧ�ļ���Ϣ
        public int                 nLane;                                      // ��Ӧ������
        public int                 nSequence;                                  // ץ�����,��3-2-1/0,1��ʾץ����������,0��ʾץ���쳣����
        public int                 nSpeed;                                     // ����,km/h
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved = new byte[2];
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ʾ��ͨ���������ݿ��¼
        public NET_RESOLUTION_INFO  stuResolution;                             // ��ӦͼƬ�ķֱ���
        public EVENT_INTELLI_COMM_INFO     stuIntelliCommInfo;                 // �����¼�������Ϣ
        public byte[]              bReserved = new byte[888];                  // �����ֽ�,������չ.
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ

    }

 // �¼����� EVENT_IVS_TRAFFIC_OVERSTOPLINE (ѹͣ�����¼�)��Ӧ�����ݿ�������Ϣ
 public static class DEV_EVENT_TRAFFIC_OVERSTOPLINE extends Structure
 {
     public int                     nChannelID;                     // ͨ����
     public byte[]                  szName = new byte[128];         // �¼�����
     public int                     nTriggerType;                   // TriggerType:��������,0������,1�״�,2��Ƶ
     public int                     PTS;                            // ʱ���(��λ�Ǻ���)
     public NET_TIME_EX             UTC;                            // �¼�������ʱ��
     public int                     nEventID;                       // �¼�ID
     public int                     nSequence;                      // ��ʾץ�����,��3,2,1,1��ʾץ�Ľ���,0��ʾ�쳣����
     public byte                    byEventAction;                  // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
     public byte                    byImageIndex;                   // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
     public byte[]                  byReserved1 = new byte[2];
     public int                     nLane;                          // ��Ӧ������
     public NET_MSG_OBJECT          stuObject;                      // ��⵽������
     public NET_MSG_OBJECT          stuVehicle;                     // ������Ϣ
     public NET_EVENT_FILE_INFO     stuFileInfo;                    // �¼���Ӧ�ļ���Ϣ
     public int                     nMark;                          // �ײ�����Ĵ���ץ��֡���
     public int                     nFrameSequence;                 // ��Ƶ����֡���
     public int                     nSource;                        // ��Ƶ����������Դ��ַ
     public int                     dwSnapFlagMask;                 // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
     public NET_RESOLUTION_INFO     stuResolution;                  // ��ӦͼƬ�ķֱ���
     public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stuTrafficCar;        // ��ͨ������Ϣ
     public int                     nSpeed;                         // ����ʵ���ٶ�,Km/h
     public byte[]                  byReserved = new byte[1024];    // �����ֽ�
     public EVENT_COMM_INFO         stCommInfo;                     // ������Ϣ

 }

 //�¼����� EVENT_IVS_TRAFFIC_PARKINGONYELLOWBOX(��������ץ���¼�)��Ӧ�����ݿ�������Ϣ
 public static class DEV_EVENT_TRAFFIC_PARKINGONYELLOWBOX_INFO extends Structure
 {
     public int                 nChannelID;                                 // ͨ����
     public byte[]              szName = new byte[128];                     // �¼�����
     public byte[]              bReserved1 = new byte[8];                   // �ֽڶ���
     public int                 PTS;                                        // ʱ���(��λ�Ǻ���)
     public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
     public int                 nEventID;                                   // �¼�ID
     public int                 nLane;                                      // ��Ӧ������
     public NET_MSG_OBJECT      stuObject;                                  // ��⵽������
     public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
     public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ

     public int                 nInterval1;                                 // �ڶ��ź͵�һ�ŵ���ʱʱ��,��λ��
     public int                 nInterval2;                                 // �����ź͵ڶ��ŵ���ʱʱ��,��λ��
     public int                 nFollowTime;                                // ����ʱ��,���һ������ǰһ��������������ʱ���С�ڴ�ֵ,����Ϊ�Ǹ�������,����������������ͣ������Υ��

     public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
     public byte[]              byReserved = new byte[2];
     public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
     public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
     public NET_RESOLUTION_INFO stuResolution;                              // ��ӦͼƬ�ķֱ���
     public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ͨ������Ϣ
     public byte[]              bReserved = new byte[1024];                 // �����ֽ�
     public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ

 }

 // �¼�����EVENT_IVS_TRAFFIC_WITHOUT_SAFEBELT(��ͨδϵ��ȫ���¼��¼�)��Ӧ�����ݿ�������Ϣ
 public static class  DEV_EVENT_TRAFFIC_WITHOUT_SAFEBELT extends Structure
 {
     public int                     nChannelID;                     // ͨ����
     public byte[]                  szName = new byte[128];      // �¼�����
     public int                     nTriggerType;                   // TriggerType:��������,0������,1�״�,2��Ƶ
     public int                     PTS;                            // ʱ���(��λ�Ǻ���)
     public NET_TIME_EX             UTC;                            // �¼�������ʱ��
     public int                     nEventID;                       // �¼�ID
     public int                     nSequence;                      // ��ʾץ�����,��3,2,1,1��ʾץ�Ľ���,0��ʾ�쳣����
     public byte                    byEventAction;                  // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;    public byte                    byReserved1[2];
     public byte                    byImageIndex;                   // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
     public byte[]                  byReserved1 = new byte[2];
     public NET_EVENT_FILE_INFO     stuFileInfo;                    // �¼���Ӧ�ļ���Ϣ
     public int                     nLane;                          // ��Ӧ������
     public int                     nMark;                          // �ײ�����Ĵ���ץ��֡���
     public int                     nFrameSequence;                 // ��Ƶ����֡���
     public int                     nSource;                        // ��Ƶ����������Դ��ַ
     public NET_MSG_OBJECT          stuObject;                      // ��⵽������
     public NET_MSG_OBJECT          stuVehicle;                     // ������Ϣ
     public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stuTrafficCar;        // ��ͨ������Ϣ
     public int                     nSpeed;                         // ����ʵ���ٶ�,Km/h
     public int      				emMainSeat;                     // ����ʻ��λ��ȫ��״̬   �ο� NET_SAFEBELT_STATE
     public int      				emSlaveSeat;                    // ����ʻ��λ��ȫ��״̬ �ο� NET_SAFEBELT_STATE
     public int                     dwSnapFlagMask;                 // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
     public NET_RESOLUTION_INFO     stuResolution;                  // ��ӦͼƬ�ķֱ���
     public byte[]                  byReserved = new byte[1024];    // �����ֽ�
     public EVENT_COMM_INFO         stCommInfo;                     // ������Ϣ

 }

 //�¼�����EVENT_IVS_TRAFFIC_JAM_FORBID_INTO(��ͨӵ�½����¼�)��Ӧ�����ݿ�������Ϣ
 public static class DEV_EVENT_ALARM_JAMFORBIDINTO_INFO extends Structure
 {
 	 public int                 nChannelID;                                 // ͨ����
     public byte[]              szName = new byte[NET_EVENT_NAME_LEN];      // �¼�����
     public byte[]              bReserved1 = new byte[4];                   // �ֽڶ���
     public int                 PTS;                                        // ʱ���(��λ�Ǻ���)
     public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
     public int                 nEveID;                                     // �¼�ID
     ///////////////////////////////����Ϊ�����ֶ�//////////////////////////////
 	 public NET_EVENT_FILE_INFO  stuFileInfo;                               // �¼���Ӧ�ļ���Ϣ 
 	 public int					nMark;										// �ײ�����Ĵ���ץ��֡���
 	 public int					nSource;									// ��Ƶ����������Դ��ַ
 	 public int					nSequence;									// ��ʾץ�����,��3-2-1/0,1��ʾץ����������,0��ʾץ���쳣����
 	 public int					nFrameSequence;								// ֡���
 	 public int					nLane;										// ������
 	 public byte                 byImageIndex;                   			// ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
 	 public NET_MSG_OBJECT       stuObject;                      			// ��⵽������
 	 public byte[]               bReserved = new byte[1024];                // �����ֽ�
 	 public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stuTrafficCar;                // ��ͨ������Ϣ
 	 public EVENT_COMM_INFO      stCommInfo;                     			// ������Ϣ
     public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
     public NET_RESOLUTION_INFO stuResolution;                              // ��ӦͼƬ�ķֱ���
 }

 //�¼�����EVENT_IVS_TRAFFIC_PASSNOTINORDER(��ͨ-δ���涨����ͨ��)��Ӧ�����ݿ�������Ϣ
 public static class DEV_EVENT_TRAFFIC_PASSNOTINORDER_INFO extends Structure
 {
     public int                 nChannelID;                                 // ͨ����
     public byte[]              szName = new byte[NET_EVENT_NAME_LEN];      // �¼�����
     public byte[]              bReserved1 = new byte[4];                   // �ֽڶ���
     public double              PTS;                                        // ʱ���(��λ�Ǻ���)
     public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
     public int                 nEventID;                                   // �¼�ID
     public int                 nLane;                                      // ��Ӧ������
     public NET_MSG_OBJECT      stuObject;                                  // ������Ϣ
     public NET_MSG_OBJECT      stuVehicle;                                 // ������Ϣ
     public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ 
     public int                 nSequence;                                  // ��ʾץ�����,��3,2,1,1��ʾץ�Ľ���,0��ʾ�쳣����
     public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
     public byte[]              byReserved = new byte[2];
     public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
     public int               dwSnapFlagMask;                               // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
     public NET_RESOLUTION_INFO  stuResolution;                             // ��ӦͼƬ�ķֱ���
     public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ͨ������Ϣ
     public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
     public byte[]              bReserved = new byte[1024];                 // �����ֽ�
 }
    
    //�¼�����EVENT_IVS_TRAFFIC_MANUALSNAP(��ͨ�ֶ�ץ���¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_TRAFFIC_MANUALSNAP_INFO extends Structure
    {
        public int                 nChannelID;                                 // ͨ����
        public byte[]              szName = new byte[128];                     // �¼�����
        public byte[]              bReserved1 = new byte[4];                   // �ֽڶ���
        public double              PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                        // �¼�������ʱ��
        public int                 nEventID;                                   // �¼�ID
        public int                 nLane;                                      // ��Ӧ������
        public byte[]              szManualSnapNo = new byte[64];              // �ֶ�ץ����� 
        public NET_MSG_OBJECT      stuObject;                                  // ��⵽������
        public NET_MSG_OBJECT      stuVehicle;                                 // ��⵽�ĳ�����Ϣ
        public DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO stTrafficCar;                 // ��ʾ��ͨ���������ݿ��¼
        public NET_EVENT_FILE_INFO stuFileInfo;                                // �¼���Ӧ�ļ���Ϣ
        public byte                bEventAction;                               // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved = new byte[2];
        public byte                byImageIndex;                               // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 dwSnapFlagMask;                             // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public NET_RESOLUTION_INFO stuResolution;                              // ��ӦͼƬ�ķֱ���
        public byte[]              bReserved = new byte[1016];                 // �����ֽ�,������չ.
        public EVENT_COMM_INFO     stCommInfo;                                 // ������Ϣ
    }
    
    // �¼����� EVENT_IVS_CROSSLINEDETECTION(�������¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_CROSSLINE_INFO extends Structure {
        public int                 nChannelID;                         // ͨ����
        public byte[]              szName = new byte[128];             // �¼�����
        public byte[]              bReserved1 = new byte[4];           // �ֽڶ���
        public double              PTS;                                // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                // �¼�������ʱ��
        public int                 nEventID;                           // �¼�ID
        public NET_MSG_OBJECT      stuObject;                          // ��⵽������
        public NET_EVENT_FILE_INFO stuFileInfo;                        // �¼���Ӧ�ļ���Ϣ
        public NET_POINT[]         DetectLine = (NET_POINT[])new NET_POINT().toArray(NET_MAX_DETECT_LINE_NUM);// ��������
        public int                 nDetectLineNum;                     // �������߶�����
        public NET_POINT[]         TrackLine = (NET_POINT[])new NET_POINT().toArray(NET_MAX_TRACK_LINE_NUM);   // �����˶��켣
        public int                 nTrackLineNum;                      // �����˶��켣������
        public byte                bEventAction;                       // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte                bDirection;                         // ��ʾ���ַ���, 0-��������, 1-��������
        public byte                byReserved;
        public byte                byImageIndex;                       // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 dwSnapFlagMask;                     // ץͼ��־(��λ),�����  NET_RESERVED_COMMON
        public int                 nSourceIndex;                       // �¼�Դ�豸�ϵ�index,-1��ʾ������Ч,-1��ʾ������Ч
        public byte[]              szSourceDevice = new byte[MAX_PATH];           // �¼�Դ�豸Ψһ��ʶ,�ֶβ����ڻ���Ϊ�ձ�ʾ�����豸
        public int        nOccurrenceCount;                   		   // �¼������ۼƴ���, ����Ϊunsigned int
        public EVENT_INTELLI_COMM_INFO     stuIntelliCommInfo;         // �����¼�������Ϣ
        public byte[]              bReserved = new byte[476];          // �����ֽ�,������չ.
    }
    
    // �¼����� EVENT_IVS_CROSSREGIONDETECTION(�������¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_CROSSREGION_INFO extends Structure {
        public int                 nChannelID;                         // ͨ����
        public byte[]              szName = new byte[128];             // �¼�����
        public byte[]              bReserved1 = new byte[4];           // �ֽڶ���
        public double              PTS;                                // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                // �¼�������ʱ��
        public int                 nEventID;                           // �¼�ID
        public NET_MSG_OBJECT      stuObject;                          // ��⵽������
        public NET_EVENT_FILE_INFO stuFileInfo;                        // �¼���Ӧ�ļ���Ϣ
        public NET_POINT[]         DetectRegion = (NET_POINT[])new NET_POINT().toArray(NET_MAX_DETECT_REGION_NUM); // ����������
        public int                 nDetectRegionNum;                   // ���������򶥵���
        public NET_POINT[]         TrackLine = (NET_POINT[])new NET_POINT().toArray(NET_MAX_TRACK_LINE_NUM);   // �����˶��켣
        public int                 nTrackLineNum;                      // �����˶��켣������
        public byte                bEventAction;                       // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte                bDirection;                         // ��ʾ���ַ���, 0-����, 1-�뿪,2-����,3-��ʧ
        public byte                bActionType;                        // ��ʾ��⶯������,0-���� 1-��ʧ 2-�������� 3-��Խ����
        public byte                byImageIndex;                       // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 dwSnapFlagMask;                     // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public int                 nSourceIndex;                       // �¼�Դ�豸�ϵ�index,-1��ʾ������Ч
        public byte[]              szSourceDevice = new byte[MAX_PATH];// �¼�Դ�豸Ψһ��ʶ,�ֶβ����ڻ���Ϊ�ձ�ʾ�����豸
        public int        		   nOccurrenceCount;                   // �¼������ۼƴ���, unsigned int ����
        public byte[]              bReserved = new byte[536];          // �����ֽ�,������չ.
        public int                 nObjectNum;                         // ��⵽���������
        public NET_MSG_OBJECT[]    stuObjectIDs = (NET_MSG_OBJECT[]) new NET_MSG_OBJECT().toArray(NET_MAX_OBJECT_LIST);   // ��⵽������
        public int                 nTrackNum;                          // �켣��(���⵽���������  nObjectNum ��Ӧ)
        public NET_POLY_POINTS[]   stuTrackInfo = (NET_POLY_POINTS[]) new NET_POLY_POINTS().toArray(NET_MAX_OBJECT_LIST);   // �켣��Ϣ(���⵽�������Ӧ)
    	public EVENT_INTELLI_COMM_INFO     stuIntelliCommInfo;         // �����¼�������Ϣ
    }
    
    // �¼����� EVENT_IVS_WANDERDETECTION(�ǻ��¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_WANDER_INFO extends Structure {
        public int                 nChannelID;                         // ͨ����
        public byte[]              szName = new byte[128];             // �¼�����
        public byte[]              bReserved1 = new byte[4];           // �ֽڶ���
        public double              PTS;                                // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                // �¼�������ʱ��
        public int                 nEventID;                           // �¼�ID
        public NET_EVENT_FILE_INFO  stuFileInfo;                       // �¼���Ӧ�ļ���Ϣ
        public byte                bEventAction;                       // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved = new byte[2];           // �����ֽ�
        public byte                byImageIndex;                       // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 nObjectNum;                         // ��⵽���������
        public NET_MSG_OBJECT[]    stuObjectIDs = (NET_MSG_OBJECT[]) new NET_MSG_OBJECT().toArray(NET_MAX_OBJECT_LIST);   // ��⵽������
        public int                 nTrackNum;                          // �켣��(���⵽�����������Ӧ)
        public NET_POLY_POINTS[]   stuTrackInfo = (NET_POLY_POINTS[]) new NET_POLY_POINTS().toArray(NET_MAX_OBJECT_LIST);   // �켣��Ϣ(���⵽�������Ӧ)
        public int                 nDetectRegionNum;                   // ���������򶥵���
        public NET_POINT[]         DetectRegion = (NET_POINT[])new NET_POINT().toArray(NET_MAX_DETECT_REGION_NUM);    // ����������
        public int                 dwSnapFlagMask;                     // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public int                 nSourceIndex;                       // �¼�Դ�豸�ϵ�index,-1��ʾ������Ч
        public byte[]              szSourceDevice = new byte[MAX_PATH]; // �¼�Դ�豸Ψһ��ʶ,�ֶβ����ڻ���Ϊ�ձ�ʾ�����豸
        public int        		   nOccurrenceCount;                   // �¼������ۼƴ���, unsigned int ����
        public EVENT_INTELLI_COMM_INFO     stuIntelliCommInfo;         // �����¼�������Ϣ
        public byte[]              bReserved =  new byte[624];         // �����ֽ�,������չ.
    }
    
    //�¼����� EVENT_IVS_LEAVEDETECTION(��ڼ���¼�)��Ӧ���ݿ�������Ϣ
    public static class DEV_EVENT_IVS_LEAVE_INFO extends Structure {
        public int                 nChannelID;                         // ͨ����
        public byte[]              szName = new byte[128];             // �¼�����
        public byte[]              bReserved1 = new byte[4];           // �ֽڶ���
        public double              PTS;                                // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                // �¼�������ʱ��
        public int                 nEventID;                           // �¼�ID
        public NET_MSG_OBJECT      stuObject;                          // ��⵽������
        public NET_EVENT_FILE_INFO stuFileInfo;                        // �¼���Ӧ�ļ���Ϣ
        public NET_RESOLUTION_INFO stuResolution;                      // ��ӦͼƬ�ķֱ���
        public int                 nDetectRegionNum;                   // ���������򶥵���
        public NET_POINT[]         DetectRegion = (NET_POINT[])new NET_POINT().toArray(NET_MAX_DETECT_REGION_NUM);// ����������
        public byte                bEventAction;                       // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;    
        public byte                byImageIndex;                       // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
    	public EVENT_INTELLI_COMM_INFO     stuIntelliCommInfo;         // �����¼�������Ϣ
    	public byte[]              bReserved = new byte[894];          // �����ֽ�
    }

    //�¼����� EVENT_IVS_AUDIO_ABNORMALDETECTION(�����쳣���)��Ӧ���ݿ�������Ϣ
    public static class DEV_EVENT_IVS_AUDIO_ABNORMALDETECTION_INFO extends Structure {
        public int                 nChannelID;                         // ͨ����
        public byte[]              szName = new byte[128];             // �¼�����
        public byte[]              bReserved1 = new byte[4];           // �ֽڶ���
        public double              PTS;                                // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                // �¼�������ʱ��
        public int                 nEventID;                           // �¼�ID
        public NET_EVENT_FILE_INFO  stuFileInfo;                       // �¼���Ӧ�ļ���Ϣ
        public int                 nDecibel;                           // ����ǿ��
        public int                 nFrequency;                         // ����Ƶ��
        public byte                bEventAction;                       // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved = new byte[2];
        public byte                byImageIndex;                       // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 dwSnapFlagMask;                     // ץͼ��־(��λ),�����NET_RESERVED_COMMON
        public NET_RESOLUTION_INFO stuResolution;                      // ��ӦͼƬ�ķֱ���
        public byte[]              bReserved = new byte[1024];         // �����ֽ�,������չ.
    }
    
    //�¼����� EVENT_IVS_CLIMBDETECTION(�ʸ߼���¼�)��Ӧ���ݿ�������Ϣ
    public static class DEV_EVENT_IVS_CLIMB_INFO extends Structure {
        public int                 nChannelID;                         // ͨ����
        public byte[]              szName = new byte[128];             // �¼�����
        public byte[]              bReserved1 = new byte[4];           // �ֽڶ���
        public double              PTS;                                // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                // �¼�������ʱ��
        public int                 nEventID;                           // �¼�ID
        public NET_MSG_OBJECT      stuObject;                          // ��⵽������
        public NET_EVENT_FILE_INFO stuFileInfo;                        // �¼���Ӧ�ļ���Ϣ
        public NET_RESOLUTION_INFO stuResolution;                      // ��ӦͼƬ�ķֱ���
        public int                 nDetectLineNum;                     // �������߶�����
        public NET_POINT[]         DetectLine = (NET_POINT[])new NET_POINT().toArray(NET_MAX_DETECT_LINE_NUM);         // ��������
        public byte                bEventAction;                       // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte                byImageIndex;                       // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int        		   nOccurrenceCount;                   // �¼������ۼƴ���, unsigned int
        public EVENT_INTELLI_COMM_INFO     stuIntelliCommInfo;         // �����¼�������Ϣ
        public byte[]              bReserved = new byte[890];          // �����ֽ�
    }
    
    // �¼����� EVENT_IVS_FIGHTDETECTION(��Ź�¼�)��Ӧ�����ݿ�������Ϣ
    public static class DEV_EVENT_FIGHT_INFO extends Structure {
        public int                 nChannelID;                         // ͨ����
        public byte[]              szName = new byte[128];             // �¼�����
        public byte[]              bReserved1 = new byte[4];           // �ֽڶ���
        public double              PTS;                                // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX         UTC;                                // �¼�������ʱ��
        public int                 nEventID;                           // �¼�ID
        public int                 nObjectNum;                         // ��⵽���������
        public NET_MSG_OBJECT[]    stuObjectIDs = (NET_MSG_OBJECT[])new NET_MSG_OBJECT().toArray(NET_MAX_OBJECT_LIST);   // ��⵽�������б�
        public NET_EVENT_FILE_INFO  stuFileInfo;                       // �¼���Ӧ�ļ���Ϣ
        public byte                bEventAction;                       // �¼�����,0��ʾ�����¼�,1��ʾ�������¼���ʼ,2��ʾ�������¼�����;
        public byte[]              byReserved = new byte[2];           // �����ֽ�
        public byte                byImageIndex;                       // ͼƬ�����, ͬһʱ����(��ȷ����)�����ж���ͼƬ, ��0��ʼ
        public int                 nDetectRegionNum;                   // ���������򶥵���
        public NET_POINT[]         DetectRegion = (NET_POINT[]) new NET_POINT().toArray(NET_MAX_DETECT_REGION_NUM);    // ����������
        
        public int                 dwSnapFlagMask;                     // ץͼ��־(��λ),�����NET_RESERVED_COMMON    
        public int                 nSourceIndex;                       // �¼�Դ�豸�ϵ�index,-1��ʾ������Ч
        public byte[]              szSourceDevice = new byte[MAX_PATH]; // �¼�Դ�豸Ψһ��ʶ,�ֶβ����ڻ���Ϊ�ձ�ʾ�����豸
        public int                 nOccurrenceCount;                   // �¼������ۼƴ���, unsigned int ����
        public EVENT_INTELLI_COMM_INFO     stuIntelliCommInfo;         // �����¼�������Ϣ
        public byte[]              bReserved = new byte[492];          // �����ֽ�,������չ.
    }
    
    // ��������
    public static class EM_REFUEL_TYPE extends Structure {
        public static final int 	EM_REFUEL_TYPE_UNKNOWN = 0;								// unknown
        public static final int		EM_REFUEL_TYPE_NINETY_EIGHT = 1;						// "98#"
        public static final int		EM_REFUEL_TYPE_NINETY_SEVEN = 2;						// "97#"
        public static final int		EM_REFUEL_TYPE_NINETY_FIVE  = 3;						// "95#"
        public static final int		EM_REFUEL_TYPE_NINETY_THREE = 4;                        // "93#"
        public static final int		EM_REFUEL_TYPE_NINETY = 5;								// "90#"
        public static final int		EM_REFUEL_TYPE_TEN 	= 6;								// "10#"
        public static final int		EM_REFUEL_TYPE_FIVE = 7;								// "5#"
        public static final int		EM_REFUEL_TYPE_ZERO = 8; 								// "0#"
        public static final int		EM_REFUEL_TYPE_NEGATIVE_TEN = 9;						// "-10#"
        public static final int		EM_REFUEL_TYPE_NEGATIVE_TWENTY = 10;					// "-20#"
        public static final int		EM_REFUEL_TYPE_NEGATIVE_THIRTY_FIVE = 11;				// "-35#"
        public static final int		EM_REFUEL_TYPE_NEGATIVE_FIFTY = 12;						// "-50#"   	
    }

    // ����ץ��ͼƬ��Ϣ
    public static class DEV_EVENT_TRAFFIC_FCC_IMAGE extends Structure {
        public int              dwOffSet; // ͼƬ�ļ��ڶ��������ݿ��е�ƫ��λ��, ��λ:�ֽ�
        public int              dwLength; // ͼƬ��С, ��λ:�ֽ�
        public short            wWidth;   // ͼƬ���, ��λ:����
        public short            wHeight;  // ͼƬ�߶�, ��λ:����
    }

    // ����ץͼ��Ϣ
    public static class DEV_EVENT_TRAFFIC_FCC_OBJECT extends Structure {
    	public DEV_EVENT_TRAFFIC_FCC_IMAGE	stuImage; // ����ץ��ͼƬ��Ϣ
    }

    // �¼�����  EVENT_IVS_TRAFFIC_FCC ����վ��ǹ����ǹ�¼�
    public static class DEV_EVENT_TRAFFIC_FCC_INFO extends Structure {
    	public int              nChannelID;                                 // ͨ����
        public byte[]           szName = new byte[NET_EVENT_NAME_LEN];      // �¼�����
        public int				nTriggerID;									// ��������: 1��ʾ��ǹ, 2��ʾ��ǹ
        public double           PTS;                                        // ʱ���(��λ�Ǻ���)
        public NET_TIME_EX      UTC;                                        // �¼�������ʱ��
        public int              nEventID;                                   // �¼�ID
        ///////////////////////////////����Ϊ�����ֶ�//////////////////////////////

    	public int				nNum;										// ����ǹ��
    	public int				nLitre;										// ��������,��λ 0.01��
    	public int   			emType;										// ��������: ȡֵ��Χ{"90#","93#","10#","-20#"}, ����ο� EM_REFUEL_TYPE
    	public int				dwMoney;									// ���ͽ��,��λ 0.01Ԫ
    	public byte[]		    szText = new byte[NET_COMMON_STRING_16];	// ���ƺ�
    	public byte[]			szTime = new byte[NET_COMMON_STRING_32];	// �¼�����ʱ��: "2016-05-23 10:31:17"
    	public DEV_EVENT_TRAFFIC_FCC_OBJECT	stuObject;						// ����ץͼ��Ϣ
    	public byte[]			bReserved = new byte[1024];					// �����ֽ�,������չ
    }
    
    // ��������߶�����Ϣ
    public static class NET_POLY_POINTS extends Structure
    {
        public int         nPointNum;                               	// ������
        public NET_POINT[] stuPoints = (NET_POINT[])new NET_POINT().toArray(NET_MAX_DETECT_REGION_NUM);     // ������Ϣ
    }
    
    
    // ץͼ�����ṹ��
    public static class SNAP_PARAMS extends Structure
    {
        public int     Channel;                       // ץͼ��ͨ��
        public int     Quality;                       // ���ʣ�1~6
        public int     ImageSize;                     // �����С��0��QCIF,1��CIF,2��D1
        public int     mode;                          // ץͼģʽ��-1:��ʾֹͣץͼ, 0����ʾ����һ֡, 1����ʾ��ʱ��������, 2����ʾ��������
        public int     InterSnap;                     // ʱ�䵥λ�룻��mode=1��ʾ��ʱ��������ʱ
                                                      // ֻ�в��������豸(�磺�����豸)֧��ͨ�����ֶ�ʵ�ֶ�ʱץͼʱ����������
                                                      // ����ͨ�� CFG_CMD_ENCODE ���õ�stuSnapFormat[nSnapMode].stuVideoFormat.nFrameRate�ֶ�ʵ����ع���
        public int     CmdSerial;                     // �������кţ���Чֵ��Χ 0~65535��������Χ�ᱻ�ض�Ϊ unsigned short
        public int[]   Reserved = new int[4];
    }
    
    // ��ӦCLIENT_StartSearchDevices�ӿ�
    public static class DEVICE_NET_INFO_EX extends Structure
    {
        public int iIPVersion;//4forIPV4,
        public byte[] szIP = new byte[64];//IPIPV4����"192.168.0.1"
        public int nPort;//tcp�˿�
        public byte[] szSubmask = new byte[64];//��������IPV6����������
        public byte[] szGateway = new byte[64];//����
        public byte[] szMac = new byte[NET_MACADDR_LEN];//MAC��ַ
        public byte[] szDeviceType = new byte[NET_DEV_TYPE_LEN];//�豸����
        public byte byManuFactory;//Ŀ���豸����������,����ο�EM_IPC_TYPE��
        public byte byDefinition;//1-����2-����
        public byte bDhcpEn;//Dhcpʹ��״̬,true-��,false-��, ����Ϊbool, ȡֵ0����1
        public byte byReserved1;//�ֽڶ���
        public byte[] verifyData = new byte[88];//У������ͨ���첽�����ص���ȡ(���޸��豸IPʱ���ô���Ϣ����У��)
        public byte[] szSerialNo = new byte[NET_DEV_SERIALNO_LEN];//���к�
        public byte[] szDevSoftVersion = new byte[NET_MAX_URL_LEN];//�豸����汾��
        public byte[] szDetailType = new byte[NET_DEV_TYPE_LEN];//�豸�ͺ�
        public byte[] szVendor = new byte[NET_MAX_STRING_LEN];//OEM�ͻ�����
        public byte[] szDevName = new byte[NET_MACHINE_NAME_NUM];//�豸����
        public byte[] szUserName = new byte[NET_USER_NAME_LENGTH_EX];//��½�豸�û��������޸��豸IPʱ��Ҫ��д��
        public byte[] szPassWord = new byte[NET_USER_NAME_LENGTH_EX];//��½�豸���루���޸��豸IPʱ��Ҫ��д��
        public short nHttpPort;//HTTP����˿ں�, unsigned short����
        public short wVideoInputCh;//��Ƶ����ͨ����
        public short wRemoteVideoInputCh;//Զ����Ƶ����ͨ����
        public short wVideoOutputCh;//��Ƶ���ͨ����
        public short wAlarmInputCh;//��������ͨ����
        public short wAlarmOutputCh;//�������ͨ����
        public byte[] cReserved = new byte[244];
    }
    
    // ��Ƶ����ͨ����Ϣ
    public static class NET_VIDEO_INPUTS extends Structure {
        public int                       dwSize;
        public byte[]                    szChnName = new byte[64];                  // ͨ������
        public int                        bEnable;                                // ʹ��
        public byte[]                   szControlID = new byte[128];            // ����ID
        public byte[]                   szMainStreamUrl = new byte[MAX_PATH];   // ������url��ַ 
        public byte[]                   szExtraStreamUrl = new byte[MAX_PATH];  // ������url��ַ
        public int                      nOptionalMainUrlCount;                  // ������������ַ����
        public byte[]                   szOptionalMainUrls = new byte[8*MAX_PATH];  // ������������ַ�б�
        public int                      nOptionalExtraUrlCount;                 // ���ø�������ַ����
        public byte[]                   szOptionalExtraUrls = new byte[8*MAX_PATH]; // ���ø�������ַ�б�
        
        public NET_VIDEO_INPUTS()
        {
            this.dwSize = this.size();
        }
    }
    
    // Զ���豸��Ϣ
    public static class NET_REMOTE_DEVICE extends Structure {
        public int                       dwSize;
        public int                       bEnable;                          // ʹ��
        public byte[]                    szIp     =  new byte[16];         // IP
        public byte[]                    szUser = new byte[8];             // �û���, ����ʹ��szUserEx
        public byte[]                    szPwd     = new byte[8];          // ����, ����ʹ��szPwdEx
        public int                       nPort;                            // �˿�
        public int                       nDefinition;                      // ������, 0-����, 1-����
        public int                       emProtocol;                       // Э������  NET_DEVICE_PROTOCOL
        public byte[]                    szDevName = new byte[64];         // �豸����
        public int                       nVideoInputChannels;              // ��Ƶ����ͨ����
        public int                       nAudioInputChannels;              // ��Ƶ����ͨ����
        public byte[]                    szDevClass = new byte[32];        // �豸����, ��IPC, DVR, NVR��
        public byte[]                    szDevType = new byte[32];         // �豸�����ͺ�, ��IPC-HF3300
        public int                       nHttpPort;                        // Http�˿�
        public int                       nMaxVideoInputCount;              // ��Ƶ����ͨ�������
        public int                       nRetVideoInputCount;              // ����ʵ��ͨ������
        public Pointer                   pstuVideoInputs;                  // ��Ƶ����ͨ����Ϣ NET_VIDEO_INPUTS*
        public byte[]                    szMachineAddress = new byte[256]; // �豸�����
        public byte[]                    szSerialNo = new byte[48];        // �豸���к�
        public int                       nRtspPort;                        // Rtsp�˿�

        /*����������ƽ̨��չ*/
        public byte[]                    szUserEx = new byte[32];          // �û���
        public byte[]                    szPwdEx = new byte[32];           // ����
        
        public NET_REMOTE_DEVICE() 
        {
            this.dwSize = this.size();
        }
    }
    
    // ���õ���ʾԴ��Ϣ
    public static class NET_MATRIX_CAMERA_INFO extends Structure 
    {
        public int                      dwSize;
        public byte                     szName[] = new byte[128];          // ����
        public byte                     szDevID[] = new byte[128];         // �豸ID
        public byte                     szszControlID[] = new byte[128];   // ����ID
        public int                      nChannelID;                        // ͨ����, DeviceID�豸��Ψһ
        public int                      nUniqueChannel;                    // �豸��ͳһ��ŵ�Ψһͨ����
        public int                      bRemoteDevice;                     // �Ƿ�Զ���豸
        public NET_REMOTE_DEVICE        stuRemoteDevice;                   // Զ���豸��Ϣ
        public int                      emStreamType;                      // ��Ƶ��������  NET_STREAM_TYPE
        public int                      emChannelType;                     // ͨ������Ӧ NET_LOGIC_CHN_TYPE
               
        public NET_MATRIX_CAMERA_INFO()
        {
            this.dwSize = this.size();
            stuRemoteDevice = new NET_REMOTE_DEVICE();
        }
    }
    
    // CLIENT_MatrixGetCameras�ӿڵ��������
    public static class NET_IN_MATRIX_GET_CAMERAS extends Structure {
        public int dwSize; 
        
        public NET_IN_MATRIX_GET_CAMERAS() {
            this.dwSize = this.size();
        }
    }
        
    // CLIENT_MatrixGetCameras�ӿڵ��������
    public static class NET_OUT_MATRIX_GET_CAMERAS extends Structure {
        public int                        dwSize;                    
        public Pointer                    pstuCameras;            // ��ʾԴ��Ϣ����, �û������ڴ� NET_MATRIX_CAMERA_INFO
        public int                        nMaxCameraCount;        // ��ʾԴ�����С
        public int                        nRetCameraCount;        // ���ص���ʾԴ����
        
        public NET_OUT_MATRIX_GET_CAMERAS() {
            this.dwSize = this.size();
        }
       
        public NET_OUT_MATRIX_GET_CAMERAS(int nMaxCameraCount) {
            this.dwSize = this.size();
            this.nMaxCameraCount = nMaxCameraCount;
        
            NET_MATRIX_CAMERA_INFO CameraInfo = new NET_MATRIX_CAMERA_INFO();            
            Memory mem = new Memory(this.nMaxCameraCount * CameraInfo.size());
            mem.clear();
            pstuCameras = mem;
            
            long offset = 0;
            for (int i = 0; i < this.nMaxCameraCount; ++i) {
                NetSDKTools.SetStructDataToPointer(CameraInfo, this.pstuCameras, offset);
                offset += CameraInfo.size();
            }
        }
    }
    
    // CLIENT_SnapPictureToFile �ӿ��������
    public static class NET_IN_SNAP_PIC_TO_FILE_PARAM extends Structure {
        public int                         dwSize;                    // �ṹ���С
        public SNAP_PARAMS                 stuParam;                  // ץͼ����, ����mode�ֶν�һ����ץͼ, ��֧�ֶ�ʱ�����ץͼ; ���˳���DVR, �����豸��֧��ÿ��һ�ŵ�ץͼƵ��

        public byte[]                      szFilePath = new byte[MAX_PATH];// д���ļ��ĵ�ַ
        
        public NET_IN_SNAP_PIC_TO_FILE_PARAM() {
            this.dwSize = this.size();
            this.stuParam = new SNAP_PARAMS();
        }
    }
    
    //  CLIENT_SnapPictureToFile �ӿ��������
    public static class NET_OUT_SNAP_PIC_TO_FILE_PARAM extends Structure {
        public int                        dwSize;                    
        public Pointer                    szPicBuf;               // ͼƬ����,�û������ڴ�
        public int                        dwPicBufLen;            // ͼƬ�����ڴ��С, ��λ:�ֽ�
        public int                        dwPicBufRetLen;         // ���ص�ͼƬ��С, ��λ:�ֽ�
        
        public NET_OUT_SNAP_PIC_TO_FILE_PARAM() {
            this.dwSize = this.size();
        }
        
        public NET_OUT_SNAP_PIC_TO_FILE_PARAM(int nMaxBuf) {
            this.dwSize = this.size();
            this.dwPicBufLen = nMaxBuf;
            Memory mem = new Memory(nMaxBuf);
            mem.clear();
            this.szPicBuf = mem;
        }
    }
    
    // ¼���ļ���Ϣ
    public static class NET_RECORDFILE_INFO extends Structure {
        public int                        ch;                         // ͨ����
        public byte[]                     filename = new byte[124];   // �ļ���
        public int                        framenum;                   // �ļ���֡��
        public int                        size;                       // �ļ�����
        public NET_TIME                   starttime = new NET_TIME(); // ��ʼʱ��
        public NET_TIME                   endtime = new NET_TIME();   // ����ʱ��
        public int                        driveno;                    // ���̺�(��������¼��ͱ���¼�������,0��127��ʾ����¼��,����64��ʾ����1,128��ʾ����¼��)
        public int                        startcluster;               // ��ʼ�غ�
        public byte                       nRecordFileType;            // ¼���ļ�����  0����ͨ¼��1������¼��2���ƶ���⣻3������¼��4��ͼƬ, 5: ����¼��,255:����¼��
        public byte                       bImportantRecID;            // 0:��ͨ¼�� 1:��Ҫ¼��
        public byte                       bHint;                      // �ļ���λ����(nRecordFileType==4<ͼƬ>ʱ,bImportantRecID<<8 +bHint ,���ͼƬ��λ���� )
        public byte                       bRecType;                  // 0-������¼�� 1-����1��¼�� 2-������2 3-������3¼��
        
        public static class ByValue extends NET_RECORDFILE_INFO implements Structure.ByValue { }
        public static class ByReference extends NET_RECORDFILE_INFO implements Structure.ByReference { }
    }
    
    // ¼���ѯ����
    public static class EM_QUERY_RECORD_TYPE extends Structure {
        public static final int            EM_RECORD_TYPE_ALL              = 0;            // ����¼��
        public static final int            EM_RECORD_TYPE_ALARM            = 1;            // �ⲿ����¼��
        public static final int            EM_RECORD_TYPE_MOTION_DETECT    = 2;            // ��̬��ⱨ��¼��
        public static final int            EM_RECORD_TYPE_ALARM_ALL        = 3;            // ���б���¼��
        public static final int            EM_RECORD_TYPE_CARD             = 4;            // ���Ų�ѯ
        public static final int            EM_RECORD_TYPE_CONDITION        = 5;            // ��������ѯ
        public static final int            EM_RECORD_TYPE_JOIN             = 6;            // ��ϲ�ѯ
        public static final int            EM_RECORD_TYPE_CARD_PICTURE     = 8;            // �����Ų�ѯͼƬ,HB-U��NVS��ʹ��
        public static final int            EM_RECORD_TYPE_PICTURE          = 9;            // ��ѯͼƬ,HB-U��NVS��ʹ��
        public static final int            EM_RECORD_TYPE_FIELD            = 10;           // ���ֶβ�ѯ
        public static final int            EM_RECORD_TYPE_INTELLI_VIDEO    = 11;           // ����¼���ѯ
        public static final int            EM_RECORD_TYPE_NET_DATA         = 15;           // ��ѯ��������,�������ɵ�ʹ��
        public static final int            EM_RECORD_TYPE_TRANS_DATA       = 16;           // ��ѯ͸����������¼��
        public static final int            EM_RECORD_TYPE_IMPORTANT        = 17;           // ��ѯ��Ҫ¼��
        public static final int            EM_RECORD_TYPE_TALK_DATA        = 18;           // ��ѯ¼���ļ�
        
        public static final int            EM_RECORD_TYPE_INVALID          = 256;          // ��Ч�Ĳ�ѯ����
    }
    
    // ��������
    public static class NET_LANGUAGE_TYPE extends Structure
    {
        public static final int NET_LANGUAGE_ENGLISH = 0; //Ӣ��
        public static final int NET_LANGUAGE_CHINESE_SIMPLIFIED = NET_LANGUAGE_ENGLISH+1; //��������
        public static final int NET_LANGUAGE_CHINESE_TRADITIONAL = NET_LANGUAGE_CHINESE_SIMPLIFIED+1; //��������
        public static final int NET_LANGUAGE_ITALIAN = NET_LANGUAGE_CHINESE_TRADITIONAL+1; //�������
        public static final int NET_LANGUAGE_SPANISH = NET_LANGUAGE_ITALIAN+1; //��������
        public static final int NET_LANGUAGE_JAPANESE = NET_LANGUAGE_SPANISH+1; //���İ�
        public static final int NET_LANGUAGE_RUSSIAN = NET_LANGUAGE_JAPANESE+1; //���İ�
        public static final int NET_LANGUAGE_FRENCH = NET_LANGUAGE_RUSSIAN+1; //���İ�
        public static final int NET_LANGUAGE_GERMAN = NET_LANGUAGE_FRENCH+1; //���İ�
        public static final int NET_LANGUAGE_PORTUGUESE = NET_LANGUAGE_GERMAN+1; //��������
        public static final int NET_LANGUAGE_TURKEY = NET_LANGUAGE_PORTUGUESE+1; //��������
        public static final int NET_LANGUAGE_POLISH = NET_LANGUAGE_TURKEY+1; //������
        public static final int NET_LANGUAGE_ROMANIAN = NET_LANGUAGE_POLISH+1; //��������
        public static final int NET_LANGUAGE_HUNGARIAN = NET_LANGUAGE_ROMANIAN+1; //��������
        public static final int NET_LANGUAGE_FINNISH = NET_LANGUAGE_HUNGARIAN+1; //������
        public static final int NET_LANGUAGE_ESTONIAN = NET_LANGUAGE_FINNISH+1; //��ɳ������
        public static final int NET_LANGUAGE_KOREAN = NET_LANGUAGE_ESTONIAN+1; //����
        public static final int NET_LANGUAGE_FARSI = NET_LANGUAGE_KOREAN+1; //��˹��
        public static final int NET_LANGUAGE_DANSK = NET_LANGUAGE_FARSI+1; //������
        public static final int NET_LANGUAGE_CZECHISH = NET_LANGUAGE_DANSK+1; //�ݿ���
        public static final int NET_LANGUAGE_BULGARIA = NET_LANGUAGE_CZECHISH+1; //����������
        public static final int NET_LANGUAGE_SLOVAKIAN = NET_LANGUAGE_BULGARIA+1; //˹�工����
        public static final int NET_LANGUAGE_SLOVENIA = NET_LANGUAGE_SLOVAKIAN+1; //˹����������
        public static final int NET_LANGUAGE_CROATIAN = NET_LANGUAGE_SLOVENIA+1; //���޵�����
        public static final int NET_LANGUAGE_DUTCH = NET_LANGUAGE_CROATIAN+1; //������
        public static final int NET_LANGUAGE_GREEK = NET_LANGUAGE_DUTCH+1; //ϣ����
        public static final int NET_LANGUAGE_UKRAINIAN = NET_LANGUAGE_GREEK+1; //�ڿ�����
        public static final int NET_LANGUAGE_SWEDISH = NET_LANGUAGE_UKRAINIAN+1; //�����
        public static final int NET_LANGUAGE_SERBIAN = NET_LANGUAGE_SWEDISH+1; //����ά����
        public static final int NET_LANGUAGE_VIETNAMESE = NET_LANGUAGE_SERBIAN+1; //Խ����
        public static final int NET_LANGUAGE_LITHUANIAN = NET_LANGUAGE_VIETNAMESE+1; //��������
        public static final int NET_LANGUAGE_FILIPINO = NET_LANGUAGE_LITHUANIAN+1; //���ɱ���
        public static final int NET_LANGUAGE_ARABIC = NET_LANGUAGE_FILIPINO+1; //��������
        public static final int NET_LANGUAGE_CATALAN = NET_LANGUAGE_ARABIC+1; //��̩��������
        public static final int NET_LANGUAGE_LATVIAN = NET_LANGUAGE_CATALAN+1; //����ά����
        public static final int NET_LANGUAGE_THAI = NET_LANGUAGE_LATVIAN+1; //̩��
        public static final int NET_LANGUAGE_HEBREW = NET_LANGUAGE_THAI+1; //ϣ������
        public static final int NET_LANGUAGE_Bosnian = NET_LANGUAGE_HEBREW+1; //��˹������
    }
    
    // ������Ϣ
    public static class CFG_RECT extends Structure
    {
        public int nLeft;
        public int nTop;
        public int nRight;
        public int nBottom;
    }

    // ��Ƶ����ҹ����������ѡ������Ϲ��߽ϰ�ʱ�Զ��л���ҹ������ò���
    public static class CFG_VIDEO_IN_NIGHT_OPTIONS extends Structure
    {
        public byte bySwitchMode;//�ѷ���,ʹ��CFG_VIDEO_IN_OPTIONS�����bySwitchMode
        //0-���л�������ʹ�ð������ã�1-���������л���2-����ʱ���л���3-���л�������ʹ��ҹ�����ã�4-ʹ����ͨ����
        public byte byProfile;//��ǰʹ�õ������ļ�.
                              // 0-����
                              // 1-����
                              // 2-Normal
                              // 0��1,2��Ϊ��ʱ���ã�ʹͼ����Ч�����ڲ鿴ͼ�����Ч���������ȷ�����뿪ҳ�治�������豸��
                              ///3-����ʱ���ã����ȷ���󱣴����豸����SwitchMode���ʹ�ã�����SwitchMode����������Ч�����á�
                              // SwitchMode=0��Profile=3�����ð������õ��豸��
                              // SwitchMode=1��Profile=3��������ҹ�����õ��豸
                              // SwitchMode=2��Profile=3�������ճ�����ʱ����л�������ʱ���ʹ�ð������ã�ҹ��ʱ���ʹ��ҹ�����ã��������豸��
                              // SwitchMode=4��Profile=3��ʹ����ͨ���ã��������豸
        public byte byBrightnessThreshold;//������ֵ0~100
        public byte bySunriseHour;//�����ճ�������ʱ�䣬����֮���ճ�֮ǰ��������ҹ����������á�
        public byte bySunriseMinute;//00:00:00 ~ 23:59:59
        public byte bySunriseSecond;
        public byte bySunsetHour;
        public byte bySunsetMinute;
        public byte bySunsetSecond;
        public byte byGainRed;//��ɫ������ڣ���ƽ��Ϊ"Custom"ģʽ����Ч0~100
        public byte byGainBlue;//��ɫ������ڣ���ƽ��Ϊ"Custom"ģʽ����Ч0~100
        public byte byGainGreen;//��ɫ������ڣ���ƽ��Ϊ"Custom"ģʽ����Ч0~100
        public byte byExposure;//�ع�ģʽ��ȡֵ��Χȡ�����豸��������0-�Զ��ع⣬1-�ع�ȼ�1��2-�ع�ȼ�2��n-1����ع�ȼ���n��ʱ�������޵��Զ��ع�n+1�Զ���ʱ���ֶ��ع� (n==byExposureEn��
        public float fExposureValue1;//�Զ��ع�ʱ�����޻����ֶ��ع��Զ���ʱ��,����Ϊ��λ��ȡֵ0.1ms~80ms
        public float fExposureValue2;//�Զ��ع�ʱ������,����Ϊ��λ��ȡֵ0.1ms~80ms
        public byte byWhiteBalance;//��ƽ��,0-"Disable", 1-"Auto", 2-"Custom", 3-"Sunny", 4-"Cloudy", 5-"Home", 6-"Office", 7-"Night", 8-"HighColorTemperature", 9-"LowColorTemperature", 10-"AutoColorTemperature", 11-"CustomColorTemperature"
        public byte byGain;//0~100,GainAutoΪtrueʱ��ʾ�Զ���������ޣ������ʾ�̶�������ֵ
        public byte bGainAuto;//�Զ�����, ����Ϊbool, ȡֵ0��1
        public byte bIrisAuto;//�Զ���Ȧ, ����Ϊbool, ȡֵ0��1
        public float fExternalSyncPhase;//��ͬ������λ����0~360
        public byte byGainMin;//��������
        public byte byGainMax;//��������
        public byte byBacklight;//���ⲹ����ȡֵ��Χȡ�����豸��������0-�ر�1-����2-ָ�����򱳹ⲹ��
        public byte byAntiFlicker;//����˸ģʽ0-Outdoor1-50Hz����˸ 2-60Hz����˸
        public byte byDayNightColor;//��/ҹģʽ��0-���ǲ�ɫ��1-���������Զ��л���2-���Ǻڰ�
        public byte byExposureMode;//�ع�ģʽ�����ع�ȼ�Ϊ�Զ��ع�ʱ��Ч��ȡֵ��0-Ĭ���Զ���1-�������ȣ�2-��������
        public byte byRotate90;//0-����ת��1-˳ʱ��90�㣬2-��ʱ��90��
        public byte bMirror;//����, ����Ϊbool, ȡֵ0��1
        public byte byWideDynamicRange;//��ֵ̬0-�رգ�1~100-Ϊ��ʵ��Χֵ
        public byte byGlareInhibition;//ǿ������0-�رգ�1~100Ϊ��Χֵ
        public CFG_RECT stuBacklightRegion = new CFG_RECT();//���ⲹ������
        public byte byFocusMode;//0-�رգ�1-�����۽���2-�Զ��۽�
        public byte bFlip;//��ת, ����Ϊbool, ȡֵ0��1
        public byte[] reserved = new byte[74];//����
    }

    // ���������
    public static class CFG_FLASH_CONTROL extends Structure
    {
        public byte byMode;//����ģʽ��0-��ֹ���⣬1-ʼ�����⣬2-�Զ�����
        public byte byValue;//����ֵ,0-0us,1-64us, 2-128us, 3-192...15-960us
        public byte byPole;//����ģʽ,0-�͵�ƽ1-�ߵ�ƽ 2-������ 3-�½���
        public byte byPreValue;//����Ԥ��ֵ����0~100
        public byte byDutyCycle;//ռ�ձ�,0~100
        public byte byFreqMultiple;//��Ƶ,0~10
        public byte[] reserved = new byte[122];//����
    }

    // ץ�Ĳ�����������
    public static class CFG_VIDEO_IN_SNAPSHOT_OPTIONS extends Structure
    {
        public byte byGainRed;//��ɫ������ڣ���ƽ��Ϊ"Custom"ģʽ����Ч0~100
        public byte byGainBlue;//��ɫ������ڣ���ƽ��Ϊ"Custom"ģʽ����Ч0~100
        public byte byGainGreen;//��ɫ������ڣ���ƽ��Ϊ"Custom"ģʽ����Ч0~100
        public byte byExposure;//�ع�ģʽ��ȡֵ��Χȡ�����豸��������0-�Զ��ع⣬1-�ع�ȼ�1��2-�ع�ȼ�2��n-1����ع�ȼ���n��ʱ�������޵��Զ��ع�n+1�Զ���ʱ���ֶ��ع� (n==byExposureEn��
        public float fExposureValue1;//�Զ��ع�ʱ�����޻����ֶ��ع��Զ���ʱ��,����Ϊ��λ��ȡֵ0.1ms~80ms
        public float fExposureValue2;//�Զ��ع�ʱ������,����Ϊ��λ��ȡֵ0.1ms~80ms  
        public byte byWhiteBalance;//��ƽ��,0-"Disable", 1-"Auto", 2-"Custom", 3-"Sunny", 4-"Cloudy", 5-"Home", 6-"Office", 7-"Night", 8-"HighColorTemperature", 9-"LowColorTemperature", 10-"AutoColorTemperature", 11-"CustomColorTemperature"
        public byte byColorTemperature;//ɫ�µȼ�,��ƽ��Ϊ"CustomColorTemperature"ģʽ����Ч
        public byte bGainAuto;//�Զ�����, ����Ϊbool, ȡֵ0��1
        public byte byGain;//�������,GainAutoΪtrueʱ��ʾ�Զ���������ޣ������ʾ�̶�������ֵ
        public byte[] reversed = new byte[112];//����
    }

    // ���۾�ͷ����
    public static class CFG_FISH_EYE extends Structure
    {
        public CFG_POLYGON stuCenterPoint;//����Բ������,��Χ[0,8192]
        public int nRadius;//���۰뾶��С,��Χ[0,8192], ����Ϊunsigned int
        public float fDirection;//��ͷ��ת����,��ת�Ƕ�[0,360.0]
        public byte byPlaceHolder;//��ͷ��װ��ʽ1��װ��2��װ��3��װ,Ĭ��1
        public byte byCalibrateMode;//���۽���ģʽ,���CFG_CALIBRATE_MODEö��ֵ
        public byte[] reversed = new byte[31];//����
    }

    public static class CFG_VIDEO_IN_NORMAL_OPTIONS extends Structure
    {
        public byte byGainRed;//��ɫ������ڣ���ƽ��Ϊ"Custom"ģʽ����Ч0~100
        public byte byGainBlue;//��ɫ������ڣ���ƽ��Ϊ"Custom"ģʽ����Ч0~100
        public byte byGainGreen;//��ɫ������ڣ���ƽ��Ϊ"Custom"ģʽ����Ч0~100
        public byte byExposure;//�ع�ģʽ��ȡֵ��Χȡ�����豸��������0-�Զ��ع⣬1-�ع�ȼ�1��2-�ع�ȼ�2��n-1����ع�ȼ���n��ʱ�������޵��Զ��ع�n+1�Զ���ʱ���ֶ��ع� (n==byExposureEn��
        public float fExposureValue1;//�Զ��ع�ʱ�����޻����ֶ��ع��Զ���ʱ��,����Ϊ��λ��ȡֵ0.1ms~80ms
        public float fExposureValue2;//�Զ��ع�ʱ������,����Ϊ��λ��ȡֵ0.1ms~80ms
        public byte byWhiteBalance;//��ƽ��,0-"Disable", 1-"Auto", 2-"Custom", 3-"Sunny", 4-"Cloudy", 5-"Home", 6-"Office", 7-"Night", 8-"HighColorTemperature", 9-"LowColorTemperature", 10-"AutoColorTemperature", 11-"CustomColorTemperature"
        public byte byGain;//0~100,GainAutoΪtrueʱ��ʾ�Զ���������ޣ������ʾ�̶�������ֵ
        public byte bGainAuto;//�Զ�����, ����Ϊbool, ȡֵ0��1
        public byte bIrisAuto;//�Զ���Ȧ, ����Ϊbool, ȡֵ0��1
        public float fExternalSyncPhase;//��ͬ������λ����0~360
        public byte byGainMin;//��������
        public byte byGainMax;//��������
        public byte byBacklight;//���ⲹ����ȡֵ��Χȡ�����豸��������0-�ر�1-����2-ָ�����򱳹ⲹ��
        public byte byAntiFlicker;//����˸ģʽ0-Outdoor1-50Hz����˸ 2-60Hz����˸
        public byte byDayNightColor;//��/ҹģʽ��0-���ǲ�ɫ��1-���������Զ��л���2-���Ǻڰ�
        public byte byExposureMode;//�ع�ģʽ�����ع�ȼ�Ϊ�Զ��ع�ʱ��Ч��ȡֵ��0-Ĭ���Զ���1-�������ȣ�2-��������
        public byte byRotate90;//0-����ת��1-˳ʱ��90�㣬2-��ʱ��90��
        public byte bMirror;//����, ����Ϊbool, ȡֵ0��1
        public byte byWideDynamicRange;//��ֵ̬0-�رգ�1~100-Ϊ��ʵ��Χֵ
        public byte byGlareInhibition;//ǿ������0-�رգ�1~100Ϊ��Χֵ
        public CFG_RECT stuBacklightRegion;//���ⲹ������
        public byte byFocusMode;//0-�رգ�1-�����۽���2-�Զ��۽�
        public byte bFlip;//��ת, ����Ϊbool, ȡֵ0��1
        public byte[] reserved = new byte[74];//����
    }

    // ��Ƶ����ǰ��ѡ��
    public static class CFG_VIDEO_IN_OPTIONS extends Structure
    {
        public byte byBacklight;//���ⲹ����ȡֵ��Χȡ�����豸��������0-�ر�1-����2-ָ�����򱳹ⲹ��
        public byte byDayNightColor;//��/ҹģʽ��0-���ǲ�ɫ��1-���������Զ��л���2-���Ǻڰ�
        public byte byWhiteBalance;//��ƽ��,0-"Disable", 1-"Auto", 2-"Custom", 3-"Sunny", 4-"Cloudy", 5-"Home", 6-"Office", 7-"Night", 8-"HighColorTemperature", 9-"LowColorTemperature", 10-"AutoColorTemperature", 11-"CustomColorTemperature"
        public byte byColorTemperature;//ɫ�µȼ�,��ƽ��Ϊ"CustomColorTemperature"ģʽ����Ч
        public byte bMirror;//����, ����Ϊbool, ȡֵ0��1
        public byte bFlip;//��ת, ����Ϊbool, ȡֵ0��1
        public byte bIrisAuto;//�Զ���Ȧ, ����Ϊbool, ȡֵ0��1
        public byte bInfraRed;//���ݻ������Զ��������ⲹ����, ����Ϊbool, ȡֵ0��1
        public byte byGainRed;//��ɫ������ڣ���ƽ��Ϊ"Custom"ģʽ����Ч0~100
        public byte byGainBlue;//��ɫ������ڣ���ƽ��Ϊ"Custom"ģʽ����Ч0~100
        public byte byGainGreen;//��ɫ������ڣ���ƽ��Ϊ"Custom"ģʽ����Ч0~100
        public byte byExposure;//�ع�ģʽ��ȡֵ��Χȡ�����豸��������0-�Զ��ع⣬1-�ع�ȼ�1��2-�ع�ȼ�2��n-1����ع�ȼ���n��ʱ�������޵��Զ��ع�n+1�Զ���ʱ���ֶ��ع� (n==byExposureEn��
        public float fExposureValue1;//�Զ��ع�ʱ�����޻����ֶ��ع��Զ���ʱ��,����Ϊ��λ��ȡֵ0.1ms~80ms
        public float fExposureValue2;//�Զ��ع�ʱ������,����Ϊ��λ��ȡֵ0.1ms~80ms
        public byte bGainAuto;//�Զ�����, ����Ϊbool, ȡֵ0��1
        public byte byGain;//�������,GainAutoΪtrueʱ��ʾ�Զ���������ޣ������ʾ�̶�������ֵ
        public byte bySignalFormat;//�źŸ�ʽ,0-Inside(�ڲ�����)1-BT656 2-720p 3-1080p  4-1080i  5-1080sF
        public byte byRotate90;//0-����ת��1-˳ʱ��90�㣬2-��ʱ��90��
        public float fExternalSyncPhase;//��ͬ������λ���� 0~360   
        public byte byExternalSync;//�ⲿͬ���ź�����,0-�ڲ�ͬ�� 1-�ⲿͬ��
        public byte bySwitchMode;//0-���л�������ʹ�ð������ã�1-���������л���2-����ʱ���л���3-���л�������ʹ��ҹ�����ã�4-ʹ����ͨ����
        public byte byDoubleExposure;//˫����,0-�����ã�1-˫����ȫ֡�ʣ���ͼ�����Ƶֻ�п��Ų�����ͬ��2-˫���Ű�֡�ʣ���ͼ�����Ƶ���ż���ƽ���������ͬ
        public byte byWideDynamicRange;//��ֵ̬
        public CFG_VIDEO_IN_NIGHT_OPTIONS stuNightOptions;//ҹ�����
        public CFG_FLASH_CONTROL stuFlash;//���������
        public CFG_VIDEO_IN_SNAPSHOT_OPTIONS stuSnapshot;//ץ�Ĳ���,˫����ʱ��Ч
        public CFG_FISH_EYE stuFishEye;//���۾�ͷ
        public byte byFocusMode;//0-�رգ�1-�����۽���2-�Զ��۽�
        public byte[] reserved = new byte[28];//����
        public byte byGainMin;//��������
        public byte byGainMax;//��������
        public byte byAntiFlicker;//����˸ģʽ 0-Outdoor 1-50Hz����˸ 2-60Hz����˸
        public byte byExposureMode;//�ع�ģʽ�����ع�ȼ�Ϊ�Զ��ع�ʱ��Ч��ȡֵ��0-Ĭ���Զ���1-�������ȣ�2-��������,4-�ֶ�
        public byte byGlareInhibition;//ǿ������0-�رգ�1~100Ϊ��Χֵ
        public CFG_RECT stuBacklightRegion;//���ⲹ������
        public CFG_VIDEO_IN_NORMAL_OPTIONS stuNormalOptions;//��ͨ����
    }
    
    // ͨ����̨��������
    public static class NET_PTZ_ControlType extends Structure
    {
        public static final int NET_PTZ_UP_CONTROL = 0;//��
        public static final int NET_PTZ_DOWN_CONTROL = NET_PTZ_UP_CONTROL+1; //��
        public static final int NET_PTZ_LEFT_CONTROL = NET_PTZ_DOWN_CONTROL+1; //��
        public static final int NET_PTZ_RIGHT_CONTROL = NET_PTZ_LEFT_CONTROL+1; //��
        public static final int NET_PTZ_ZOOM_ADD_CONTROL = NET_PTZ_RIGHT_CONTROL+1; //�䱶+
        public static final int NET_PTZ_ZOOM_DEC_CONTROL = NET_PTZ_ZOOM_ADD_CONTROL+1; //�䱶-
        public static final int NET_PTZ_FOCUS_ADD_CONTROL = NET_PTZ_ZOOM_DEC_CONTROL+1; //����+
        public static final int NET_PTZ_FOCUS_DEC_CONTROL = NET_PTZ_FOCUS_ADD_CONTROL+1; //����-
        public static final int NET_PTZ_APERTURE_ADD_CONTROL = NET_PTZ_FOCUS_DEC_CONTROL+1; //��Ȧ+
        public static final int NET_PTZ_APERTURE_DEC_CONTROL = NET_PTZ_APERTURE_ADD_CONTROL+1; //��Ȧ-
        public static final int NET_PTZ_POINT_MOVE_CONTROL = NET_PTZ_APERTURE_DEC_CONTROL+1; //ת��Ԥ�õ�
        public static final int NET_PTZ_POINT_SET_CONTROL = NET_PTZ_POINT_MOVE_CONTROL+1; //����
        public static final int NET_PTZ_POINT_DEL_CONTROL = NET_PTZ_POINT_SET_CONTROL+1; //ɾ��
        public static final int NET_PTZ_POINT_LOOP_CONTROL = NET_PTZ_POINT_DEL_CONTROL+1; //���Ѳ��
        public static final int NET_PTZ_LAMP_CONTROL = NET_PTZ_POINT_LOOP_CONTROL+1; //�ƹ���ˢ
    }

    // ��̨������չ����
    public static class NET_EXTPTZ_ControlType extends Structure
    {
        public static final int NET_EXTPTZ_LEFTTOP = 0x20;//����
        public static final int NET_EXTPTZ_RIGHTTOP = NET_EXTPTZ_LEFTTOP+1; //����
        public static final int NET_EXTPTZ_LEFTDOWN = NET_EXTPTZ_RIGHTTOP+1; //����
        public static final int NET_EXTPTZ_RIGHTDOWN = NET_EXTPTZ_LEFTDOWN+1; //����
        public static final int NET_EXTPTZ_ADDTOLOOP = NET_EXTPTZ_RIGHTDOWN+1; //����Ԥ�õ㵽Ѳ��Ѳ����·Ԥ�õ�ֵ
        public static final int NET_EXTPTZ_DELFROMLOOP = NET_EXTPTZ_ADDTOLOOP+1; //ɾ��Ѳ����Ԥ�õ�Ѳ����·Ԥ�õ�ֵ
        public static final int NET_EXTPTZ_CLOSELOOP = NET_EXTPTZ_DELFROMLOOP+1; //���Ѳ��Ѳ����·
        public static final int NET_EXTPTZ_STARTPANCRUISE = NET_EXTPTZ_CLOSELOOP+1; //��ʼˮƽ��ת
        public static final int NET_EXTPTZ_STOPPANCRUISE = NET_EXTPTZ_STARTPANCRUISE+1; //ֹͣˮƽ��ת
        public static final int NET_EXTPTZ_SETLEFTBORDER = NET_EXTPTZ_STOPPANCRUISE+1; //������߽�
        public static final int NET_EXTPTZ_SETRIGHTBORDER = NET_EXTPTZ_SETLEFTBORDER+1; //�����ұ߽�
        public static final int NET_EXTPTZ_STARTLINESCAN = NET_EXTPTZ_SETRIGHTBORDER+1; //��ʼ��ɨ
        public static final int NET_EXTPTZ_CLOSELINESCAN = NET_EXTPTZ_STARTLINESCAN+1; //ֹͣ��ɨ
        public static final int NET_EXTPTZ_SETMODESTART = NET_EXTPTZ_CLOSELINESCAN+1; //����ģʽ��ʼģʽ��·
        public static final int NET_EXTPTZ_SETMODESTOP = NET_EXTPTZ_SETMODESTART+1; //����ģʽ����ģʽ��·
        public static final int NET_EXTPTZ_RUNMODE = NET_EXTPTZ_SETMODESTOP+1; //����ģʽģʽ��·
        public static final int NET_EXTPTZ_STOPMODE = NET_EXTPTZ_RUNMODE+1; //ֹͣģʽģʽ��·
        public static final int NET_EXTPTZ_DELETEMODE = NET_EXTPTZ_STOPMODE+1; //���ģʽģʽ��·
        public static final int NET_EXTPTZ_REVERSECOMM = NET_EXTPTZ_DELETEMODE+1; //��ת����
        public static final int NET_EXTPTZ_FASTGOTO = NET_EXTPTZ_REVERSECOMM+1; //���ٶ�λˮƽ����(8192)��ֱ����(8192)�䱶(4)
        public static final int NET_EXTPTZ_AUXIOPEN = NET_EXTPTZ_FASTGOTO+1; //�������ؿ�������
        public static final int NET_EXTPTZ_AUXICLOSE = NET_EXTPTZ_AUXIOPEN+1; //�������عظ�����
        public static final int NET_EXTPTZ_OPENMENU = 0x36;//������˵�
        public static final int NET_EXTPTZ_CLOSEMENU = NET_EXTPTZ_OPENMENU+1; //�رղ˵�
        public static final int NET_EXTPTZ_MENUOK = NET_EXTPTZ_CLOSEMENU+1; //�˵�ȷ��
        public static final int NET_EXTPTZ_MENUCANCEL = NET_EXTPTZ_MENUOK+1; //�˵�ȡ��
        public static final int NET_EXTPTZ_MENUUP = NET_EXTPTZ_MENUCANCEL+1; //�˵���
        public static final int NET_EXTPTZ_MENUDOWN = NET_EXTPTZ_MENUUP+1; //�˵���
        public static final int NET_EXTPTZ_MENULEFT = NET_EXTPTZ_MENUDOWN+1; //�˵���
        public static final int NET_EXTPTZ_MENURIGHT = NET_EXTPTZ_MENULEFT+1; //�˵���
        public static final int NET_EXTPTZ_ALARMHANDLE = 0x40;//����������̨parm1����������ͨ����parm2��������������1-Ԥ�õ�2-��ɨ3-Ѳ����parm3������ֵ����Ԥ�õ��
        public static final int NET_EXTPTZ_MATRIXSWITCH = 0x41;//�����л�parm1����������(��Ƶ�����)��parm2����Ƶ����ţ�parm3�������
        public static final int NET_EXTPTZ_LIGHTCONTROL= NET_EXTPTZ_MATRIXSWITCH+1; //�ƹ������
        public static final int NET_EXTPTZ_EXACTGOTO = NET_EXTPTZ_LIGHTCONTROL+1; //��ά��ȷ��λparm1��ˮƽ�Ƕ�(0~3600)��parm2����ֱ����(0~900)��parm3���䱶(1~128)
        public static final int NET_EXTPTZ_RESETZERO = NET_EXTPTZ_EXACTGOTO+1; //��ά��λ������λ
        public static final int NET_EXTPTZ_MOVE_ABSOLUTELY = NET_EXTPTZ_RESETZERO+1; //�����ƶ��������param4��Ӧ�ṹPTZ_CONTROL_ABSOLUTELY
        public static final int NET_EXTPTZ_MOVE_CONTINUOUSLY = NET_EXTPTZ_MOVE_ABSOLUTELY+1; //�����ƶ��������param4��Ӧ�ṹPTZ_CONTROL_CONTINUOUSLY
        public static final int NET_EXTPTZ_GOTOPRESET = NET_EXTPTZ_MOVE_CONTINUOUSLY+1; //��̨���������һ���ٶ�ת��Ԥ��λ�㣬parm4��Ӧ�ṹPTZ_CONTROL_GOTOPRESET
        public static final int NET_EXTPTZ_SET_VIEW_RANGE = 0x49;//���ÿ�����(param4��Ӧ�ṹPTZ_VIEW_RANGE_INFO)
        public static final int NET_EXTPTZ_FOCUS_ABSOLUTELY = 0x4A;//���Ծ۽�(param4��Ӧ�ṹPTZ_FOCUS_ABSOLUTELY)
        public static final int NET_EXTPTZ_HORSECTORSCAN = 0x4B;//ˮƽ��ɨ(param4��ӦPTZ_CONTROL_SECTORSCAN,param1��param2��param3��Ч)
        public static final int NET_EXTPTZ_VERSECTORSCAN = 0x4C;//��ֱ��ɨ(param4��ӦPTZ_CONTROL_SECTORSCAN,param1��param2��param3��Ч)
        public static final int NET_EXTPTZ_SET_ABS_ZOOMFOCUS = 0x4D;//�趨���Խ��ࡢ�۽�ֵ,param1Ϊ����,��Χ:0,255],param2Ϊ�۽�,��Χ:[0,255],param3��param4��Ч
        public static final int NET_EXTPTZ_SET_FISHEYE_EPTZ = 0x4E;//�������۵�����̨��param4��Ӧ�ṹPTZ_CONTROL_SET_FISHEYE_EPTZ
        public static final int NET_EXTPTZ_UP_TELE = 0x70;    //�� + TELE param1=�ٶ�(1-8)����ͬ
        public static final int NET_EXTPTZ_DOWN_TELE = NET_EXTPTZ_UP_TELE+1; //�� + TELE
        public static final int NET_EXTPTZ_LEFT_TELE = NET_EXTPTZ_DOWN_TELE+1; //�� + TELE
        public static final int NET_EXTPTZ_RIGHT_TELE = NET_EXTPTZ_LEFT_TELE+1; //�� + TELE
        public static final int NET_EXTPTZ_LEFTUP_TELE = NET_EXTPTZ_RIGHT_TELE+1; //���� + TELE
        public static final int NET_EXTPTZ_LEFTDOWN_TELE = NET_EXTPTZ_LEFTUP_TELE+1; //���� + TELE
        public static final int NET_EXTPTZ_TIGHTUP_TELE = NET_EXTPTZ_LEFTDOWN_TELE+1; //���� + TELE
        public static final int NET_EXTPTZ_RIGHTDOWN_TELE = NET_EXTPTZ_TIGHTUP_TELE+1; //���� + TELE
        public static final int NET_EXTPTZ_UP_WIDE = NET_EXTPTZ_RIGHTDOWN_TELE+1; // �� + WIDEparam1=�ٶ�(1-8)����ͬ
        public static final int NET_EXTPTZ_DOWN_WIDE = NET_EXTPTZ_UP_WIDE+1; //�� + WIDE
        public static final int NET_EXTPTZ_LEFT_WIDE = NET_EXTPTZ_DOWN_WIDE+1; //�� + WIDE
        public static final int NET_EXTPTZ_RIGHT_WIDE = NET_EXTPTZ_LEFT_WIDE+1; //�� + WIDE
        public static final int NET_EXTPTZ_LEFTUP_WIDE = NET_EXTPTZ_RIGHT_WIDE+1; //���� + WIDE
        public static final int NET_EXTPTZ_LEFTDOWN_WIDE = NET_EXTPTZ_LEFTUP_WIDE+1; //���� + WIDE
        public static final int NET_EXTPTZ_TIGHTUP_WIDE = NET_EXTPTZ_LEFTDOWN_WIDE+1; //���� + WIDE
        public static final int NET_EXTPTZ_RIGHTDOWN_WIDE = NET_EXTPTZ_TIGHTUP_WIDE+1; //���� + WIDE
        public static final int NET_EXTPTZ_TOTAL = NET_EXTPTZ_RIGHTDOWN_WIDE+1; //�������ֵ
    }

    // ��ˢ����ģʽ
    public static class EM_CFG_RAINBRUSHMODE_MODE extends Structure
    {
        public static final int EM_CFG_RAINBRUSHMODE_MODE_UNKNOWN = 0; //δ֪
        public static final int EM_CFG_RAINBRUSHMODE_MODE_MANUAL = EM_CFG_RAINBRUSHMODE_MODE_UNKNOWN+1; //�ֶ�ģʽ
        public static final int EM_CFG_RAINBRUSHMODE_MODE_TIMING = EM_CFG_RAINBRUSHMODE_MODE_MANUAL+1; //��ʱģʽ
    }

    // ��ˢʹ�ܵ�ƽģʽ
    public static class EM_CFG_RAINBRUSHMODE_ENABLEMODE extends Structure
    {
        public static final int EM_CFG_RAINBRUSHMODE_ENABLEMODE_UNKNOWN = 0; //δ֪
        public static final int EM_CFG_RAINBRUSHMODE_ENABLEMODE_LOW = EM_CFG_RAINBRUSHMODE_ENABLEMODE_UNKNOWN+1; //�͵�ƽ��Ч�����գ�
        public static final int EM_CFG_RAINBRUSHMODE_ENABLEMODE_HIGH = EM_CFG_RAINBRUSHMODE_ENABLEMODE_LOW+1; //�ߵ�ƽ��Ч��������
    }

    // ��ˢģʽ�������(��Ӧ CFG_RAINBRUSHMODE_INFO ����)
    public static class CFG_RAINBRUSHMODE_INFO extends Structure
    {
        public int emMode;//��ˢ����ģʽ, ȡֵΪEM_CFG_RAINBRUSHMODE_MODE�е�ֵ
        public int emEnableMode;//��ˢʹ�ܵ�ƽģʽ, ȡֵΪEM_CFG_RAINBRUSHMODE_ENABLEMODE�е�ֵ
        public int nPort;//��ˢʹ�õ�IO�˿�,-1��ʾδ�����豸,-2��ʾ���ֶ���Ч���豸δ���͸��ֶΣ�
    }

    public static class CFG_RAINBRUSH_INFO extends Structure
    {
        public byte bEnable;//��ˢʹ��, ����Ϊbool, ȡֵ0��1
        public byte bSpeedRate;//��ˢ�ٶ�,1:����;2:����;3:����
        public byte[] bReserved = new byte[2];//��������
        public CFG_TIME_SECTION[] stuTimeSection = (CFG_TIME_SECTION[])new CFG_TIME_SECTION().toArray(WEEK_DAY_NUM*MAX_REC_TSECT);// �¼���Ӧʱ���
    }

    // �������ͣ���ӦCLIENT_ControlDevice�ӿ�
    public static class CtrlType extends Structure
    {
        public static final int CTRLTYPE_CTRL_REBOOT = 0;//�����豸
        public static final int CTRLTYPE_CTRL_SHUTDOWN = CTRLTYPE_CTRL_REBOOT+1; //�ر��豸
        public static final int CTRLTYPE_CTRL_DISK = CTRLTYPE_CTRL_SHUTDOWN+1; //Ӳ�̹���
        public static final int CTRLTYPE_KEYBOARD_POWER =3;//�������
        public static final int CTRLTYPE_KEYBOARD_ENTER = CTRLTYPE_KEYBOARD_POWER+1; 
        public static final int CTRLTYPE_KEYBOARD_ESC = CTRLTYPE_KEYBOARD_ENTER+1; 
        public static final int CTRLTYPE_KEYBOARD_UP = CTRLTYPE_KEYBOARD_ESC+1; 
        public static final int CTRLTYPE_KEYBOARD_DOWN = CTRLTYPE_KEYBOARD_UP+1; 
        public static final int CTRLTYPE_KEYBOARD_LEFT = CTRLTYPE_KEYBOARD_DOWN+1; 
        public static final int CTRLTYPE_KEYBOARD_RIGHT = CTRLTYPE_KEYBOARD_LEFT+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN0 = CTRLTYPE_KEYBOARD_RIGHT+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN1 = CTRLTYPE_KEYBOARD_BTN0+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN2 = CTRLTYPE_KEYBOARD_BTN1+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN3 = CTRLTYPE_KEYBOARD_BTN2+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN4 = CTRLTYPE_KEYBOARD_BTN3+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN5 = CTRLTYPE_KEYBOARD_BTN4+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN6 = CTRLTYPE_KEYBOARD_BTN5+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN7 = CTRLTYPE_KEYBOARD_BTN6+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN8 = CTRLTYPE_KEYBOARD_BTN7+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN9 = CTRLTYPE_KEYBOARD_BTN8+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN10 = CTRLTYPE_KEYBOARD_BTN9+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN11 = CTRLTYPE_KEYBOARD_BTN10+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN12 = CTRLTYPE_KEYBOARD_BTN11+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN13 = CTRLTYPE_KEYBOARD_BTN12+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN14 = CTRLTYPE_KEYBOARD_BTN13+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN15 = CTRLTYPE_KEYBOARD_BTN14+1; 
        public static final int CTRLTYPE_KEYBOARD_BTN16 = CTRLTYPE_KEYBOARD_BTN15+1; 
        public static final int CTRLTYPE_KEYBOARD_SPLIT = CTRLTYPE_KEYBOARD_BTN16+1; 
        public static final int CTRLTYPE_KEYBOARD_ONE = CTRLTYPE_KEYBOARD_SPLIT+1; 
        public static final int CTRLTYPE_KEYBOARD_NINE = CTRLTYPE_KEYBOARD_ONE+1; 
        public static final int CTRLTYPE_KEYBOARD_ADDR = CTRLTYPE_KEYBOARD_NINE+1; 
        public static final int CTRLTYPE_KEYBOARD_INFO = CTRLTYPE_KEYBOARD_ADDR+1; 
        public static final int CTRLTYPE_KEYBOARD_REC = CTRLTYPE_KEYBOARD_INFO+1; 
        public static final int CTRLTYPE_KEYBOARD_FN1 = CTRLTYPE_KEYBOARD_REC+1; 
        public static final int CTRLTYPE_KEYBOARD_FN2 = CTRLTYPE_KEYBOARD_FN1+1; 
        public static final int CTRLTYPE_KEYBOARD_PLAY = CTRLTYPE_KEYBOARD_FN2+1; 
        public static final int CTRLTYPE_KEYBOARD_STOP = CTRLTYPE_KEYBOARD_PLAY+1; 
        public static final int CTRLTYPE_KEYBOARD_SLOW = CTRLTYPE_KEYBOARD_STOP+1; 
        public static final int CTRLTYPE_KEYBOARD_FAST = CTRLTYPE_KEYBOARD_SLOW+1; 
        public static final int CTRLTYPE_KEYBOARD_PREW = CTRLTYPE_KEYBOARD_FAST+1; 
        public static final int CTRLTYPE_KEYBOARD_NEXT = CTRLTYPE_KEYBOARD_PREW+1; 
        public static final int CTRLTYPE_KEYBOARD_JMPDOWN = CTRLTYPE_KEYBOARD_NEXT+1; 
        public static final int CTRLTYPE_KEYBOARD_JMPUP = CTRLTYPE_KEYBOARD_JMPDOWN+1; 
        public static final int CTRLTYPE_KEYBOARD_10PLUS = CTRLTYPE_KEYBOARD_JMPUP+1; 
        public static final int CTRLTYPE_KEYBOARD_SHIFT = CTRLTYPE_KEYBOARD_10PLUS+1; 
        public static final int CTRLTYPE_KEYBOARD_BACK = CTRLTYPE_KEYBOARD_SHIFT+1; 
        public static final int CTRLTYPE_KEYBOARD_LOGIN = CTRLTYPE_KEYBOARD_BACK+1;//��������̹���
        public static final int CTRLTYPE_KEYBOARD_CHNNEL = CTRLTYPE_KEYBOARD_LOGIN+1;//�л���Ƶͨ��
        public static final int CTRLTYPE_TRIGGER_ALARM_IN =100;//������������
        public static final int CTRLTYPE_TRIGGER_ALARM_OUT = CTRLTYPE_TRIGGER_ALARM_IN+1; //�����������
        public static final int CTRLTYPE_CTRL_MATRIX = CTRLTYPE_TRIGGER_ALARM_OUT+1; //�������
        public static final int CTRLTYPE_CTRL_SDCARD = CTRLTYPE_CTRL_MATRIX+1; //SD������(IPC��Ʒ)����ͬӲ�̿���
        public static final int CTRLTYPE_BURNING_START = CTRLTYPE_CTRL_SDCARD+1; //��¼�����ƣ���ʼ��¼
        public static final int CTRLTYPE_BURNING_STOP = CTRLTYPE_BURNING_START+1; //��¼�����ƣ�������¼
        public static final int CTRLTYPE_BURNING_ADDPWD = CTRLTYPE_BURNING_STOP+1; //��¼�����ƣ���������(��'\0'Ϊ��β���ַ�������󳤶�8λ)
        public static final int CTRLTYPE_BURNING_ADDHEAD = CTRLTYPE_BURNING_ADDPWD+1; //��¼�����ƣ�����Ƭͷ(��'\0'Ϊ��β���ַ�������󳤶�1024�ֽڣ�֧�ַ��У��зָ���'\n')
        public static final int CTRLTYPE_BURNING_ADDSIGN = CTRLTYPE_BURNING_ADDHEAD+1; //��¼�����ƣ����Ӵ�㵽��¼��Ϣ(������)
        public static final int CTRLTYPE_BURNING_ADDCURSTOMINFO = CTRLTYPE_BURNING_ADDSIGN+1; //��¼�����ƣ��Զ������(��'\0'Ϊ��β���ַ�������󳤶�1024�ֽڣ�֧�ַ��У��зָ���'\n')
        public static final int CTRLTYPE_CTRL_RESTOREDEFAULT = CTRLTYPE_BURNING_ADDCURSTOMINFO+1; //�ָ��豸��Ĭ������
        public static final int CTRLTYPE_CTRL_CAPTURE_START = CTRLTYPE_CTRL_RESTOREDEFAULT+1; //�����豸ץͼ
        public static final int CTRLTYPE_CTRL_CLEARLOG = CTRLTYPE_CTRL_CAPTURE_START+1; //�����־
        public static final int CTRLTYPE_TRIGGER_ALARM_WIRELESS =200;//�������߱���(IPC��Ʒ)
        public static final int CTRLTYPE_MARK_IMPORTANT_RECORD = CTRLTYPE_TRIGGER_ALARM_WIRELESS+1; //��ʶ��Ҫ¼���ļ�
        public static final int CTRLTYPE_CTRL_DISK_SUBAREA = CTRLTYPE_MARK_IMPORTANT_RECORD+1; //����Ӳ�̷���
        public static final int CTRLTYPE_BURNING_ATTACH = CTRLTYPE_CTRL_DISK_SUBAREA+1; //��¼�����ƣ�������¼.
        public static final int CTRLTYPE_BURNING_PAUSE = CTRLTYPE_BURNING_ATTACH+1; //��¼��ͣ
        public static final int CTRLTYPE_BURNING_CONTINUE = CTRLTYPE_BURNING_PAUSE+1; //��¼����
        public static final int CTRLTYPE_BURNING_POSTPONE = CTRLTYPE_BURNING_CONTINUE+1; //��¼˳��
        public static final int CTRLTYPE_CTRL_OEMCTRL = CTRLTYPE_BURNING_POSTPONE+1; //��ͣ����
        public static final int CTRLTYPE_BACKUP_START = CTRLTYPE_CTRL_OEMCTRL+1; //�豸���ݿ�ʼ
        public static final int CTRLTYPE_BACKUP_STOP = CTRLTYPE_BACKUP_START+1; //�豸����ֹͣ
        public static final int CTRLTYPE_VIHICLE_WIFI_ADD = CTRLTYPE_BACKUP_STOP+1; //�����ֶ�����WIFI����
        public static final int CTRLTYPE_VIHICLE_WIFI_DEC = CTRLTYPE_VIHICLE_WIFI_ADD+1; //�����ֶ�ɾ��WIFI����
        public static final int CTRLTYPE_BUZZER_START = CTRLTYPE_VIHICLE_WIFI_DEC+1; //���������ƿ�ʼ
        public static final int CTRLTYPE_BUZZER_STOP = CTRLTYPE_BUZZER_START+1; //���������ƽ���
        public static final int CTRLTYPE_REJECT_USER = CTRLTYPE_BUZZER_STOP+1; //�޳��û�
        public static final int CTRLTYPE_SHIELD_USER = CTRLTYPE_REJECT_USER+1; //�����û�
        public static final int CTRLTYPE_RAINBRUSH = CTRLTYPE_SHIELD_USER+1; //���ܽ�ͨ,��ˢ����
        public static final int CTRLTYPE_MANUAL_SNAP = CTRLTYPE_RAINBRUSH+1; //���ܽ�ͨ,�ֶ�ץ��(��Ӧ�ṹ��MANUAL_SNAP_PARAMETER)
        public static final int CTRLTYPE_MANUAL_NTP_TIMEADJUST = CTRLTYPE_MANUAL_SNAP+1; //�ֶ�NTPУʱ
        public static final int CTRLTYPE_NAVIGATION_SMS = CTRLTYPE_MANUAL_NTP_TIMEADJUST+1; //������Ϣ�Ͷ���Ϣ
        public static final int CTRLTYPE_CTRL_ROUTE_CROSSING = CTRLTYPE_NAVIGATION_SMS+1; //·�ߵ�λ��Ϣ
        public static final int CTRLTYPE_BACKUP_FORMAT = CTRLTYPE_CTRL_ROUTE_CROSSING+1; //��ʽ�������豸
        public static final int CTRLTYPE_DEVICE_LOCALPREVIEW_SLIPT = CTRLTYPE_BACKUP_FORMAT+1; //�����豸�˱���Ԥ���ָ�(��Ӧ�ṹ��DEVICE_LOCALPREVIEW_SLIPT_PARAMETER)
        public static final int CTRLTYPE_CTRL_INIT_RAID = CTRLTYPE_DEVICE_LOCALPREVIEW_SLIPT+1; //RAID��ʼ��
        public static final int CTRLTYPE_CTRL_RAID = CTRLTYPE_CTRL_INIT_RAID+1; //RAID����
        public static final int CTRLTYPE_CTRL_SAPREDISK = CTRLTYPE_CTRL_RAID+1; //�ȱ��̲���
        public static final int CTRLTYPE_WIFI_CONNECT = CTRLTYPE_CTRL_SAPREDISK+1; //�ֶ�����WIFI����(��Ӧ�ṹ��WIFI_CONNECT)
        public static final int CTRLTYPE_WIFI_DISCONNECT = CTRLTYPE_WIFI_CONNECT+1; //�ֶ��Ͽ�WIFI����(��Ӧ�ṹ��WIFI_CONNECT)
        public static final int CTRLTYPE_CTRL_ARMED = CTRLTYPE_WIFI_DISCONNECT+1; //����������
        public static final int CTRLTYPE_CTRL_IP_MODIFY = CTRLTYPE_CTRL_ARMED+1; //�޸�ǰ��IP(��Ӧ�ṹ��NET_CTRL_IPMODIFY_PARAM)
        public static final int CTRLTYPE_CTRL_WIFI_BY_WPS = CTRLTYPE_CTRL_IP_MODIFY+1; //wps����wifi(��Ӧ�ṹ��NET_CTRL_CONNECT_WIFI_BYWPS)
        public static final int CTRLTYPE_CTRL_FORMAT_PATITION = CTRLTYPE_CTRL_WIFI_BY_WPS+1; //��ʽ������(��Ӧ�ṹ��NET_FORMAT_PATITION)
        public static final int CTRLTYPE_CTRL_EJECT_STORAGE = CTRLTYPE_CTRL_FORMAT_PATITION+1; //�ֶ�ж���豸(��Ӧ�ṹ��NET_EJECT_STORAGE_DEVICE)
        public static final int CTRLTYPE_CTRL_LOAD_STORAGE = CTRLTYPE_CTRL_EJECT_STORAGE+1; //�ֶ�װ���豸(��Ӧ�ṹ��NET_LOAD_STORAGE_DEVICE)
        public static final int CTRLTYPE_CTRL_CLOSE_BURNER = CTRLTYPE_CTRL_LOAD_STORAGE+1; //�رտ�¼��������(��Ӧ�ṹ��NET_CTRL_BURNERDOOR)һ����Ҫ��6
        public static final int CTRLTYPE_CTRL_EJECT_BURNER = CTRLTYPE_CTRL_CLOSE_BURNER+1; //������¼��������(��Ӧ�ṹ��NET_CTRL_BURNERDOOR)һ����Ҫ��4��
        public static final int CTRLTYPE_CTRL_CLEAR_ALARM = CTRLTYPE_CTRL_EJECT_BURNER+1; //����(��Ӧ�ṹ��NET_CTRL_CLEAR_ALARM)
        public static final int CTRLTYPE_CTRL_MONITORWALL_TVINFO = CTRLTYPE_CTRL_CLEAR_ALARM+1; //����ǽ��Ϣ��ʾ(��Ӧ�ṹ��NET_CTRL_MONITORWALL_TVINFO)
        public static final int CTRLTYPE_CTRL_START_VIDEO_ANALYSE = CTRLTYPE_CTRL_MONITORWALL_TVINFO+1; //��ʼ��Ƶ���ܷ���(��Ӧ�ṹ��NET_CTRL_START_VIDEO_ANALYSE)
        public static final int CTRLTYPE_CTRL_STOP_VIDEO_ANALYSE = CTRLTYPE_CTRL_START_VIDEO_ANALYSE+1; //ֹͣ��Ƶ���ܷ���(��Ӧ�ṹ��NET_CTRL_STOP_VIDEO_ANALYSE)
        public static final int CTRLTYPE_CTRL_UPGRADE_DEVICE = CTRLTYPE_CTRL_STOP_VIDEO_ANALYSE+1; //���������豸����,���豸���������������,����Ҫ���������ļ�
        public static final int CTRLTYPE_CTRL_MULTIPLAYBACK_CHANNALES = CTRLTYPE_CTRL_UPGRADE_DEVICE+1; //�л���ͨ��Ԥ���طŵ�ͨ��(��Ӧ�ṹ��NET_CTRL_MULTIPLAYBACK_CHANNALES)
        public static final int CTRLTYPE_CTRL_SEQPOWER_OPEN = CTRLTYPE_CTRL_MULTIPLAYBACK_CHANNALES+1; //��Դʱ�����򿪿����������(��ӦNET_CTRL_SEQPOWER_PARAM)
        public static final int CTRLTYPE_CTRL_SEQPOWER_CLOSE = CTRLTYPE_CTRL_SEQPOWER_OPEN+1; //��Դʱ�����رտ����������(��ӦNET_CTRL_SEQPOWER_PARAM)
        public static final int CTRLTYPE_CTRL_SEQPOWER_OPEN_ALL = CTRLTYPE_CTRL_SEQPOWER_CLOSE+1; //��Դʱ�����򿪿������������(��ӦNET_CTRL_SEQPOWER_PARAM)
        public static final int CTRLTYPE_CTRL_SEQPOWER_CLOSE_ALL = CTRLTYPE_CTRL_SEQPOWER_OPEN_ALL+1; //��Դʱ�����رտ������������(��ӦNET_CTRL_SEQPOWER_PARAM)
        public static final int CTRLTYPE_CTRL_PROJECTOR_RISE = CTRLTYPE_CTRL_SEQPOWER_CLOSE_ALL+1; //ͶӰ������(��ӦNET_CTRL_PROJECTOR_PARAM)
        public static final int CTRLTYPE_CTRL_PROJECTOR_FALL = CTRLTYPE_CTRL_PROJECTOR_RISE+1; //ͶӰ���½�(��ӦNET_CTRL_PROJECTOR_PARAM)
        public static final int CTRLTYPE_CTRL_PROJECTOR_STOP = CTRLTYPE_CTRL_PROJECTOR_FALL+1; //ͶӰ��ֹͣ(��ӦNET_CTRL_PROJECTOR_PARAM)
        public static final int CTRLTYPE_CTRL_INFRARED_KEY = CTRLTYPE_CTRL_PROJECTOR_STOP+1; //���ⰴ��(��ӦNET_CTRL_INFRARED_KEY_PARAM)
        public static final int CTRLTYPE_CTRL_START_PLAYAUDIO = CTRLTYPE_CTRL_INFRARED_KEY+1; //�豸��ʼ������Ƶ�ļ�(��Ӧ�ṹ��NET_CTRL_START_PLAYAUDIO)
        public static final int CTRLTYPE_CTRL_STOP_PLAYAUDIO = CTRLTYPE_CTRL_START_PLAYAUDIO+1; //�豸ֹͣ������Ƶ�ļ�
        public static final int CTRLTYPE_CTRL_START_ALARMBELL = CTRLTYPE_CTRL_STOP_PLAYAUDIO+1; //��������(��Ӧ�ṹ��NET_CTRL_ALARMBELL)
        public static final int CTRLTYPE_CTRL_STOP_ALARMBELL = CTRLTYPE_CTRL_START_ALARMBELL+1; //�رվ���(��Ӧ�ṹ��NET_CTRL_ALARMBELL)
        public static final int CTRLTYPE_CTRL_ACCESS_OPEN = CTRLTYPE_CTRL_STOP_ALARMBELL+1; //�Ž�����-����(��Ӧ�ṹ��NET_CTRL_ACCESS_OPEN)
        public static final int CTRLTYPE_CTRL_SET_BYPASS = CTRLTYPE_CTRL_ACCESS_OPEN+1; //������·����(��Ӧ�ṹ��NET_CTRL_SET_BYPASS)
        public static final int CTRLTYPE_CTRL_RECORDSET_INSERT = CTRLTYPE_CTRL_SET_BYPASS+1; //��Ӽ�¼����ü�¼�����(��ӦNET_CTRL_RECORDSET_INSERT_PARAM)
        public static final int CTRLTYPE_CTRL_RECORDSET_UPDATE = CTRLTYPE_CTRL_RECORDSET_INSERT+1; //����ĳ��¼����ŵļ�¼(��ӦNET_CTRL_RECORDSET_PARAM)
        public static final int CTRLTYPE_CTRL_RECORDSET_REMOVE = CTRLTYPE_CTRL_RECORDSET_UPDATE+1; //���ݼ�¼�����ɾ��ĳ��¼(��ӦNET_CTRL_RECORDSET_PARAM)
        public static final int CTRLTYPE_CTRL_RECORDSET_CLEAR = CTRLTYPE_CTRL_RECORDSET_REMOVE+1; //������м�¼����Ϣ(��ӦNET_CTRL_RECORDSET_PARAM)
        public static final int CTRLTYPE_CTRL_ACCESS_CLOSE = CTRLTYPE_CTRL_RECORDSET_CLEAR+1; //�Ž�����-����(��Ӧ�ṹ��NET_CTRL_ACCESS_CLOSE)
        public static final int CTRLTYPE_CTRL_ALARM_SUBSYSTEM_ACTIVE_SET = CTRLTYPE_CTRL_ACCESS_CLOSE+1; //������ϵͳ��������(��Ӧ�ṹ��NET_CTRL_ALARM_SUBSYSTEM_SETACTIVE)
        public static final int CTRLTYPE_CTRL_FORBID_OPEN_STROBE = CTRLTYPE_CTRL_ALARM_SUBSYSTEM_ACTIVE_SET+1; //��ֹ�豸�˿�բ(��Ӧ�ṹ��NET_CTRL_FORBID_OPEN_STROBE)
        public static final int CTRLTYPE_CTRL_OPEN_STROBE = CTRLTYPE_CTRL_FORBID_OPEN_STROBE+1; //������բ(��Ӧ�ṹ�� NET_CTRL_OPEN_STROBE)
        public static final int CTRLTYPE_CTRL_TALKING_REFUSE = CTRLTYPE_CTRL_OPEN_STROBE+1; //�Խ��ܾ�����(��Ӧ�ṹ��NET_CTRL_TALKING_REFUSE)
        public static final int CTRLTYPE_CTRL_ARMED_EX = CTRLTYPE_CTRL_TALKING_REFUSE+1; //����������(��Ӧ�ṹ��CTRL_ARM_DISARM_PARAM_EX),��CTRL_ARM_DISARM_PARAM���������������
        public static final int CTRLTYPE_CTRL_NET_KEYBOARD =400;//������̿���(��Ӧ�ṹ��NET_CTRL_NET_KEYBOARD)
        public static final int CTRLTYPE_CTRL_AIRCONDITION_OPEN = CTRLTYPE_CTRL_NET_KEYBOARD+1; //�򿪿յ�(��Ӧ�ṹ��NET_CTRL_OPEN_AIRCONDITION)
        public static final int CTRLTYPE_CTRL_AIRCONDITION_CLOSE = CTRLTYPE_CTRL_AIRCONDITION_OPEN+1; //�رտյ�(��Ӧ�ṹ��NET_CTRL_CLOSE_AIRCONDITION)
        public static final int CTRLTYPE_CTRL_AIRCONDITION_SET_TEMPERATURE = CTRLTYPE_CTRL_AIRCONDITION_CLOSE+1; //�趨�յ��¶�(��Ӧ�ṹ��NET_CTRL_SET_TEMPERATURE)
        public static final int CTRLTYPE_CTRL_AIRCONDITION_ADJUST_TEMPERATURE = CTRLTYPE_CTRL_AIRCONDITION_SET_TEMPERATURE+1; //���ڿյ��¶�(��Ӧ�ṹ��NET_CTRL_ADJUST_TEMPERATURE)
        public static final int CTRLTYPE_CTRL_AIRCONDITION_SETMODE = CTRLTYPE_CTRL_AIRCONDITION_ADJUST_TEMPERATURE+1; //���ÿյ�����ģʽ(��Ӧ�ṹ��NET_CTRL_ADJUST_TEMPERATURE)
        public static final int CTRLTYPE_CTRL_AIRCONDITION_SETWINDMODE = CTRLTYPE_CTRL_AIRCONDITION_SETMODE+1; //���ÿյ��ͷ�ģʽ(��Ӧ�ṹ��NET_CTRL_AIRCONDITION_SETMODE)
        public static final int CTRLTYPE_CTRL_RESTOREDEFAULT_EX  = CTRLTYPE_CTRL_AIRCONDITION_SETWINDMODE+1;//�ָ��豸��Ĭ��������Э��(��Ӧ�ṹ��NET_CTRL_RESTORE_DEFAULT)
                                                                                                  // �ָ���������ʹ�ø�ö�٣�����ӿ�ʧ�ܣ�
                                                                                                  // ��CLIENT_GetLastError����NET_UNSUPPORTED,�ٳ���ʹ��NET_CTRL_RESTOREDEFAULT�ָ�����
        public static final int CTRLTYPE_CTRL_NOTIFY_EVENT = CTRLTYPE_CTRL_RESTOREDEFAULT_EX+1; //���豸�����¼�(��Ӧ�ṹ��NET_NOTIFY_EVENT_DATA)
        public static final int CTRLTYPE_CTRL_SILENT_ALARM_SET = CTRLTYPE_CTRL_NOTIFY_EVENT+1; //������������
        public static final int CTRLTYPE_CTRL_START_PLAYAUDIOEX = CTRLTYPE_CTRL_SILENT_ALARM_SET+1; //�豸��ʼ��������(��Ӧ�ṹ��NET_CTRL_START_PLAYAUDIOEX)
        public static final int CTRLTYPE_CTRL_STOP_PLAYAUDIOEX = CTRLTYPE_CTRL_START_PLAYAUDIOEX+1; //�豸ֹͣ��������
        public static final int CTRLTYPE_CTRL_CLOSE_STROBE = CTRLTYPE_CTRL_STOP_PLAYAUDIOEX+1; //�رյ�բ(��Ӧ�ṹ�� NET_CTRL_CLOSE_STROBE)
        public static final int CTRLTYPE_CTRL_SET_ORDER_STATE = CTRLTYPE_CTRL_CLOSE_STROBE+1; //���ó�λԤ��״̬(��Ӧ�ṹ��NET_CTRL_SET_ORDER_STATE)
        public static final int CTRLTYPE_CTRL_RECORDSET_INSERTEX = CTRLTYPE_CTRL_SET_ORDER_STATE+1; //��Ӽ�¼����ü�¼�����(��ӦNET_CTRL_RECORDSET_INSERT_PARAM)
        public static final int CTRLTYPE_CTRL_RECORDSET_UPDATEEX = CTRLTYPE_CTRL_RECORDSET_INSERTEX+1; //����ĳ��¼����ŵļ�¼(��ӦNET_CTRL_RECORDSET_PARAM)
        public static final int CTRLTYPE_CTRL_CAPTURE_FINGER_PRINT = CTRLTYPE_CTRL_RECORDSET_UPDATEEX+1; //ָ�Ʋɼ�(��Ӧ�ṹ��NET_CTRL_CAPTURE_FINGER_PRINT)
        public static final int CTRLTYPE_CTRL_ECK_LED_SET = CTRLTYPE_CTRL_CAPTURE_FINGER_PRINT+1; //ͣ��������ڿ�����LED����(��Ӧ�ṹ��NET_CTRL_ECK_LED_SET_PARAM)
        public static final int CTRLTYPE_CTRL_ECK_IC_CARD_IMPORT = CTRLTYPE_CTRL_ECK_LED_SET+1; //����ͣ��ϵͳ����ڻ�IC����Ϣ����(��Ӧ�ṹ��NET_CTRL_ECK_IC_CARD_IMPORT_PARAM)
        public static final int CTRLTYPE_CTRL_ECK_SYNC_IC_CARD = CTRLTYPE_CTRL_ECK_IC_CARD_IMPORT+1; //����ͣ��ϵͳ����ڻ�IC����Ϣͬ��ָ��յ���ָ����豸ɾ��ԭ��IC����Ϣ(��Ӧ�ṹ��NET_CTRL_ECK_SYNC_IC_CARD_PARAM)
        public static final int CTRLTYPE_CTRL_LOWRATEWPAN_REMOVE = CTRLTYPE_CTRL_ECK_SYNC_IC_CARD+1; //ɾ��ָ�������豸(��Ӧ�ṹ��NET_CTRL_LOWRATEWPAN_REMOVE)
        public static final int CTRLTYPE_CTRL_LOWRATEWPAN_MODIFY = CTRLTYPE_CTRL_LOWRATEWPAN_REMOVE+1; //�޸������豸��Ϣ(��Ӧ�ṹ��NET_CTRL_LOWRATEWPAN_MODIFY)
        public static final int CTRLTYPE_CTRL_ECK_SET_PARK_INFO = CTRLTYPE_CTRL_LOWRATEWPAN_MODIFY+1; //����ͣ��ϵͳ����ڻ����ó�λ��Ϣ(��Ӧ�ṹ��NET_CTRL_ECK_SET_PARK_INFO_PARAM)
        public static final int CTRLTYPE_CTRL_VTP_DISCONNECT = CTRLTYPE_CTRL_ECK_SET_PARK_INFO+1; //�Ҷ���Ƶ�绰(��Ӧ�ṹ��NET_CTRL_VTP_DISCONNECT)
        public static final int CTRLTYPE_CTRL_UPDATE_FILES = CTRLTYPE_CTRL_VTP_DISCONNECT+1; //Զ��Ͷ�Ŷ�ý���ļ�����(��Ӧ�ṹ��NET_CTRL_UPDATE_FILES)
        public static final int CTRLTYPE_CTRL_MATRIX_SAVE_SWITCH = CTRLTYPE_CTRL_UPDATE_FILES+1; //��������λ���������ϵ(��Ӧ�ṹ��NET_CTRL_MATRIX_SAVE_SWITCH)
        public static final int CTRLTYPE_CTRL_MATRIX_RESTORE_SWITCH = CTRLTYPE_CTRL_MATRIX_SAVE_SWITCH+1; //�ָ�����λ���������ϵ(��Ӧ�ṹ��NET_CTRL_MATRIX_RESTORE_SWITCH)
        public static final int CTRLTYPE_CTRL_VTP_DIVERTACK = CTRLTYPE_CTRL_MATRIX_RESTORE_SWITCH+1; //����ת����Ӧ(��Ӧ�ṹ��NET_CTRL_VTP_DIVERTACK)
        public static final int CTRLTYPE_CTRL_RAINBRUSH_MOVEONCE = CTRLTYPE_CTRL_VTP_DIVERTACK+1; //��ˢ����ˢһ�Σ���ˢģʽ����Ϊ�ֶ�ģʽʱ��Ч(��Ӧ�ṹ��NET_CTRL_RAINBRUSH_MOVEONCE)
        public static final int CTRLTYPE_CTRL_RAINBRUSH_MOVECONTINUOUSLY = CTRLTYPE_CTRL_RAINBRUSH_MOVEONCE+1; //��ˢ����ѭ��ˢ����ˢģʽ����Ϊ�ֶ�ģʽʱ��Ч(��Ӧ�ṹ��NET_CTRL_RAINBRUSH_MOVECONTINUOUSLY)
        public static final int CTRLTYPE_CTRL_RAINBRUSH_STOPMOVE = CTRLTYPE_CTRL_RAINBRUSH_MOVECONTINUOUSLY+1; //��ˢֹͣˢ����ˢģʽ����Ϊ�ֶ�ģʽʱ��Ч(��Ӧ�ṹ��NET_CTRL_RAINBRUSH_STOPMOVE)
        public static final int CTRLTYPE_CTRL_ALARM_ACK = CTRLTYPE_CTRL_RAINBRUSH_STOPMOVE+1; //�����¼�ȷ��(��Ӧ�ṹ��NET_CTRL_ALARM_ACK)
                                                                                    // NET_CTRL_ALARM_ACK �ò��������ڱ����ص��ӿ��е���
                                                                                    // ��������ֻ�� CLIENT_ControlDeviceEx ����Ч
        public static final int CTRLTYPE_CTRL_THERMO_GRAPHY_ENSHUTTER = 0x10000;//�����ȳ����������/����,pInBuf= NET_IN_THERMO_EN_SHUTTER*, pOutBuf= NET_OUT_THERMO_EN_SHUTTER * 
        public static final int CTRLTYPE_CTRL_RADIOMETRY_SETOSDMARK = CTRLTYPE_CTRL_THERMO_GRAPHY_ENSHUTTER+1; //���ò������osdΪ����,pInBuf=NET_IN_RADIOMETRY_SETOSDMARK*,pOutBuf= NET_OUT_RADIOMETRY_SETOSDMARK * 
        public static final int CTRLTYPE_CTRL_AUDIO_REC_START_NAME = CTRLTYPE_CTRL_RADIOMETRY_SETOSDMARK+1; //������Ƶ¼�����õ�¼����,pInBuf = NET_IN_AUDIO_REC_MNG_NAME *, pOutBuf = NET_OUT_AUDIO_REC_MNG_NAME *
        public static final int CTRLTYPE_CTRL_AUDIO_REC_STOP_NAME = CTRLTYPE_CTRL_AUDIO_REC_START_NAME+1; //�ر���Ƶ¼���������ļ�����,pInBuf = NET_IN_AUDIO_REC_MNG_NAME *, pOutBuf = NET_OUT_AUDIO_REC_MNG_NAME *
        public static final int CTRLTYPE_CTRL_SNAP_MNG_SNAP_SHOT = CTRLTYPE_CTRL_AUDIO_REC_STOP_NAME+1; //��ʱץͼ(�����ֶ�ץͼ),pInBuf  =NET_IN_SNAP_MNG_SHOT *, pOutBuf = NET_OUT_SNAP_MNG_SHOT *
        public static final int CTRLTYPE_CTRL_LOG_STOP = CTRLTYPE_CTRL_SNAP_MNG_SNAP_SHOT+1; //ǿ��ͬ���������ݵ����ݿⲢ�ر����ݿ�,pInBuf = NET_IN_LOG_MNG_CTRL *, pOutBuf = NET_OUT_LOG_MNG_CTRL *
        public static final int CTRLTYPE_CTRL_LOG_RESUME = CTRLTYPE_CTRL_LOG_STOP+1; //�ָ����ݿ�,pInBuf = NET_IN_LOG_MNG_CTRL *, pOutBuf = NET_OUT_LOG_MNG_CTRL *
    }

    // ��Ƶѹ����ʽ
    public static class CFG_VIDEO_COMPRESSION extends Structure
    {
        public static final int VIDEO_FORMAT_MPEG4 = 0; //MPEG4
        public static final int VIDEO_FORMAT_MS_MPEG4 = VIDEO_FORMAT_MPEG4+1; //MS-MPEG4
        public static final int VIDEO_FORMAT_MPEG2 = VIDEO_FORMAT_MS_MPEG4+1; //MPEG2
        public static final int VIDEO_FORMAT_MPEG1 = VIDEO_FORMAT_MPEG2+1; //MPEG1
        public static final int VIDEO_FORMAT_H263 = VIDEO_FORMAT_MPEG1+1; //H.263
        public static final int VIDEO_FORMAT_MJPG = VIDEO_FORMAT_H263+1; //MJPG
        public static final int VIDEO_FORMAT_FCC_MPEG4 = VIDEO_FORMAT_MJPG+1; //FCC-MPEG4
        public static final int VIDEO_FORMAT_H264 = VIDEO_FORMAT_FCC_MPEG4+1; //H.264
        public static final int VIDEO_FORMAT_H265 = VIDEO_FORMAT_H264+1; //H.265
    }

    // ��������ģʽ
    public static class CFG_BITRATE_CONTROL extends Structure
    {
        public static final int BITRATE_CBR = 0;              //�̶�����
        public static final int BITRATE_VBR = BITRATE_CBR+1; //�ɱ�����
    }

    // H264 ���뼶��
    public static class CFG_H264_PROFILE_RANK extends Structure
    {
        public static final int PROFILE_BASELINE = 1;//�ṩI/P֡����֧��progressive(����ɨ��)��CAVLC
        public static final int PROFILE_MAIN = PROFILE_BASELINE+1; //�ṩI/P/B֡��֧��progressiv��interlaced���ṩCAVLC��CABAC
        public static final int PROFILE_EXTENDED = PROFILE_MAIN+1; //�ṩI/P/B/SP/SI֡����֧��progressive(����ɨ��)��CAVLC
        public static final int PROFILE_HIGH = PROFILE_EXTENDED+1; //��FRExt��Main_Profile������������8x8intraprediction(8x8֡��Ԥ��), custom 
                                                                   // quant(�Զ�������), lossless video coding(������Ƶ����), �����yuv��ʽ
    }

    // ����
    public static class CFG_IMAGE_QUALITY extends Structure
    {
        public static final int IMAGE_QUALITY_Q10 = 1;//ͼ������10%
        public static final int IMAGE_QUALITY_Q30 = IMAGE_QUALITY_Q10+1; //ͼ������30%
        public static final int IMAGE_QUALITY_Q50 = IMAGE_QUALITY_Q30+1; //ͼ������50%
        public static final int IMAGE_QUALITY_Q60 = IMAGE_QUALITY_Q50+1; //ͼ������60%
        public static final int IMAGE_QUALITY_Q80 = IMAGE_QUALITY_Q60+1; //ͼ������80%
        public static final int IMAGE_QUALITY_Q100 = IMAGE_QUALITY_Q80+1; //ͼ������100%
    }

    // ��Ƶ��ʽ
    public static class CFG_VIDEO_FORMAT extends Structure
    {
        // ����
        public byte abCompression;// ����Ϊbool, ȡֵ0��1
        public byte abWidth;// ����Ϊbool, ȡֵ0��1
        public byte abHeight;// ����Ϊbool, ȡֵ0��1
        public byte abBitRateControl;// ����Ϊbool, ȡֵ0��1
        public byte abBitRate;// ����Ϊbool, ȡֵ0��1
        public byte abFrameRate;// ����Ϊbool, ȡֵ0��1
        public byte abIFrameInterval;// ����Ϊbool, ȡֵ0��1
        public byte abImageQuality;// ����Ϊbool, ȡֵ0��1
        public byte abFrameType;// ����Ϊbool, ȡֵ0��1
        public byte abProfile;// ����Ϊbool, ȡֵ0��1
        // ��Ϣ
        public int emCompression;//��Ƶѹ����ʽ, ȡֵΪCFG_VIDEO_COMPRESSION�е�ֵ
        public int nWidth;//��Ƶ���
        public int nHeight;//��Ƶ�߶�
        public int emBitRateControl;//��������ģʽ, ȡֵΪCFG_BITRATE_CONTROL�е�ֵ
        public int nBitRate;//��Ƶ����(kbps)
        public float nFrameRate;//��Ƶ֡��
        public int nIFrameInterval;//I֡���(1-100)������50��ʾÿ49��B֡��P֡������һ��I֡��
        public int emImageQuality;//ͼ������, ȡֵΪCFG_IMAGE_QUALITY�е�ֵ
        public int nFrameType;//���ģʽ��0��DHAV��1��"PS"
        public int emProfile;//H.264���뼶��, ȡֵΪCFG_H264_PROFILE_RANK�е�ֵ
    }

    // ��Ƶ����ģʽ
    public static class CFG_AUDIO_FORMAT extends Structure
    {
        public static final int  AUDIO_FORMAT_G711A = 0; //G711a
        public static final int  AUDIO_FORMAT_PCM = AUDIO_FORMAT_G711A+1; //PCM
        public static final int  AUDIO_FORMAT_G711U = AUDIO_FORMAT_PCM+1; //G711u
        public static final int  AUDIO_FORMAT_AMR = AUDIO_FORMAT_G711U+1; //AMR
        public static final int  AUDIO_FORMAT_AAC = AUDIO_FORMAT_AMR+1; //AAC
    }

    // ��Ƶ��ʽ
    public static class CFG_AUDIO_ENCODE_FORMAT extends Structure
    {
        // ����
        public byte abCompression;// ����Ϊbool, ȡֵ0��1
        public byte abDepth;// ����Ϊbool, ȡֵ0��1
        public byte abFrequency;// ����Ϊbool, ȡֵ0��1
        public byte abMode;// ����Ϊbool, ȡֵ0��1
        public byte abFrameType;// ����Ϊbool, ȡֵ0��1
        public byte abPacketPeriod;// ����Ϊbool, ȡֵ0��1
        // ��Ϣ
        public int emCompression;//��Ƶѹ��ģʽ��ȡֵΪCFG_AUDIO_FORMAT�е�ֵ
        public int nDepth;//��Ƶ�������
        public int nFrequency;//��Ƶ����Ƶ��
        public int nMode;//��Ƶ����ģʽ
        public int nFrameType;//��Ƶ���ģʽ,0-DHAV,1-PS
        public int nPacketPeriod;//��Ƶ�������,ms
    }

    // ��Ƶ�������
    public static class CFG_VIDEOENC_OPT extends Structure
    {
        // ����
        public byte abVideoEnable;// ����Ϊbool, ȡֵ0��1
        public byte abAudioEnable;// ����Ϊbool, ȡֵ0��1
        public byte abSnapEnable;// ����Ϊbool, ȡֵ0��1
        public byte abAudioAdd;//��Ƶ��������, ����Ϊbool, ȡֵ0��1
        public byte abAudioFormat;// ����Ϊbool, ȡֵ0��1
        // ��Ϣ
        public int bVideoEnable;//��Ƶʹ��, ����ΪBOOL, ȡֵ0����1
        public CFG_VIDEO_FORMAT stuVideoFormat;//��Ƶ��ʽ
        public int bAudioEnable;//��Ƶʹ��, ����ΪBOOL, ȡֵ0����1
        public int bSnapEnable;//��ʱץͼʹ��, ����ΪBOOL, ȡֵ0����1
        public int bAudioAddEnable;//��Ƶ����ʹ��, ����ΪBOOL, ȡֵ0����1
        public CFG_AUDIO_ENCODE_FORMAT stuAudioFormat;//��Ƶ��ʽ
    }

    // �ڵ���Ϣ
    public static class CFG_COVER_INFO extends Structure
    {
        // ����
        public byte abBlockType;// ����Ϊbool, ȡֵ0��1
        public byte abEncodeBlend;// ����Ϊbool, ȡֵ0��1
        public byte abPreviewBlend;// ����Ϊbool, ȡֵ0��1
        // ��Ϣ
        public CFG_RECT stuRect = new CFG_RECT();//���ǵ���������
        public CFG_RGBA stuColor = new CFG_RGBA();//���ǵ���ɫ
        public int nBlockType;//���Ƿ�ʽ��0���ڿ飬1��������
        public int nEncodeBlend;//���뼶�ڵ���1����Ч��0������Ч
        public int nPreviewBlend;//Ԥ���ڵ���1����Ч��0������Ч
    }

    // �������ڵ�����
    public static class CFG_VIDEO_COVER extends Structure
    {
        public int nTotalBlocks;//֧�ֵ��ڵ�����
        public int nCurBlocks;//�����õĿ���
        public CFG_COVER_INFO[] stuCoverBlock = (CFG_COVER_INFO[])new CFG_COVER_INFO().toArray(MAX_VIDEO_COVER_NUM);// ���ǵ�����    
    }

    // OSD��Ϣ
    public static class CFG_OSD_INFO extends Structure
    {
        // ����
        public byte abShowEnable;// ����Ϊbool, ȡֵ0��1
        // ��Ϣ
        public CFG_RGBA stuFrontColor = new CFG_RGBA();//ǰ����ɫ
        public CFG_RGBA stuBackColor = new CFG_RGBA();//������ɫ
        public CFG_RECT stuRect = new CFG_RECT();//��������
        public int bShowEnable;//��ʾʹ��, ����ΪBOOL, ȡֵ0����1
    }

    // ������ɫ����
    public static class CFG_COLOR_INFO extends Structure
    {
        public int nBrightness;//����(0-100)
        public int nContrast;//�Աȶ�(0-100)
        public int nSaturation;//���Ͷ�(0-100)
        public int nHue;//ɫ��(0-100)
        public int nGain;//����(0-100)
        public int bGainEn;//����ʹ��, ����ΪBOOL, ȡֵ0����1
    }

    // ͼ��ͨ��������Ϣ
    public static class CFG_ENCODE_INFO extends Structure
    {
        public int nChannelID;//ͨ����(0��ʼ),��ȡʱ�����ֶ���Ч������ʱ�����ֶ���Ч
        public byte[] szChnName = new byte[MAX_CHANNELNAME_LEN];//��Ч�ֶ�
        public CFG_VIDEOENC_OPT[] stuMainStream = (CFG_VIDEOENC_OPT[])new CFG_VIDEOENC_OPT().toArray(MAX_VIDEOSTREAM_NUM);    // ��������0����ͨ¼��1-����¼��2������¼��
        public CFG_VIDEOENC_OPT[] stuExtraStream = (CFG_VIDEOENC_OPT[])new CFG_VIDEOENC_OPT().toArray(MAX_VIDEOSTREAM_NUM);    // ��������0��������1��1��������2��2��������3
        public CFG_VIDEOENC_OPT[] stuSnapFormat = (CFG_VIDEOENC_OPT[])new CFG_VIDEOENC_OPT().toArray(MAX_VIDEOSTREAM_NUM);    // ץͼ��0����ͨץͼ��1������ץͼ��2������ץͼ
        public int dwCoverAbilityMask;//��Ч�ֶ�
        public int dwCoverEnableMask;//��Ч�ֶ�
        public CFG_VIDEO_COVER stuVideoCover;//��Ч�ֶ�
        public CFG_OSD_INFO stuChnTitle;//��Ч�ֶ�
        public CFG_OSD_INFO stuTimeTitle;//��Ч�ֶ�
        public CFG_COLOR_INFO stuVideoColor;//��Ч�ֶ�
        public int emAudioFormat;//��Ч�ֶ�, ȡֵΪCFG_AUDIO_FORMAT�е�ֵ
        public int nProtocolVer;//Э��汾��,ֻ��,��ȡʱ�����ֶ���Ч������ʱ�����ֶ���Ч
    }

    // �豸����汾��Ϣ,��16λ��ʾ���汾��,��16λ��ʾ�ΰ汾��
    public static class NET_VERSION_INFO extends Structure
    {
        public int dwSoftwareVersion;
        public int dwSoftwareBuildDate;
        public int dwDspSoftwareVersion;
        public int dwDspSoftwareBuildDate;
        public int dwPanelVersion;
        public int dwPanelSoftwareBuildDate;
        public int dwHardwareVersion;
        public int dwHardwareDate;
        public int dwWebVersion;
        public int dwWebBuildDate;
    }

    // DSP��������,��ӦCLIENT_GetDevConfig�ӿ�
    public static class NET_DSP_ENCODECAP extends Structure
    {
        public int dwVideoStandardMask;//��Ƶ��ʽ����,��λ��ʾ�豸�ܹ�֧�ֵ���Ƶ��ʽ
        public int dwImageSizeMask;//�ֱ�������,��λ��ʾ�豸�ܹ�֧�ֵķֱ�������
        public int dwEncodeModeMask;//����ģʽ����,��λ��ʾ�豸�ܹ�֧�ֵı���ģʽ����
        public int dwStreamCap;    // ��λ��ʾ�豸֧�ֵĶ�ý�幦��,
                                // ��һλ��ʾ֧��������
                                // �ڶ�λ��ʾ֧�ָ�����1
                                // ����λ��ʾ֧�ָ�����2
                                // ����λ��ʾ֧��jpgץͼ
        public int[] dwImageSizeMask_Assi = new int[8];//��ʾ������Ϊ���ֱ���ʱ,֧�ֵĸ������ֱ������롣
        public int dwMaxEncodePower;//DSP֧�ֵ���߱�������
        public short wMaxSupportChannel;//ÿ��DSP֧�����������Ƶͨ����
        public short wChannelMaxSetSync;//DSPÿͨ���������������Ƿ�ͬ����0����ͬ��,1��ͬ��
    }

    // ϵͳ��Ϣ
    public static class NET_DEV_SYSTEM_ATTR_CFG extends Structure
    {
        public int dwSize;
        /* �������豸��ֻ������ */
        public NET_VERSION_INFO stVersion;
        public NET_DSP_ENCODECAP stDspEncodeCap;//DSP��������
        public byte[] szDevSerialNo = new byte[NET_DEV_SERIALNO_LEN];//���к�
        public byte byDevType;//�豸����,��ö��NET_DEVICE_TYPE
        public byte[] szDevType = new byte[NET_DEV_TYPE_LEN];//�豸��ϸ�ͺ�,�ַ�����ʽ,����Ϊ��
        public byte byVideoCaptureNum;//��Ƶ������
        public byte byAudioCaptureNum;//��Ƶ������
        public byte byTalkInChanNum;//�Խ�����ӿ�����
        public byte byTalkOutChanNum;//�Խ�����ӿ�����
        public byte byDecodeChanNum;//NSP
        public byte byAlarmInNum;//�����������
        public byte byAlarmOutNum;//�����������
        public byte byNetIONum;//�������
        public byte byUsbIONum;//USB������
        public byte byIdeIONum;//IDE����
        public byte byComIONum;//��������
        public byte byLPTIONum;//��������
        public byte byVgaIONum;//NSP
        public byte byIdeControlNum;//NSP
        public byte byIdeControlType;//NSP
        public byte byCapability;//NSP,��չ����
        public byte byMatrixOutNum;//��Ƶ�����������
        /* �������豸�Ŀ�д���� */
        public byte byOverWrite;//Ӳ��������ʽ(���ǡ�ֹͣ)
        public byte byRecordLen;//¼��������
        public byte byDSTEnable;//�Ƿ�ʵ������ʱ1-ʵ��0-��ʵ��
        public short wDevNo;//�豸���,����ң��
        public byte byVideoStandard;//��Ƶ��ʽ:0-PAL,1-NTSC
        public byte byDateFormat;//���ڸ�ʽ
        public byte byDateSprtr;//���ڷָ��(0��".",1��"-",2��"/")
        public byte byTimeFmt;//ʱ���ʽ(0-24Сʱ,1��12Сʱ)
        public byte byLanguage;//ö��ֵ���NET_LANGUAGE_TYPE

        public NET_DEV_SYSTEM_ATTR_CFG()
        {
            this.dwSize = this.size();
        }
    }

    // ���ַ���
    public static class EM_MSG_OBJ_PERSON_DIRECTION extends Structure
    {
        public static final int EM_MSG_OBJ_PERSON_DIRECTION_UNKOWN = 0; //δ֪����
        public static final int EM_MSG_OBJ_PERSON_DIRECTION_LEFT_TO_RIGHT = EM_MSG_OBJ_PERSON_DIRECTION_UNKOWN+1; //��������
        public static final int EM_MSG_OBJ_PERSON_DIRECTION_RIGHT_TO_LEFT = EM_MSG_OBJ_PERSON_DIRECTION_LEFT_TO_RIGHT+1; //��������
    }

    // ��Ƶ����������Ϣ��չ�ṹ��
    public static class NET_MSG_OBJECT_EX extends Structure
    {
        public int dwSize;
        public int nObjectID;//����ID,ÿ��ID��ʾһ��Ψһ������
        public byte[] szObjectType = new byte[128];//��������
        public int nConfidence;//���Ŷ�(0~255),ֵԽ���ʾ���Ŷ�Խ��
        public int nAction;//���嶯��:1:Appear2:Move3:Stay 4:Remove 5:Disappear 6:Split 7:Merge 8:Rename
        public DH_RECT BoundingBox;//��Χ��
        public NET_POINT Center;//��������
        public int nPolygonNum;//����ζ������
        public NET_POINT[] Contour = (NET_POINT[])new NET_POINT().toArray(NET_MAX_POLYGON_NUM);// �Ͼ�ȷ�����������
        public int rgbaMainColor;//��ʾ���ơ������������Ҫ��ɫ�����ֽڱ�ʾ,�ֱ�Ϊ�졢�̡�����͸����,����:RGBֵΪ(0,255,0),͸����Ϊ0ʱ,��ֵΪ0x00ff0000.
        public byte[] szText = new byte[128];//ͬNET_MSG_OBJECT��Ӧ�ֶ�
        public byte[] szObjectSubType = new byte[64];//���������,���ݲ�ͬ����������,����ȡ���������ͣ�
        // ͬNET_MSG_OBJECT��Ӧ�ֶ�
        public byte[] byReserved1 = new byte[3];
        public byte bPicEnble;//�Ƿ��������ӦͼƬ�ļ���Ϣ, ����Ϊbool, ȡֵ0��1
        public NET_PIC_INFO stPicInfo;//�����ӦͼƬ��Ϣ
        public byte bShotFrame;//�Ƿ���ץ���ŵ�ʶ����, ����Ϊbool, ȡֵ0��1
        public byte bColor;//������ɫ(rgbaMainColor)�Ƿ����, ����Ϊbool, ȡֵ0��1
        public byte bLowerBodyColor;//�°�����ɫ(rgbaLowerBodyColor)�Ƿ����
        public byte byTimeType;//ʱ���ʾ����,���EM_TIME_TYPE˵��
        public NET_TIME_EX stuCurrentTime;//�����ƵŨ��,��ǰʱ���������ץ�Ļ�ʶ��ʱ,�Ὣ��ʶ������֡����һ����Ƶ֡��jpegͼƬ��,��֡����ԭʼ��Ƶ�еĳ���ʱ�䣩
        public NET_TIME_EX stuStartTime;//��ʼʱ��������忪ʼ����ʱ��
        public NET_TIME_EX stuEndTime;//����ʱ���������������ʱ��
        public DH_RECT stuOriginalBoundingBox;//��Χ��(��������)
        public DH_RECT stuSignBoundingBox;//���������Χ��
        public int dwCurrentSequence;//��ǰ֡��ţ�ץ���������ʱ��֡��
        public int dwBeginSequence;//��ʼ֡��ţ����忪ʼ����ʱ��֡��ţ�
        public int dwEndSequence;//����֡��ţ���������ʱ��֡��ţ�
        public long nBeginFileOffset;//��ʼʱ�ļ�ƫ��,��λ:�ֽڣ����忪ʼ����ʱ,��Ƶ֡��ԭʼ��Ƶ�ļ���������ļ���ʼ����ƫ�ƣ�
        public long nEndFileOffset;//����ʱ�ļ�ƫ��,��λ:�ֽڣ���������ʱ,��Ƶ֡��ԭʼ��Ƶ�ļ���������ļ���ʼ����ƫ�ƣ�
        public byte[] byColorSimilar = new byte[EM_COLOR_TYPE.NET_COLOR_TYPE_MAX];//������ɫ���ƶ�,ȡֵ��Χ��0-100,�����±�ֵ����ĳ����ɫ,���EM_COLOR_TYPE
        public byte[] byUpperBodyColorSimilar = new byte[EM_COLOR_TYPE.NET_COLOR_TYPE_MAX];//�ϰ���������ɫ���ƶ�(��������Ϊ��ʱ��Ч)
        public byte[] byLowerBodyColorSimilar = new byte[EM_COLOR_TYPE.NET_COLOR_TYPE_MAX];//�°���������ɫ���ƶ�(��������Ϊ��ʱ��Ч)
        public int nRelativeID;//�������ID
        public byte[] szSubText = new byte[20];//"ObjectType"Ϊ"Vehicle"����"Logo"ʱ,��ʾ�����µ�ĳһ��ϵ,����µ�A6L,���ڳ�ϵ�϶�,SDKʵ��ʱ͸�����ֶ�,�豸��ʵ��д��
        public int nPersonStature;//������Ա���,��λcm
        public int emPersonDirection;//��Ա���ַ���, ȡֵΪEM_MSG_OBJ_PERSON_DIRECTION�е�ֵ
        public int rgbaLowerBodyColor;//ʹ�÷���ͬrgbaMainColor,��������Ϊ��ʱ��Ч

        public NET_MSG_OBJECT_EX()
        {
            this.dwSize = this.size();
            
            // ǿ�Ʋ���������ֽڶ���
            setAlignType(ALIGN_GNUC);
        }
    }
    
    // ��Ƶ����������Ϣ��չ�ṹ��,��չ�汾2
    public static class NET_MSG_OBJECT_EX2 extends Structure
    {
        public int dwSize;
        public int nObjectID;//����ID,ÿ��ID��ʾһ��Ψһ������
        public byte[] szObjectType = new byte[128];//��������
        public int nConfidence;//���Ŷ�(0~255),ֵԽ���ʾ���Ŷ�Խ��
        public int nAction;//���嶯��:1:Appear2:Move3:Stay 4:Remove 5:Disappear 6:Split 7:Merge 8:Rename
        public DH_RECT BoundingBox;//��Χ��
        public NET_POINT Center;//��������
        public int nPolygonNum;//����ζ������
        public NET_POINT[] Contour = (NET_POINT[])new NET_POINT().toArray(NET_MAX_POLYGON_NUM);//�Ͼ�ȷ�����������
        public int rgbaMainColor;//��ʾ���ơ������������Ҫ��ɫ�����ֽڱ�ʾ,�ֱ�Ϊ�졢�̡�����͸����,����:RGBֵΪ(0,255,0),͸����Ϊ0ʱ,��ֵΪ0x00ff0000.
        public byte[] szText = new byte[128];//ͬNET_MSG_OBJECT��Ӧ�ֶ�
        public byte[] szObjectSubType = new byte[64];//���������,���ݲ�ͬ����������,����ȡ���������ͣ�
        // ͬNET_MSG_OBJECT��Ӧ�ֶ�
        public byte[] byReserved1 = new byte[3];
        public byte bPicEnble;//�Ƿ��������ӦͼƬ�ļ���Ϣ, ����Ϊbool, ȡֵ0����1
        public NET_PIC_INFO stPicInfo;//�����ӦͼƬ��Ϣ
        public byte bShotFrame;//�Ƿ���ץ���ŵ�ʶ����, ����Ϊbool, ȡֵ0����1
        public byte bColor;//������ɫ(rgbaMainColor)�Ƿ����, ����Ϊbool, ȡֵ0����1
        public byte bLowerBodyColor;//�°�����ɫ(rgbaLowerBodyColor)�Ƿ����
        public byte byTimeType;//ʱ���ʾ����,���EM_TIME_TYPE˵��
        public NET_TIME_EX stuCurrentTime;//�����ƵŨ��,��ǰʱ���������ץ�Ļ�ʶ��ʱ,�Ὣ��ʶ������֡����һ����Ƶ֡��jpegͼƬ��,��֡����ԭʼ��Ƶ�еĳ���ʱ�䣩
        public NET_TIME_EX stuStartTime;//��ʼʱ��������忪ʼ����ʱ��
        public NET_TIME_EX stuEndTime;//����ʱ���������������ʱ��
        public DH_RECT stuOriginalBoundingBox;//��Χ��(��������)
        public DH_RECT stuSignBoundingBox;//���������Χ��
        public int dwCurrentSequence;//��ǰ֡��ţ�ץ���������ʱ��֡��
        public int dwBeginSequence;//��ʼ֡��ţ����忪ʼ����ʱ��֡��ţ�
        public int dwEndSequence;//����֡��ţ���������ʱ��֡��ţ�
        public long nBeginFileOffset;//��ʼʱ�ļ�ƫ��,��λ:�ֽڣ����忪ʼ����ʱ,��Ƶ֡��ԭʼ��Ƶ�ļ���������ļ���ʼ����ƫ�ƣ�
        public long nEndFileOffset;//����ʱ�ļ�ƫ��,��λ:�ֽڣ���������ʱ,��Ƶ֡��ԭʼ��Ƶ�ļ���������ļ���ʼ����ƫ�ƣ�
        public byte[] byColorSimilar = new byte[EM_COLOR_TYPE.NET_COLOR_TYPE_MAX];//������ɫ���ƶ�,ȡֵ��Χ��0-100,�����±�ֵ����ĳ����ɫ,���EM_COLOR_TYPE
        public byte[] byUpperBodyColorSimilar = new byte[EM_COLOR_TYPE.NET_COLOR_TYPE_MAX];//�ϰ���������ɫ���ƶ�(��������Ϊ��ʱ��Ч)
        public byte[] byLowerBodyColorSimilar = new byte[EM_COLOR_TYPE.NET_COLOR_TYPE_MAX];//�°���������ɫ���ƶ�(��������Ϊ��ʱ��Ч)
        public int nRelativeID;//�������ID
        public byte[] szSubText = new byte[20];//"ObjectType"Ϊ"Vehicle"����"Logo"ʱ,��ʾ�����µ�ĳһ��ϵ,����µ�A6L,���ڳ�ϵ�϶�,SDKʵ��ʱ͸�����ֶ�,�豸��ʵ��д��
        public int nPersonStature;//������Ա���,��λcm
        public int emPersonDirection;//��Ա���ַ���, ȡֵΪEM_MSG_OBJ_PERSON_DIRECTION�е�ֵ
        public int rgbaLowerBodyColor;//ʹ�÷���ͬrgbaMainColor,��������Ϊ��ʱ��Ч
        //��ƵŨ��������Ϣ
        public int nSynopsisSpeed;//Ũ���ٶ���ֵ,����1~10��ʮ����λ,5��ʾŨ����ֻ����5�����ٶȵ����塣�Ǹ���Ե�λ
        // Ϊ0ʱ,���ֶ���Ч
        public int nSynopsisSize;//Ũ���ߴ���ֵ,����1~10��ʮ����λ,3��ʾŨ����ֻ����3���ϴ�С�����塣�Ǹ���Ե�λ
        // Ϊ0ʱ,���ֶ���Ч
        public int bEnableDirection;//ΪTrueʱ,�������˶�����������, ����ΪBOOL, ȡֵ0����1
        // ΪFalseʱ,���������˶�����������,
        public NET_POINT stuSynopsisStartLocation;//Ũ���˶�����,��ʼ�����,��������һ����[0,8192)����,bEnableDirectionΪTrueʱ��Ч
        public NET_POINT stuSynopsisEndLocation;//Ũ���˶�����,��ֹ�����,��������һ����[0,8192)����,bEnableDirectionΪTrueʱ��Ч
        public byte[] byReserved = new byte[2048];//��չ�ֽ�
        
        public NET_MSG_OBJECT_EX2()
        {
            this.dwSize = this.size();
            
            // ǿ�Ʋ���������ֽڶ���
            setAlignType(ALIGN_GNUC);
        }
    }
    
    // �豸Э������
    public static class NET_DEVICE_PROTOCOL extends Structure
    {
        public static final int NET_PROTOCOL_PRIVATE2 = 0; //˽��2��Э��
        public static final int NET_PROTOCOL_PRIVATE3 = NET_PROTOCOL_PRIVATE2+1; //˽��3��Э��
        public static final int NET_PROTOCOL_ONVIF = NET_PROTOCOL_PRIVATE3+1; //Onvif
        public static final int NET_PROTOCOL_VNC = NET_PROTOCOL_ONVIF+1; //������������
        public static final int NET_PROTOCOL_TS = NET_PROTOCOL_VNC+1; //��׼TS
        public static final int NET_PROTOCOL_PRIVATE = 100;//˽��Э��
        public static final int NET_PROTOCOL_AEBELL = NET_PROTOCOL_PRIVATE+1; //���籴��
        public static final int NET_PROTOCOL_PANASONIC = NET_PROTOCOL_AEBELL+1; //����
        public static final int NET_PROTOCOL_SONY = NET_PROTOCOL_PANASONIC+1; //����
        public static final int NET_PROTOCOL_DYNACOLOR = NET_PROTOCOL_SONY+1; //Dynacolor
        public static final int NET_PROTOCOL_TCWS = NET_PROTOCOL_DYNACOLOR+1; //�������
        public static final int NET_PROTOCOL_SAMSUNG = NET_PROTOCOL_TCWS+1; //����
        public static final int NET_PROTOCOL_YOKO = NET_PROTOCOL_SAMSUNG+1; //YOKO
        public static final int NET_PROTOCOL_AXIS = NET_PROTOCOL_YOKO+1; //��Ѷ��
        public static final int NET_PROTOCOL_SANYO = NET_PROTOCOL_AXIS+1; //����
        public static final int NET_PROTOCOL_BOSH = NET_PROTOCOL_SANYO+1; //Bosch
        public static final int NET_PROTOCOL_PECLO = NET_PROTOCOL_BOSH+1; //Peclo
        public static final int NET_PROTOCOL_PROVIDEO = NET_PROTOCOL_PECLO+1; //Provideo
        public static final int NET_PROTOCOL_ACTI = NET_PROTOCOL_PROVIDEO+1; //ACTi
        public static final int NET_PROTOCOL_VIVOTEK = NET_PROTOCOL_ACTI+1; //Vivotek
        public static final int NET_PROTOCOL_ARECONT = NET_PROTOCOL_VIVOTEK+1; //Arecont
        public static final int NET_PROTOCOL_PRIVATEEH = NET_PROTOCOL_ARECONT+1; //PrivateEH
        public static final int NET_PROTOCOL_IMATEK = NET_PROTOCOL_PRIVATEEH+1; //IMatek
        public static final int NET_PROTOCOL_SHANY = NET_PROTOCOL_IMATEK+1; //Shany
        public static final int NET_PROTOCOL_VIDEOTREC = NET_PROTOCOL_SHANY+1; //����ӯ��
        public static final int NET_PROTOCOL_URA = NET_PROTOCOL_VIDEOTREC+1; //Ura
        public static final int NET_PROTOCOL_BITICINO = NET_PROTOCOL_URA+1; //Bticino
        public static final int NET_PROTOCOL_ONVIF2 = NET_PROTOCOL_BITICINO+1; //OnvifЭ������,ͬNET_PROTOCOL_ONVIF
        public static final int NET_PROTOCOL_SHEPHERD = NET_PROTOCOL_ONVIF2+1; //�Ӱ�
        public static final int NET_PROTOCOL_YAAN = NET_PROTOCOL_SHEPHERD+1; //�ǰ�
        public static final int NET_PROTOCOL_AIRPOINT = NET_PROTOCOL_YAAN+1; //Airpop
        public static final int NET_PROTOCOL_TYCO = NET_PROTOCOL_AIRPOINT+1; //TYCO
        public static final int NET_PROTOCOL_XUNMEI = NET_PROTOCOL_TYCO+1; //Ѷ��
        public static final int NET_PROTOCOL_HIKVISION = NET_PROTOCOL_XUNMEI+1; //����
        public static final int NET_PROTOCOL_LG = NET_PROTOCOL_HIKVISION+1; //LG
        public static final int NET_PROTOCOL_AOQIMAN = NET_PROTOCOL_LG+1; //������
        public static final int NET_PROTOCOL_BAOKANG = NET_PROTOCOL_AOQIMAN+1; //����
        public static final int NET_PROTOCOL_WATCHNET = NET_PROTOCOL_BAOKANG+1; //Watchnet
        public static final int NET_PROTOCOL_XVISION = NET_PROTOCOL_WATCHNET+1; //Xvision
        public static final int NET_PROTOCOL_FUSITSU = NET_PROTOCOL_XVISION+1; //��ʿͨ
        public static final int NET_PROTOCOL_CANON = NET_PROTOCOL_FUSITSU+1; //Canon
        public static final int NET_PROTOCOL_GE = NET_PROTOCOL_CANON+1; //GE
        public static final int NET_PROTOCOL_Basler = NET_PROTOCOL_GE+1; //��˹��
        public static final int NET_PROTOCOL_Patro = NET_PROTOCOL_Basler+1; //������
        public static final int NET_PROTOCOL_CPKNC = NET_PROTOCOL_Patro+1; //CPPLUSKϵ��
        public static final int NET_PROTOCOL_CPRNC = NET_PROTOCOL_CPKNC+1; //CPPLUSRϵ��
        public static final int NET_PROTOCOL_CPUNC = NET_PROTOCOL_CPRNC+1; //CPPLUSUϵ��
        public static final int NET_PROTOCOL_CPPLUS = NET_PROTOCOL_CPUNC+1; //CPPLUSIPC
        public static final int NET_PROTOCOL_XunmeiS = NET_PROTOCOL_CPPLUS+1; //Ѷ��s,ʵ��Э��ΪOnvif
        public static final int NET_PROTOCOL_GDDW = NET_PROTOCOL_XunmeiS+1; //�㶫����
        public static final int NET_PROTOCOL_PSIA = NET_PROTOCOL_GDDW+1; //PSIA
        public static final int NET_PROTOCOL_GB2818 = NET_PROTOCOL_PSIA+1; //GB2818
        public static final int NET_PROTOCOL_GDYX = NET_PROTOCOL_GB2818+1; //GDYX
        public static final int NET_PROTOCOL_OTHER = NET_PROTOCOL_GDYX+1; //���û��Զ���
    }
    
    // ��ˢ����ѭ��ˢ,��ˢģʽ����Ϊ�ֶ�ģʽʱ��Ч(��Ӧ���� CTRLTYPE_CTRL_RAINBRUSH_MOVECONTINUOUSLY)
    public static class NET_CTRL_RAINBRUSH_MOVECONTINUOUSLY extends Structure
    {
        public int dwSize;
        public int nChannel;//��ʾ��ˢ������
        public int nInterval;//��ˢ���
        
        public NET_CTRL_RAINBRUSH_MOVECONTINUOUSLY()
        {
            this.dwSize = this.size();
        }
    }

    // ��ˢֹͣˢ,��ˢģʽ����Ϊ�ֶ�ģʽʱ��Ч(��Ӧ���� CTRLTYPE_CTRL_RAINBRUSH_STOPMOVE)
    public static class NET_CTRL_RAINBRUSH_STOPMOVE extends Structure
    {
        public int dwSize;
        public int nChannel;//��ʾ��ˢ������
        
        public NET_CTRL_RAINBRUSH_STOPMOVE()
        {
            this.dwSize = this.size();
        }
    }

    // ��ˢ����ˢһ�Σ���ˢģʽ����Ϊ�ֶ�ģʽʱ��Ч(��Ӧ���� CTRLTYPE_CTRL_RAINBRUSH_MOVEONCE)
    public static class NET_CTRL_RAINBRUSH_MOVEONCE extends Structure
    {
        public int dwSize;
        public int nChannel;//��ʾ��ˢ������
        
        public NET_CTRL_RAINBRUSH_MOVEONCE()
        {
            this.dwSize = this.size();
        }
    }
    
    // DSP������������չ���ͣ���ӦCLIENT_QueryDevState�ӿ�
    public static class NET_DEV_DSP_ENCODECAP extends Structure
    {
        public int dwVideoStandardMask;//��Ƶ��ʽ���룬��λ��ʾ�豸�ܹ�֧�ֵ���Ƶ��ʽ
        public int dwImageSizeMask;//�ֱ������룬��λ��ʾ�豸�ܹ�֧�ֵķֱ���
        public int dwEncodeModeMask;//����ģʽ���룬��λ��ʾ�豸�ܹ�֧�ֵı���ģʽ
        public int dwStreamCap;//��λ��ʾ�豸֧�ֵĶ�ý�幦�ܣ�
                               // ��һλ��ʾ֧��������
                               // �ڶ�λ��ʾ֧�ָ�����1
                               // ����λ��ʾ֧�ָ�����2
                               // ����λ��ʾ֧��jpgץͼ
        public int[] dwImageSizeMask_Assi = new int[32];//��ʾ������Ϊ���ֱ���ʱ��֧�ֵĸ������ֱ������롣
        public int dwMaxEncodePower;//DSP֧�ֵ���߱�������
        public short wMaxSupportChannel;//ÿ��DSP֧�����������Ƶͨ����
        public short wChannelMaxSetSync;//DSPÿͨ���������������Ƿ�ͬ����0����ͬ����1��ͬ��
        public byte[] bMaxFrameOfImageSize = new byte[32];//��ͬ�ֱ����µ����ɼ�֡�ʣ���dwVideoStandardMask��λ��Ӧ
        public byte bEncodeCap;//��־������ʱҪ����������������������ò�����Ч��
                               // 0���������ı�������+�������ı������� <= �豸�ı���������
                               // 1���������ı�������+�������ı������� <= �豸�ı���������
                               // �������ı������� <= �������ı���������
                               // �������ķֱ��� <= �������ķֱ��ʣ�
                               // �������͸�������֡�� <= ǰ����Ƶ�ɼ�֡��
                               // 2��N5�ļ��㷽��
                               // �������ķֱ��� <= �������ķֱ���
                               // ��ѯ֧�ֵķֱ��ʺ���Ӧ���֡��
        public byte[] reserved = new byte[95];
    }
    
    //��̨�������굥Ԫ
    public static class PTZ_SPACE_UNIT extends Structure
    {
        public int nPositionX;//��̨ˮƽ�˶�λ��,��Ч��Χ��0,3600]
        public int nPositionY;//��̨��ֱ�˶�λ��,��Ч��Χ��-1800,1800]
        public int nZoom;//��̨��Ȧ�䶯λ��,��Ч��Χ��0,128]
        public byte[] szReserve = new byte[32];//Ԥ��32�ֽ�
    }

    //��̨�����ٶȵ�Ԫ
    public static class PTZ_SPEED_UNIT extends Structure
    {
        public float fPositionX;//��̨ˮƽ��������,��һ����-1~1
        public float fPositionY;//��̨��ֱ��������,��һ����-1~1
        public float fZoom;//��̨��Ȧ�Ŵ���,��һ����0~1
        public byte[] szReserve = new byte[32];//Ԥ��32�ֽ�
    }

    //����������̨��Ӧ�ṹ
    public static class PTZ_CONTROL_CONTINUOUSLY extends Structure
    {
        public PTZ_SPEED_UNIT 	  stuSpeed;							//��̨�����ٶ�
        public int 				  nTimeOut;							//�����ƶ���ʱʱ��,��λΪ��
        public byte[] 			  szReserve = new byte[64];			//Ԥ��64�ֽ�
    }

    //���Կ�����̨��Ӧ�ṹ
    public static class PTZ_CONTROL_ABSOLUTELY extends Structure
    {
        public PTZ_SPACE_UNIT 	 stuPosition;						//��̨�����ƶ�λ��
        public PTZ_SPEED_UNIT	 stuSpeed;							//��̨�����ٶ�
        public byte[] 			 szReserve = new byte[64];			//Ԥ��64�ֽ�
    }

    //���ٶ�ת����Ԥ��λ����̨���ƶ�Ӧ�ṹ
    public static class PTZ_CONTROL_GOTOPRESET extends Structure
    {
        public int 				nPresetIndex;						//Ԥ��λ����
        public PTZ_SPEED_UNIT 	stuSpeed;							//��̨�����ٶ�
        public byte[] 			szReserve = new byte[64];			//Ԥ��64�ֽ�
    }

    //������̨��������Ϣ
    public static class PTZ_VIEW_RANGE_INFO extends Structure
    {
        public int 				nStructSize;
        public int 				nAzimuthH;							//ˮƽ��λ�Ƕ�,0~3600,��λ:��
        
        public PTZ_VIEW_RANGE_INFO()
        {
            this.nStructSize = this.size();
        }
    }

    //��̨���Ծ۽���Ӧ�ṹ
    public static class PTZ_FOCUS_ABSOLUTELY extends Structure
    {
        public int 			   dwValue;					 //��̨�۽�λ��,ȡֵ��Χ(0~8191)
        public int 			   dwSpeed;					 //��̨�۽��ٶ�,ȡֵ��Χ(0~7)
        public byte[]		   szReserve = new byte[64]; //Ԥ��64�ֽ�
    }

    // ��̨����-��ɨ��Ӧ�ṹ
    public static class PTZ_CONTROL_SECTORSCAN extends Structure
    {
        public int 			 nBeginAngle;				//��ʼ�Ƕ�,��Χ:-180,180]
        public int			 nEndAngle;					//�����Ƕ�,��Χ:-180,180]
        public int 			 nSpeed;					//�ٶ�,��Χ:0,255]
        public byte[]		 szReserve = new byte[64];  //Ԥ��64�ֽ�
    }

    // �������۵�����̨��Ϣ
    public static class PTZ_CONTROL_SET_FISHEYE_EPTZ extends Structure
    {
        public int 			 dwSize;					//�ṹ���С
        public int 			 dwWindowID;				//����EPtz���ƵĴ��ڱ��
        public int 			 dwCommand;					//������̨����
        public int 			 dwParam1;					//�����Ӧ����1
        public int 			 dwParam2;					//�����Ӧ����2
        public int 			 dwParam3;					//�����Ӧ����3
        public int 			 dwParam4;					//�����Ӧ����4
    }
    
    // �䱶���û�����Ϣ��Ԫ
    public static class CFG_VIDEO_IN_ZOOM_UNIT extends Structure
    {
        public int nSpeed;//�䱶����(0~7)
        public int bDigitalZoom;//�Ƿ����ֱ䱶, ����ΪBOOL, ȡֵ0����1
        public int nZoomLimit;//��ǰ���������䱶����(0~13)��
    }

    // ��ͨ���䱶���û�����Ϣ
    public static class CFG_VIDEO_IN_ZOOM extends Structure
    {
        public int nChannelIndex;//ͨ����
        public int nVideoInZoomRealNum;//����ʹ�ø���
        public CFG_VIDEO_IN_ZOOM_UNIT[] stVideoInZoomUnit = (CFG_VIDEO_IN_ZOOM_UNIT[])new CFG_VIDEO_IN_ZOOM_UNIT().toArray(MAX_VIDEO_IN_ZOOM);//ͨ���������õ�Ԫ��Ϣ
    }

    // �豸״̬
    public static class CFG_TRAFFIC_DEVICE_STATUS extends Structure 
    {
        public byte[]                 szType = new byte[MAX_PATH];          // �豸���� ֧�֣�"Radar","Detector","SigDetector","StroboscopicLamp"," FlashLamp"
        public byte[]                 szSerialNo = new byte[MAX_PATH];      // �豸���
        public byte[]                 szVendor = new byte[MAX_PATH];        // ��������
        public int                    nWokingState;                         // ����״̬ 0-����,1-��������
    }
    
    // ��ȡ�豸����״̬�Ƿ����� (��Ӧ���� CFG_CAP_CMD_DEVICE_STATE )
    public static class CFG_CAP_TRAFFIC_DEVICE_STATUS extends Structure
    {
        public int                          nStatus;                        // stuStatus ʵ�ʸ���
        public CFG_TRAFFIC_DEVICE_STATUS[]  stuStatus = (CFG_TRAFFIC_DEVICE_STATUS[]) new CFG_TRAFFIC_DEVICE_STATUS().toArray(MAX_STATUS_NUM);
    }
    
    // ��Ƶ����ͨ��
    public static class CFG_RemoteDeviceVideoInput extends Structure
    {
    	public int				bEnable;
    	public byte[]			szName = new byte[MAX_DEVICE_NAME_LEN];
    	public byte[]			szControlID = new byte[MAX_DEV_ID_LEN_EX];
    	public byte[]			szMainUrl = new byte[MAX_PATH];	// ������url��ַ
    	public byte[]			szExtraUrl = new byte[MAX_PATH]; // ������url��ַ
    	public int				nServiceType; // ��������, 0-TCP, 1-UDP, 2-MCAST, -1-AUTO
    }
    
    // Զ���豸
    public static class AV_CFG_RemoteDevice extends Structure
    {
    	public int 				nStructSize;
    	public int 				bEnable; // ʹ��
    	public byte[]			szID = new byte[AV_CFG_Device_ID_Len]; // �豸ID
    	public byte[]			szIP = new byte[AV_CFG_IP_Address_Len];// �豸IP
    	public int 				nPort; // �˿�
    	public byte[]			szProtocol = new byte[AV_CFG_Protocol_Len]; // Э������
    	public byte[]			szUser = new byte[AV_CFG_User_Name_Len]; // �û���
    	public byte[]			szPassword = new byte[AV_CFG_Password_Len]; // ����
    	public byte[]			szSerial = new byte[AV_CFG_Serial_Len];	// �豸���к�
    	public byte[]			szDevClass = new byte[AV_CFG_Device_Class_Len]; // �豸����
    	public byte[]			szDevType = new byte[AV_CFG_Device_Type_Len]; // �豸�ͺ�
    	public byte[]			szName = new byte[AV_CFG_Device_Name_Len]; // ��������
    	public byte[]			szAddress =  new byte[AV_CFG_Address_Len]; // ��������ص�
    	public byte[]			szGroup = new byte[AV_CFG_Group_Name_Len]; // ��������
    	public int 				nDefinition; // ������, 0-����, 1-����
    	public int 				nVideoChannel; // ��Ƶ����ͨ����
    	public int 				nAudioChannel; // ��Ƶ����ͨ����
    	public int             	nRtspPort; // Rtsp�˿ں�
    	public byte[]           szVendor = new byte[MAX_PATH]; // �豸��������
    	public Pointer 			pVideoInput; // ��Ƶ����ͨ�����û�����nMaxVideoInputs��CFG_RemoteDeviceVideoInput�ռ�
    	public int              nMaxVideoInputs;
    	public int              nRetVideoInputs;
    	public int				nHttpPort; // http�˿ں�
    	
    	/* ����3��Ϊ���ʽ��뷽ʽ���  */
    	public int 				bGB28181; // �Ƿ��й��ʽ��뷽ʽ
    	public int				nDevLocalPort; // �豸���ض˿�
    	public byte[]			szDeviceNo = new byte[AV_CFG_DeviceNo_Len]; // �豸���
    	
    	public AV_CFG_RemoteDevice() {
        	this.nStructSize = this.size();
    	}
    }
    
    // ��Ƶ������Դ����
    public static class CFG_VIDEO_SOURCE_TYPE extends Structure {
    	public static final int CFG_VIDEO_SOURCE_REALSTREAM = 0; // ʵʱ��
    	public static final int CFG_VIDEO_SOURCE_FILESTREAM = 1; // �ļ���
    }
    
    // ����Դ�ļ�����
    public static class CFG_SOURCE_FILE_TYPE extends Structure {
    	public static final int CFG_SOURCE_FILE_UNKNOWN = 0; // δ֪����
    	public static final int CFG_SOURCE_FILE_RECORD = 1; // ¼���ļ�
    	public static final int CFG_SOURCE_FILE_PICTURE = 2; // ͼƬ�ļ�
    }
    
    // ��Ƶ����Դ�ļ���Ϣ
    public static class CFG_SOURCE_FILE_INFO extends Structure {
    	public byte[]				szFilePath = new byte[MAX_PATH];// �ļ�·��
    	public int 					emFileType; // �ļ����ͣ���� CFG_SOURCE_FILE_TYPE
    }
    
    // ÿ����Ƶ����ͨ����Ӧ����Ƶ������Դ������Ϣ
    public static class CFG_ANALYSESOURCE_INFO extends Structure {
    	public byte					bEnable; // ��Ƶ����ʹ��   1-ʹ�ܣ� 0-����
    	public int					nChannelID;	// ���ܷ�����ǰ����Ƶͨ����
    	public int					nStreamType;// ���ܷ�����ǰ����Ƶ�������ͣ�0:ץͼ����; 1:������; 2:������1; 3:������2; 4:������3; 5:������
    	public byte[]				szRemoteDevice = new byte[MAX_NAME_LEN];// �豸��
    	public byte					abDeviceInfo; // �豸��Ϣ�Ƿ���Ч ; 1-��Ч��0-��Ч
    	public AV_CFG_RemoteDevice  stuDeviceInfo; // �豸��Ϣ
    	public int                  emSourceType; // ��Ƶ����Դ���ͣ����  CFG_VIDEO_SOURCE_TYPE
    	public CFG_SOURCE_FILE_INFO stuSourceFile; // ����Ƶ����Դ����Ϊ CFG_VIDEO_SOURCE_FILESTREAM ʱ����Ч
    } 
    
    public static class CFG_OVERSPEED_INFO extends Structure {
    	public int[]	nSpeedingPercentage = new int[2];                        // ���ٰٷֱ�����Ҫ�����䲻���ص�����ЧֵΪ0,����,-1��-1��ʾ�����ֵ
        // �����Ƿ�٣�Ҫ�����䲻���ص�����ЧֵΪ0,����,-1��-1��ʾ�����ֵ��Ƿ�ٰٷֱȵļ��㷽ʽ���޵���-ʵ�ʳ���/�޵���
    	public byte[]   szCode = new byte[MAX_VIOLATIONCODE];                     // Υ�´���
    	public byte[]   szDescription = new byte[MAX_VIOLATIONCODE_DESCRIPT];     // Υ������
    }
    
    // Υ�´������ñ�
    public static class VIOLATIONCODE_INFO extends Structure {
        public byte[]	szRetrograde = new byte[MAX_VIOLATIONCODE];			                   // ����
    	public byte[]	szRetrogradeDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];               // Υ��������Ϣ
        public byte[]	szRetrogradeShowName = new byte[MAX_VIOLATIONCODE_DESCRIPT];           // ��ʾ����

    	public byte[]	szRetrogradeHighway = new byte[MAX_VIOLATIONCODE];		               // ����-���ٹ�·
    	public byte[]	szRetrogradeHighwayDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];		   // Υ��������Ϣ

    	public byte[]	szRunRedLight = new byte[MAX_VIOLATIONCODE];			               // �����
    	public byte[]	szRunRedLightDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];			   // Υ��������Ϣ

    	public byte[]	szCrossLane = new byte[MAX_VIOLATIONCODE];				               // Υ�±��
    	public byte[]	szCrossLaneDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];			       // Υ��������Ϣ
        public byte[]	szCrossLaneShowName = new byte[MAX_VIOLATIONCODE_DESCRIPT];            // Υ�±����ʾ����

    	public byte[]	szTurnLeft = new byte[MAX_VIOLATIONCODE];				               // Υ����ת
    	public byte[]   szTurnLeftDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];				   // Υ��������Ϣ

    	public byte[]   szTurnRight = new byte[MAX_VIOLATIONCODE];				               // Υ����ת
    	public byte[]   szTurnRightDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];				   // Υ��������Ϣ

    	public byte[]   szU_Turn = new byte[MAX_VIOLATIONCODE];				                   // Υ�µ�ͷ
    	public byte[]   szU_TurnDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];				   // Υ��������Ϣ
        public byte[]   szU_TurnShowName = new byte[MAX_VIOLATIONCODE_DESCRIPT];               // ��ʾ��Ϣ

    	public byte[]   szJam = new byte[MAX_VIOLATIONCODE];					                // ��ͨӵ��
    	public byte[]   szJamDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];					    // Υ��������Ϣ

    	public byte[]   szParking = new byte[MAX_VIOLATIONCODE];				                // Υ��ͣ��
    	public byte[]   szParkingDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];		 	      	// Υ��������Ϣ
        public byte[]   szParkingShowName = new byte[MAX_VIOLATIONCODE_DESCRIPT];               // Υ��ͣ����ʾ����

    	// ���� �� ���ٱ��� ֻ���ұ�����һ������
    	public byte[]    szOverSpeed = new byte[MAX_VIOLATIONCODE];				                // ����
    	public byte[]    szOverSpeedDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];				// Υ��������Ϣ
    	public CFG_OVERSPEED_INFO[]  stOverSpeedConfig = (CFG_OVERSPEED_INFO[])new CFG_OVERSPEED_INFO().toArray(5);                                               // ���ٱ�������

    	// ����(���ٹ�·) �� ���ٱ���(���ٹ�·) ֻ���ұ�����һ������
    	public byte[]    szOverSpeedHighway = new byte[MAX_VIOLATIONCODE];		                // ����-���ٹ�·
    	public byte[]    szOverSpeedHighwayDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];	      	// ����-Υ��������Ϣ
    	public CFG_OVERSPEED_INFO[] stOverSpeedHighwayConfig = (CFG_OVERSPEED_INFO[])new CFG_OVERSPEED_INFO().toArray(5);                                 // ���ٱ�������

    	// Ƿ�� �� Ƿ�ٱ��� ֻ���ұ�����һ������
    	public byte[]    szUnderSpeed = new byte[MAX_VIOLATIONCODE];	                        // Ƿ��
    	public byte[]    szUnderSpeedDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];	            // Υ��������Ϣ
    	public CFG_OVERSPEED_INFO[] stUnderSpeedConfig = (CFG_OVERSPEED_INFO[]) new CFG_OVERSPEED_INFO().toArray(5);                                              // Ƿ��������Ϣ��һ�����飬��ͬ��Ƿ�ٱ�Υ�´��벻ͬ��Ϊ�ձ�ʾΥ�´��벻���ֳ��ٱ�

    	public byte[]    szOverLine = new byte[MAX_VIOLATIONCODE];				                // ѹ��
    	public byte[]    szOverLineDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];			    	// Υ��������Ϣ
        public byte[]    szOverLineShowName = new byte[MAX_VIOLATIONCODE_DESCRIPT];             // ѹ����ʾ����

    	public byte[]    szOverYellowLine = new byte[MAX_VIOLATIONCODE];	                    // ѹ����
    	public byte[]    szOverYellowLineDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];	    	// Υ��������Ϣ

    	public byte[]    szYellowInRoute = new byte[MAX_VIOLATIONCODE];			                // ����ռ��
    	public byte[]    szYellowInRouteDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];			// ����ռ��Υ��������Ϣ

    	public byte[]    szWrongRoute = new byte[MAX_VIOLATIONCODE];			                // ����������ʻ
    	public byte[]    szWrongRouteDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];				// ����������ʻΥ��������Ϣ

    	public byte[]    szDrivingOnShoulder = new byte[MAX_VIOLATIONCODE];		                // ·����ʻ
    	public byte[]    szDrivingOnShoulderDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];		// ·����ʻΥ��������Ϣ

    	public byte[]    szPassing = new byte[MAX_VIOLATIONCODE];                               // ������ʻ
    	public byte[]    szPassingDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];               	// ������ʻΥ��������Ϣ

    	public byte[]    szNoPassing = new byte[MAX_VIOLATIONCODE];                             // ��ֹ��ʻ
    	public byte[]    szNoPassingDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT]; 				// ��ֹ��ʻΥ��������Ϣ

    	public byte[]    szFakePlate = new byte[MAX_VIOLATIONCODE];                             // ����
    	public byte[]    szFakePlateDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT]; 				// ����Υ��������Ϣ
    	
    	public byte[]    szParkingSpaceParking = new byte[MAX_VIOLATIONCODE];                   // ��λ�г�
    	public byte[]    szParkingSpaceParkingDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT]; 		// ��λ�г�Υ��������Ϣ��

    	public byte[]    szParkingSpaceNoParking = new byte[MAX_VIOLATIONCODE];                 // ��λ�޳�
    	public byte[]    szParkingSpaceNoParkingDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT]; 	// ��λ�޳�Υ��������Ϣ

        public byte[]    szWithoutSafeBelt = new byte[MAX_VIOLATIONCODE];                       // ��ϵ��ȫ��
        public byte[]    szWithoutSafeBeltShowName = new byte[MAX_VIOLATIONCODE_DESCRIPT];      // ��ϵ��ȫ����ʾ����
        public byte[]    szWithoutSafeBeltDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT]; 	        // ��ϵ��ȫ��Υ��������Ϣ

        public byte[]    szDriverSmoking = new byte[MAX_VIOLATIONCODE];                         // ��ʻԱ����
        public byte[]    szDriverSmokingShowName = new byte[MAX_VIOLATIONCODE_DESCRIPT];        // ��ʻԱ������ʾ����
        public byte[]    szDriverSmokingDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT]; 	        // ��ʻԱ���̴�Υ��������Ϣ

        public byte[]    szDriverCalling = new byte[MAX_VIOLATIONCODE];                         // ��ʻԱ��绰
        public byte[]    szDriverCallingShowName = new byte[MAX_VIOLATIONCODE_DESCRIPT];        // ��ʻԱ��绰��ʾ����
        public byte[]    szDriverCallingDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT]; 	        // ��ʻԱ��绰Υ��������Ϣ

        public byte[]    szBacking = new byte[MAX_VIOLATIONCODE];                               // Υ�µ���
        public byte[]    szBackingShowName = new byte[MAX_VIOLATIONCODE_DESCRIPT];	            // Υ�µ�����ʾ����
        public byte[]    szBackingDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];                  // Υ�µ���������Ϣ

        public byte[]    szVehicleInBusRoute = new byte[MAX_VIOLATIONCODE];                     // Υ��ռ��
        public byte[]    szVehicleInBusRouteShowName = new byte[MAX_VIOLATIONCODE_DESCRIPT];    // Υ��ռ����ʾ����
        public byte[]    szVehicleInBusRouteDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];        // Υ��ռ��������Ϣ

        public byte[]    szPedestrianRunRedLight = new byte[MAX_VIOLATIONCODE];                 // ���˴����
        public byte[]    szPedestrianRunRedLightShowName = new byte[MAX_VIOLATIONCODE_DESCRIPT];// ���˴������ʾ����
        public byte[]    szPedestrianRunRedLightDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];    // ���˴����������Ϣ
        
        public byte[]    szPassNotInOrder = new byte[MAX_VIOLATIONCODE];                        // δ���涨����ͨ��
        public byte[]    szPassNotInOrderShowName = new byte[MAX_VIOLATIONCODE_DESCRIPT];       // δ���涨����ͨ����ʾ����
        public byte[]    szPassNotInOrderDesc = new byte[MAX_VIOLATIONCODE_DESCRIPT];           // δ���涨����ͨ��������Ϣ
    }
    
    // Υ��ץ��ʱ�����ñ�
    public static class TIME_SCHEDULE_INFO extends Structure {
    	public int                	 bEnable;                                              // �Ƿ�����ʱ���
        public CFG_TIME_SECTION[]    stuTimeSchedule = (CFG_TIME_SECTION[])new CFG_TIME_SECTION().toArray(WEEK_DAY_NUM*MAX_REC_TSECT);         // ʱ���
    }
    
    // Υ��ץ���Զ���ʱ������
    public static class VIOLATION_TIME_SCHEDULE extends Structure 
    {
        public int                abTrafficGate;                  // �Ƿ�Я����ͨ������Ϣ
        public TIME_SCHEDULE_INFO  stTrafficGate;                  // ��ͨ����ʱ������

        public int                abTrafficJunction;              // �Ƿ�Я����ͨ·����Ϣ
        public TIME_SCHEDULE_INFO  stTrafficJunction;              // ��ͨ·��ʱ������

        public int                abTrafficTollGate;              // �Ƿ�Я���½�ͨ������Ϣ
        public TIME_SCHEDULE_INFO  stTrafficTollGate;              // �½�ͨ����ʱ������

        public int                abTrafficRunRedLight;           // �Ƿ�Я����ͨ�������Ϣ
        public TIME_SCHEDULE_INFO  stTrafficRunRedLight;           // ��ͨ�����ʱ������
        
        public int                abTrafficRunYellowLight;        // �Ƿ�Я����ͨ���Ƶ���Ϣ
        public TIME_SCHEDULE_INFO  stTrafficRunYellowLight;        // ��ͨ���Ƶ�ʱ������

        public int                abTrafficOverLine;              // �Ƿ�Я����ͨѹ����Ϣ
        public TIME_SCHEDULE_INFO  stTrafficOverLine;              // ��ͨѹ��ʱ������

        public int                abTrafficOverYellowLine;        // �Ƿ�Я����ͨѹ������Ϣ
        public TIME_SCHEDULE_INFO  stTrafficOverYellowLine;        // ��ͨѹ����ʱ������

        public int                abTrafficRetrograde;            // �Ƿ�Я����ͨ������Ϣ
        public TIME_SCHEDULE_INFO  stTrafficRetrograde;            // ��ͨ����ʱ������

        public int                abTrafficTurnLeft;              // �Ƿ�Я����ͨΥ����ת��Ϣ
        public TIME_SCHEDULE_INFO  stTrafficTurnLeft;              // ��ͨΥ����תʱ������

        public int                abTrafficTurnRight;             // �Ƿ�Я����ͨΥ����ת��Ϣ
        public TIME_SCHEDULE_INFO  stTrafficTurnRight;             // ��ͨ·��Υ����ת����

        public int                abTrafficU_Turn;                // �Ƿ�Я����ͨΥ�µ�ͷ��Ϣ
        public TIME_SCHEDULE_INFO  stTrafficU_Turn;                // ��ͨΥ�µ�ͷʱ������

        public int                abTrafficCrossLane;             // �Ƿ�Я����ͨΥ�±����Ϣ
        public TIME_SCHEDULE_INFO  stTrafficCrossLane;             // ��ͨΥ�±��ʱ������

        public int                abTrafficParking;               // �Ƿ�Я����ͨΥ��ͣ����Ϣ
        public TIME_SCHEDULE_INFO  stTrafficParking;               // ��ͨΥ��ͣ��ʱ������

        public int                abTrafficJam;                   // �Ƿ�Я����ͨӵ����Ϣ
        public TIME_SCHEDULE_INFO  stTrafficJam;                   // ��ͨӵ��ʱ������

        public int                abTrafficIdle;                  // �Ƿ�Я����ͨ��ͨ������Ϣ
        public TIME_SCHEDULE_INFO  stTrafficIdle;                  // ��ͨ��ͨ����ʱ������

        public int                abTrafficWaitingArea;           // �Ƿ�Я����ͨΥ��ʻ���������Ϣ
        public TIME_SCHEDULE_INFO  stTrafficWaitingArea;           // ��ͨΥ��ʻ�������ʱ������

        public int                abTrafficUnderSpeed;            // �Ƿ�Я����ͨǷ����Ϣ
        public TIME_SCHEDULE_INFO  stTrafficUnderSpeed;            // ��ͨǷ��ʱ������

        public int                abTrafficOverSpeed;             // �Ƿ�Я����ͨ������Ϣ
        public TIME_SCHEDULE_INFO  stTrafficOverSpeed;             // ��ͨ����ʱ������

        public int                abTrafficWrongRoute;            // �Ƿ�Я����ͨ����������ʻ��Ϣ
        public TIME_SCHEDULE_INFO  stTrafficWrongRoute;            // ��ͨ����������ʻʱ������

        public int                abTrafficYellowInRoute;         // �Ƿ�Я����ͨ����ռ����Ϣ
        public TIME_SCHEDULE_INFO  stTrafficYellowInRoute;         // ��ͨ����ռ��ʱ������

        public int                abTrafficVehicleInRoute;        // �Ƿ�Я����ͨ�г�ռ����Ϣ
        public TIME_SCHEDULE_INFO  stTrafficVehicleInRoute;        // ��ͨ�г�ռ��ʱ������

        public int                abTrafficControl;               // �Ƿ�Я����ͨ��ͨ������Ϣ
        public TIME_SCHEDULE_INFO  stTrafficControl;               // ��ͨ��ͨ����ʱ������

        public int                abTrafficObjectAlarm;           // �Ƿ�Я����ָͨ������ץ����Ϣ
        public TIME_SCHEDULE_INFO  stTrafficObjectAlarm;           // ��ָͨ������ץ��ʱ������

        public int                abTrafficAccident;              // �Ƿ�Я����ͨ��ͨ�¹���Ϣ
        public TIME_SCHEDULE_INFO  stTrafficAccident;              // ��ͨ��ͨ�¹�ʱ������

        public int                abTrafficStay;                  // �Ƿ�Я����ͨ��ͨͣ��/������Ϣ
        public TIME_SCHEDULE_INFO  stTrafficStay;                  // ��ͨ��ͨͣ��/����ʱ������

        public int                abTrafficPedestrainPriority;    // �Ƿ�Я����ͨ����������������Ϣ
        public TIME_SCHEDULE_INFO  stTrafficPedestrainPriority;    // ��ͨ��������������ʱ������

        public int                abTrafficPedestrain;            // �Ƿ�Я����ͨ��ͨ�����¼���Ϣ
        public TIME_SCHEDULE_INFO  stTrafficPedestrain;            // ��ͨ��ͨ�����¼�ʱ������

        public int                abTrafficThrow;                 // �Ƿ�Я����ͨ��ͨ������Ʒ�¼���Ϣ
        public TIME_SCHEDULE_INFO  stTrafficThrow;                 // ��ͨ��ͨ������Ʒ�¼�ʱ������

        public int                abTrafficVehicleInBusRoute;     // �Ƿ�Я����ͨΥ��ռ����Ϣ
        public TIME_SCHEDULE_INFO  stTrafficVehicleInBusRoute;     // ��ͨΥ��ռ��ʱ������

        public int                abTrafficBacking;               // �Ƿ�Я����ͨΥ�µ�����Ϣ
        public TIME_SCHEDULE_INFO  stTrafficBacking;               // ��ͨΥ�µ���ʱ������

        public int                abTrafficOverStopLine;          // �Ƿ�Я����ͨѹֹͣ����Ϣ
        public TIME_SCHEDULE_INFO  stTrafficOverStopLine;          // ��ͨѹֹͣ��ʱ������

        public int                abTrafficParkingOnYellowBox;    // �Ƿ�Я����ͨ��������ץ����Ϣ
        public TIME_SCHEDULE_INFO  stTrafficParkingOnYellowBox;    // ��ͨ��������ץ��ʱ������

        public int                abTrafficParkingSpaceParking;   // �Ƿ�Я����ͨ��λ�г���Ϣ
        public TIME_SCHEDULE_INFO  stTrafficParkingSpaceParking;   // ��ͨ��λ�г�ʱ������

        public int                abTrafficParkingSpaceNoParking; // �Ƿ�Я����ͨ��λ�޳���Ϣ
        public TIME_SCHEDULE_INFO  stTrafficParkingSpaceNoParking; // ��ͨ��λ�޳�ʱ������

        public int                abTrafficParkingSpaceOverLine;  // �Ƿ�Я����ͨ��λ�г�ѹ����Ϣ
        public TIME_SCHEDULE_INFO  stTrafficParkingSpaceOverLine;  // ��ͨ��λ�г�ѹ��ʱ������

        public int                abParkingSpaceDetection;        // �Ƿ�Я����ͨ��ͣ��λ״̬�����Ϣ
        public TIME_SCHEDULE_INFO  stParkingSpaceDetection;        // ��ͨ��ͣ��λ״̬���ʱ������

        public int                abTrafficRestrictedPlate;       // �Ƿ�Я����ͨ���޳�����Ϣ
        public TIME_SCHEDULE_INFO  stTrafficRestrictedPlate;       // ��ͨ���޳���ʱ������

        public int                abTrafficWithoutSafeBelt;       // �Ƿ�Я����ͨ��ϵ��ȫ����Ϣ
        public TIME_SCHEDULE_INFO  stTrafficWithoutSafeBelt;       // ��ͨ��ϵ��ȫ��ʱ������

        public int                abTrafficNoPassing;             // �Ƿ�Я����ͨ������Ϣ
        public TIME_SCHEDULE_INFO  stTrafficNoPassing;             // ��ͨ����ʱ������

        public int                abVehicleAnalyse;               // �Ƿ�Я����ͨ����������������Ϣ
        public TIME_SCHEDULE_INFO  stVehicleAnalyse;               // ��ͨ��������ʱ������

        public int                abCrossLineDetection;           // �Ƿ�Я����ͨ��������Ϣ
        public TIME_SCHEDULE_INFO  stCrossLineDetection;           // ��ͨ������ʱ������

        public int                abCrossFenceDetection;          // �Ƿ�Я����ͨ��ԽΧ����Ϣ
        public TIME_SCHEDULE_INFO  stCrossFenceDetection;          // ��ͨ��ԽΧ��ʱ������

        public int                abCrossRegionDetection;         // �Ƿ�Я����ͨ��������Ϣ
        public TIME_SCHEDULE_INFO  stCrossRegionDetection;         // ��ͨ������ʱ������

        public int                abPasteDetection;               // �Ƿ�Я����ͨATM������Ϣ
        public TIME_SCHEDULE_INFO  stPasteDetection;               // ��ͨATM����ʱ������

        public int                abLeftDetection;                // �Ƿ�Я����ͨ��Ʒ������Ϣ
        public TIME_SCHEDULE_INFO  stLeftDetection;                // ��ͨ��Ʒ����ʱ������

        public int                abPreservation;                 // �Ƿ�Я����ͨ��Ʒ��ȫ��Ϣ
        public TIME_SCHEDULE_INFO  stPreservation;                 // ��ͨ��Ʒ��ȫʱ������

        public int                abTakenAwayDetection;           // �Ƿ�Я����ͨ��Ʒ������Ϣ
        public TIME_SCHEDULE_INFO  stTakenAwayDetection;           // ��ͨ��Ʒ����ʱ������

        public int                abStayDetection;                // �Ƿ�Я����ͨͣ��/������Ϣ
        public TIME_SCHEDULE_INFO  stStayDetection;                // ��ͨͣ��/����ʱ������

        public int                abParkingDetection;             // �Ƿ�Я����ͨ�Ƿ�ͣ����Ϣ
        public TIME_SCHEDULE_INFO  stParkingDetection;             // ��ͨ�Ƿ�ͣ��ʱ������

        public int                abWanderDetection;              // �Ƿ�Я����ͨ�ǻ���Ϣ
        public TIME_SCHEDULE_INFO  stWanderDetection;              // ��ͨ�ǻ�ʱ������

        public int                abMoveDetection;                // �Ƿ�Я����ͨ�˶���Ϣ
        public TIME_SCHEDULE_INFO  stMoveDetection;                // ��ͨ�˶�ʱ������

        public int                abTailDetection;                // �Ƿ�Я����ͨβ����Ϣ
        public TIME_SCHEDULE_INFO  stTailDetection;                // ��ͨβ��ʱ������

        public int                abRioterDetection;              // �Ƿ�Я����ͨ�ۼ���Ϣ
        public TIME_SCHEDULE_INFO  stRioterDetection;              // ��ͨ�ۼ�ʱ������

        public int                abFightDetection;               // �Ƿ�Я����ͨ�����Ϣ
        public TIME_SCHEDULE_INFO  stFightDetection;               // ��ͨ���ʱ������

        public int                abRetrogradeDetection;          // �Ƿ�Я����ͨ������Ϣ
        public TIME_SCHEDULE_INFO  stRetrogradeDetection;          // ��ͨ����ʱ������

        public int                abFireDetection;                // �Ƿ�Я����ͨ������Ϣ
        public TIME_SCHEDULE_INFO  stFireDetection;                // ��ͨ����ʱ������

        public int                abSmokeDetection;               // �Ƿ�Я����ͨ������Ϣ
        public TIME_SCHEDULE_INFO  stSmokeDetection;               // ��ͨ����ʱ������

        public int                abNumberStat;                   // �Ƿ�Я����ͨ����ͳ����Ϣ
        public TIME_SCHEDULE_INFO  stNumberStat;                   // ��ͨ����ͳ��ʱ������

        public int                abVideoAbnormalDetection;       // �Ƿ�Я����ͨ��Ƶ�쳣��Ϣ
        public TIME_SCHEDULE_INFO  stVideoAbnormalDetection;       // ��ͨ��Ƶ�쳣ʱ������

        public int                abPrisonerRiseDetection;        // �Ƿ�Я��������������������Ϣ
        public TIME_SCHEDULE_INFO  stPrisonerRiseDetection;        // ����������������ʱ������

        public int                abFaceDetection;                // �Ƿ�Я�����������Ϣ
        public TIME_SCHEDULE_INFO  stFaceDetection;                // �������ʱ������

        public int                abFaceRecognition;              // �Ƿ�Я������ʶ����Ϣ
        public TIME_SCHEDULE_INFO  stFaceRecognition;              // ����ʶ��ʱ������

        public int                abDensityDetection;             // �Ƿ�Я���ܼ��ȼ����Ϣ
        public TIME_SCHEDULE_INFO  stDensityDetection;             // �ܼ��ȼ��ʱ������

        public int                abQueueDetection;               // �Ƿ�Я���ŶӼ����Ϣ
        public TIME_SCHEDULE_INFO  stQueueDetection;               // �ŶӼ��ʱ������

        public int                abClimbDetection;               // �Ƿ�Я���ʸ߼����Ϣ
        public TIME_SCHEDULE_INFO  stClimbDetection;               // �ʸ�ʱ������

        public int                abLeaveDetection;               // �Ƿ�Я����ڼ����Ϣ
        public TIME_SCHEDULE_INFO  stLeaveDetection;               // ���ʱ������

        public int                abVehicleOnPoliceCar;           // �Ƿ�Я�����ؾ�����Ϣ
        public TIME_SCHEDULE_INFO  stVehicleOnPoliceCar;           // ���ؾ���ʱ������

        public int                abVehicleOnBus;                 // �Ƿ�Я�����ع�����Ϣ
        public TIME_SCHEDULE_INFO  stVehicleOnBus;                 // ���ع���ʱ������

        public int                abVehicleOnSchoolBus;           // �Ƿ�Я������У����Ϣ
        public TIME_SCHEDULE_INFO  stVehicleOnSchoolBus;           // ����У��ʱ������  
    }
    
    // ��ͨȫ�����ö�ӦͼƬ������ʽ��������
    public static class TRAFFIC_NAMING_FORMAT extends Structure {
    	public byte[]               szFormat = new byte[CFG_COMMON_STRING_256];            // ͼƬ��ʽ
    }
    
    // CFG_NET_TIME ʱ��
    public static class CFG_NET_TIME extends Structure {
    	public int                 	nStructSize;
        public int					dwYear;					// ��
        public int					dwMonth;				// ��
        public int					dwDay;					// ��
        public int					dwHour;					// ʱ
        public int					dwMinute;				// ��
        public int					dwSecond;				// ��
        
        public CFG_NET_TIME() {
        	this.nStructSize = this.size();
        }
    }
    
    // PERIOD_OF_VALIDITY
    public static class PERIOD_OF_VALIDITY extends Structure {
        public CFG_NET_TIME            stBeginTime;                    // �궨��ʼʱ�� 
        public CFG_NET_TIME            stEndTime;                      // �궨����ʱ��
    }
    
    // ��ͨȫ�����ö�Ӧ�궨�������
    public static class TRAFFIC_CALIBRATION_INFO extends Structure {
    	public byte[]               szUnit = new byte[CFG_COMMON_STRING_256];              // �궨��λ
    	public byte[]               szCertificate = new byte[CFG_COMMON_STRING_256];       // �궨֤��
        public PERIOD_OF_VALIDITY   stPeriodOfValidity;                         // �궨��Ч��
    }
    
    // TRAFFIC_EVENT_CHECK_MASK
    public static class TRAFFIC_EVENT_CHECK_MASK extends Structure {
        public int                abTrafficGate;                  // �Ƿ�Я����ͨ������Ϣ
        public int                 nTrafficGate;                   // ��ͨ���ڼ��ģʽ����

        public int                abTrafficJunction;              // �Ƿ�Я����ͨ·����Ϣ
        public int                 nTrafficJunction;               // ��ͨ·�ڼ��ģʽ����

        public int                abTrafficTollGate;              // �Ƿ�Я���½�ͨ������Ϣ
        public int                 nTrafficTollGate;               // �½�ͨ���ڼ��ģʽ����

        public int                abTrafficRunRedLight;           // �Ƿ�Я����ͨ�������Ϣ
        public int                 nTrafficRunRedLight;            // ��ͨ����Ƽ��ģʽ����
        
        public int                abTrafficRunYellowLight;        // �Ƿ�Я����ͨ���Ƶ���Ϣ
        public int                 nTrafficRunYellowLight;         // ��ͨ���ƵƼ��ģʽ����

        public int                abTrafficOverLine;              // �Ƿ�Я����ͨѹ����Ϣ
        public int                 nTrafficOverLine;               // ��ͨѹ�߼��ģʽ����

        public int                abTrafficOverYellowLine;        // �Ƿ�Я����ͨѹ������Ϣ
        public int                 nTrafficOverYellowLine;         // ��ͨѹ���߼��ģʽ����

        public int                abTrafficRetrograde;            // �Ƿ�Я����ͨ������Ϣ
        public int                 nTrafficRetrograde;             // ��ͨ���м��ģʽ����

        public int                abTrafficTurnLeft;              // �Ƿ�Я����ͨΥ����ת��Ϣ
        public int                 nTrafficTurnLeft;               // ��ͨΥ����ת���ģʽ����

        public int                abTrafficTurnRight;             // �Ƿ�Я����ͨΥ����ת��Ϣ
        public int                 nTrafficTurnRight;              // ��ͨ·��Υ����ת����

        public int                abTrafficU_Turn;                // �Ƿ�Я����ͨΥ�µ�ͷ��Ϣ
        public int                 nTrafficU_Turn;                 // ��ͨΥ�µ�ͷ���ģʽ����

        public int                abTrafficCrossLane;             // �Ƿ�Я����ͨΥ�±����Ϣ
        public int                 nTrafficCrossLane;              // ��ͨΥ�±�����ģʽ����

        public int                abTrafficParking;               // �Ƿ�Я����ͨΥ��ͣ����Ϣ
        public int                 nTrafficParking;                // ��ͨΥ��ͣ�����ģʽ����

        public int                abTrafficJam;                   // �Ƿ�Я����ͨӵ����Ϣ
        public int                 nTrafficJam;                    // ��ͨӵ�¼��ģʽ����

        public int                abTrafficIdle;                  // �Ƿ�Я����ͨ��ͨ������Ϣ
        public int                 nTrafficIdle;                   // ��ͨ��ͨ���м��ģʽ����

        public int                abTrafficWaitingArea;           // �Ƿ�Я����ͨΥ��ʻ���������Ϣ
        public int                 nTrafficWaitingArea;            // ��ͨΥ��ʻ����������ģʽ����

        public int                abTrafficUnderSpeed;            // �Ƿ�Я����ͨǷ����Ϣ
        public int                 nTrafficUnderSpeed;             // ��ͨǷ�ټ��ģʽ����

        public int                abTrafficOverSpeed;             // �Ƿ�Я����ͨ������Ϣ
        public int                 nTrafficOverSpeed;              // ��ͨ���ټ��ģʽ����

        public int                abTrafficWrongRoute;            // �Ƿ�Я����ͨ����������ʻ��Ϣ
        public int                 nTrafficWrongRoute;             // ��ͨ����������ʻ���ģʽ����

        public int                abTrafficYellowInRoute;         // �Ƿ�Я����ͨ����ռ����Ϣ
        public int                 nTrafficYellowInRoute;          // ��ͨ����ռ�����ģʽ����

        public int                abTrafficVehicleInRoute;        // �Ƿ�Я����ͨ�г�ռ����Ϣ
        public int                 nTrafficVehicleInRoute;         // ��ͨ�г�ռ�����ģʽ����

        public int                abTrafficControl;               // �Ƿ�Я����ͨ��ͨ������Ϣ
        public int                 nTrafficControl;                // ��ͨ��ͨ���Ƽ��ģʽ����

        public int                abTrafficObjectAlarm;           // �Ƿ�Я����ָͨ������ץ����Ϣ
        public int                 nTrafficObjectAlarm;            // ��ָͨ������ץ�ļ��ģʽ����

        public int                abTrafficAccident;              // �Ƿ�Я����ͨ��ͨ�¹���Ϣ
        public int                 nTrafficAccident;               // ��ͨ��ͨ�¹ʼ��ģʽ����

        public int                abTrafficStay;                  // �Ƿ�Я����ͨ��ͨͣ��/������Ϣ
        public int                 nTrafficStay;                   // ��ͨ��ͨͣ��/�������ģʽ����

        public int                abTrafficPedestrainPriority;    // �Ƿ�Я����ͨ����������������Ϣ
        public int                 nTrafficPedestrainPriority;     // ��ͨ�������������ȼ��ģʽ����

        public int                abTrafficPedestrain;            // �Ƿ�Я����ͨ��ͨ�����¼���Ϣ
        public int                 nTrafficPedestrain;             // ��ͨ��ͨ�����¼����ģʽ����

        public int                abTrafficThrow;                 // �Ƿ�Я����ͨ��ͨ������Ʒ�¼���Ϣ
        public int                 nTrafficThrow;                  // ��ͨ��ͨ������Ʒ�¼����ģʽ����

        public int                abTrafficVehicleInBusRoute;     // �Ƿ�Я����ͨΥ��ռ����Ϣ
        public int                 nTrafficVehicleInBusRoute;      // ��ͨΥ��ռ�����ģʽ����

        public int                abTrafficBacking;               // �Ƿ�Я����ͨΥ�µ�����Ϣ
        public int                 nTrafficBacking;                // ��ͨΥ�µ������ģʽ����

        public int                abTrafficOverStopLine;          // �Ƿ�Я����ͨѹֹͣ����Ϣ
        public int                 nTrafficOverStopLine;           // ��ͨѹֹͣ�߼��ģʽ����

        public int                abTrafficParkingOnYellowBox;    // �Ƿ�Я����ͨ��������ץ����Ϣ
        public int                 nTrafficParkingOnYellowBox;     // ��ͨ��������ץ�ļ��ģʽ����

        public int                abTrafficParkingSpaceParking;   // �Ƿ�Я����ͨ��λ�г���Ϣ
        public int                 nTrafficParkingSpaceParking;    // ��ͨ��λ�г����ģʽ����

        public int                abTrafficParkingSpaceNoParking; // �Ƿ�Я����ͨ��λ�޳���Ϣ
        public int                 nTrafficParkingSpaceNoParking;  // ��ͨ��λ�޳����ģʽ����

        public int                abTrafficParkingSpaceOverLine;  // �Ƿ�Я����ͨ��λ�г�ѹ����Ϣ
        public int                 nTrafficParkingSpaceOverLine;   // ��ͨ��λ�г�ѹ�߼��ģʽ����

        public int                abParkingSpaceDetection;        // �Ƿ�Я����ͨ��ͣ��λ״̬�����Ϣ
        public int                 nParkingSpaceDetection;         // ��ͨ��ͣ��λ״̬�����ģʽ����

        public int                abTrafficRestrictedPlate;       // �Ƿ�Я����ͨ���޳�����Ϣ
        public int                 nTrafficRestrictedPlate;        // ��ͨ���޳��Ƽ��ģʽ����

        public int                abTrafficWithoutSafeBelt;       // �Ƿ�Я����ͨ��ϵ��ȫ����Ϣ
        public int                 nTrafficWithoutSafeBelt;        // ��ͨ��ϵ��ȫ�����ģʽ����

        public int                abTrafficNoPassing;             // �Ƿ�Я����ͨ������Ϣ
        public int                 nTrafficNoPassing;              // ��ͨ���м��ģʽ����

        public int                abVehicleAnalyse;               // �Ƿ�Я����ͨ����������������Ϣ
        public int                 nVehicleAnalyse;                // ��ͨ�����������ģʽ����

        public int                abCrossLineDetection;           // �Ƿ�Я����ͨ��������Ϣ
        public int                 nCrossLineDetection;            // ��ͨ�����߼��ģʽ����

        public int                abCrossFenceDetection;          // �Ƿ�Я����ͨ��ԽΧ����Ϣ
        public int                 nCrossFenceDetection;           // ��ͨ��ԽΧ�����ģʽ����

        public int                abCrossRegionDetection;         // �Ƿ�Я����ͨ��������Ϣ
        public int                 nCrossRegionDetection;          // ��ͨ���������ģʽ����

        public int                abPasteDetection;               // �Ƿ�Я����ͨATM������Ϣ
        public int                 nPasteDetection;                // ��ͨATM�������ģʽ����

        public int                abLeftDetection;                // �Ƿ�Я����ͨ��Ʒ������Ϣ
        public int                 nLeftDetection;                 // ��ͨ��Ʒ�������ģʽ����

        public int                abPreservation;                 // �Ƿ�Я����ͨ��Ʒ��ȫ��Ϣ
        public int                 nPreservation;                  // ��ͨ��Ʒ��ȫ���ģʽ����

        public int                abTakenAwayDetection;           // �Ƿ�Я����ͨ��Ʒ������Ϣ
        public int                 nTakenAwayDetection;            // ��ͨ��Ʒ���Ƽ��ģʽ����

        public int                abStayDetection;                // �Ƿ�Я����ͨͣ��/������Ϣ
        public int                 nStayDetection;                 // ��ͨͣ��/�������ģʽ����

        public int                abParkingDetection;             // �Ƿ�Я����ͨ�Ƿ�ͣ����Ϣ
        public int                 nParkingDetection;              // ��ͨ�Ƿ�ͣ�����ģʽ����

        public int                abWanderDetection;              // �Ƿ�Я����ͨ�ǻ���Ϣ
        public int                 nWanderDetection;               // ��ͨ�ǻ����ģʽ����

        public int                abMoveDetection;                // �Ƿ�Я����ͨ�˶���Ϣ
        public int                 nMoveDetection;                 // ��ͨ�˶����ģʽ����

        public int                abTailDetection;                // �Ƿ�Я����ͨβ����Ϣ
        public int                 nTailDetection;                 // ��ͨβ����ģʽ����

        public int                abRioterDetection;              // �Ƿ�Я����ͨ�ۼ���Ϣ
        public int                 nRioterDetection;               // ��ͨ�ۼ����ģʽ����

        public int                abFightDetection;               // �Ƿ�Я����ͨ�����Ϣ
        public int                 nFightDetection;                // ��ͨ��ܼ��ģʽ����

        public int                abRetrogradeDetection;          // �Ƿ�Я����ͨ������Ϣ
        public int                 nRetrogradeDetection;           // ��ͨ���м��ģʽ����

        public int                abFireDetection;                // �Ƿ�Я����ͨ������Ϣ
        public int                 nFireDetection;                 // ��ͨ������ģʽ����

        public int                abSmokeDetection;               // �Ƿ�Я����ͨ������Ϣ
        public int                 nSmokeDetection;                // ��ͨ������ģʽ����

        public int                abNumberStat;                   // �Ƿ�Я����ͨ����ͳ����Ϣ
        public int                 nNumberStat;                    // ��ͨ����ͳ�Ƽ��ģʽ����

        public int                abVideoAbnormalDetection;       // �Ƿ�Я����ͨ��Ƶ�쳣��Ϣ
        public int                 nVideoAbnormalDetection;        // ��ͨ��Ƶ�쳣���ģʽ����

        public int                abPrisonerRiseDetection;        // �Ƿ�Я��������������������Ϣ
        public int                 nPrisonerRiseDetection;         // ������������������ģʽ����

        public int                abFaceDetection;                // �Ƿ�Я�����������Ϣ
        public int                 nFaceDetection;                 // ���������ģʽ����

        public int                abFaceRecognition;              // �Ƿ�Я������ʶ����Ϣ
        public int                 nFaceRecognition;               // ����ʶ����ģʽ����

        public int                abDensityDetection;             // �Ƿ�Я���ܼ��ȼ����Ϣ
        public int                 nDensityDetection;              // �ܼ��ȼ����ģʽ����

        public int                abQueueDetection;               // �Ƿ�Я���ŶӼ����Ϣ
        public int                 nQueueDetection;                // �ŶӼ����ģʽ����

        public int                abClimbDetection;               // �Ƿ�Я���ʸ߼����Ϣ
        public int                 nClimbDetection;                // �ʸ߼��ģʽ����

        public int                abLeaveDetection;               // �Ƿ�Я����ڼ����Ϣ
        public int                 nLeaveDetection;                // ��ڼ��ģʽ����

        public int                abVehicleOnPoliceCar;           // �Ƿ�Я�����ؾ�����Ϣ
        public int                 nVehicleOnPoliceCar;            // ���ؾ������ģʽ����

        public int                abVehicleOnBus;                 // �Ƿ�Я�����ع�����Ϣ
        public int                 nVehicleOnBus;                  // ���ع������ģʽ����

        public int                abVehicleOnSchoolBus;           // �Ƿ�Я������У����Ϣ
        public int                 nVehicleOnSchoolBus;            // ����У�����ģʽ����  
        
    }
    
    // ��ͨȫ�����ö�Ӧ����״̬����
    public static class ENABLE_LIGHT_STATE_INFO extends Structure {
    	public int 				bEnable;      // �Ƿ�����Ӧ�ò��յ��ĵ���״̬���ײ�
    }
    
    // �����������
    public static class EM_CHECK_TYPE extends Structure {
    	public int EM_CHECK_TYPE_UNKNOWN = 0;             // ��ʶ��ļ������
    	public int EM_CHECK_TYPE_PHYSICAL = 1;            // ������
    	public int EM_CHECK_TYPE_VIDEO = 2;               // ��Ƶ���
    }
    
    // TRAFFIC_EVENT_CHECK_INFO
    public static class TRAFFIC_EVENT_CHECK_INFO extends Structure {
        public int       abTrafficGate;                  // �Ƿ�Я����ͨ������Ϣ
        public int       emTrafficGate;                  // ��ͨ���ڼ������ EM_CHECK_TYPE

        public int       abTrafficJunction;              // �Ƿ�Я����ͨ·����Ϣ
        public int       emTrafficJunction;              // ��ͨ·�ڼ������

        public int       abTrafficTollGate;              // �Ƿ�Я���½�ͨ������Ϣ
        public int       emTrafficTollGate;              // �½�ͨ���ڼ������

        public int       abTrafficRunRedLight;           // �Ƿ�Я����ͨ�������Ϣ
        public int       emTrafficRunRedLight;           // ��ͨ����Ƽ������
        
        public int       abTrafficRunYellowLight;        // �Ƿ�Я����ͨ���Ƶ���Ϣ
        public int       emTrafficRunYellowLight;        // ��ͨ���ƵƼ������

        public int       abTrafficOverLine;              // �Ƿ�Я����ͨѹ����Ϣ
        public int       emTrafficOverLine;              // ��ͨѹ�߼������

        public int       abTrafficOverYellowLine;        // �Ƿ�Я����ͨѹ������Ϣ
        public int       emTrafficOverYellowLine;        // ��ͨѹ���߼������

        public int       abTrafficRetrograde;            // �Ƿ�Я����ͨ������Ϣ
        public int       emTrafficRetrograde;            // ��ͨ���м������

        public int       abTrafficTurnLeft;              // �Ƿ�Я����ͨΥ����ת��Ϣ
        public int       emTrafficTurnLeft;              // ��ͨΥ����ת�������

        public int       abTrafficTurnRight;             // �Ƿ�Я����ͨΥ����ת��Ϣ
        public int       emTrafficTurnRight;             // ��ͨ·��Υ����ת����

        public int       abTrafficU_Turn;                // �Ƿ�Я����ͨΥ�µ�ͷ��Ϣ
        public int       emTrafficU_Turn;                // ��ͨΥ�µ�ͷ�������

        public int       abTrafficCrossLane;             // �Ƿ�Я����ͨΥ�±����Ϣ
        public int       emTrafficCrossLane;             // ��ͨΥ�±���������

        public int       abTrafficParking;               // �Ƿ�Я����ͨΥ��ͣ����Ϣ
        public int       emTrafficParking;               // ��ͨΥ��ͣ���������

        public int       abTrafficJam;                   // �Ƿ�Я����ͨӵ����Ϣ
        public int       emTrafficJam;                   // ��ͨӵ�¼������

        public int       abTrafficIdle;                  // �Ƿ�Я����ͨ��ͨ������Ϣ
        public int       emTrafficIdle;                  // ��ͨ��ͨ���м������

        public int       abTrafficWaitingArea;           // �Ƿ�Я����ͨΥ��ʻ���������Ϣ
        public int       emTrafficWaitingArea;           // ��ͨΥ��ʻ��������������

        public int       abTrafficUnderSpeed;            // �Ƿ�Я����ͨǷ����Ϣ
        public int       emTrafficUnderSpeed;            // ��ͨǷ�ټ������

        public int       abTrafficOverSpeed;             // �Ƿ�Я����ͨ������Ϣ
        public int       emTrafficOverSpeed;             // ��ͨ���ټ������

        public int       abTrafficWrongRoute;            // �Ƿ�Я����ͨ����������ʻ��Ϣ
        public int       emTrafficWrongRoute;            // ��ͨ����������ʻ�������

        public int       abTrafficYellowInRoute;         // �Ƿ�Я����ͨ����ռ����Ϣ
        public int       emTrafficYellowInRoute;         // ��ͨ����ռ���������

        public int       abTrafficVehicleInRoute;        // �Ƿ�Я����ͨ�г�ռ����Ϣ
        public int       emTrafficVehicleInRoute;        // ��ͨ�г�ռ���������

        public int       abTrafficControl;               // �Ƿ�Я����ͨ��ͨ������Ϣ
        public int       emTrafficControl;               // ��ͨ��ͨ���Ƽ������

        public int       abTrafficObjectAlarm;           // �Ƿ�Я����ָͨ������ץ����Ϣ
        public int       emTrafficObjectAlarm;           // ��ָͨ������ץ�ļ������

        public int       abTrafficAccident;              // �Ƿ�Я����ͨ��ͨ�¹���Ϣ
        public int       emTrafficAccident;              // ��ͨ��ͨ�¹ʼ������

        public int       abTrafficStay;                  // �Ƿ�Я����ͨ��ͨͣ��/������Ϣ
        public int       emTrafficStay;                  // ��ͨ��ͨͣ��/�����������

        public int       abTrafficPedestrainPriority;    // �Ƿ�Я����ͨ����������������Ϣ
        public int       emTrafficPedestrainPriority;    // ��ͨ�������������ȼ������

        public int       abTrafficPedestrain;            // �Ƿ�Я����ͨ��ͨ�����¼���Ϣ
        public int       emTrafficPedestrain;            // ��ͨ��ͨ�����¼��������

        public int       abTrafficThrow;                 // �Ƿ�Я����ͨ��ͨ������Ʒ�¼���Ϣ
        public int       emTrafficThrow;                 // ��ͨ��ͨ������Ʒ�¼��������

        public int       abTrafficVehicleInBusRoute;     // �Ƿ�Я����ͨΥ��ռ����Ϣ
        public int       emTrafficVehicleInBusRoute;     // ��ͨΥ��ռ���������

        public int       abTrafficBacking;               // �Ƿ�Я����ͨΥ�µ�����Ϣ
        public int       emTrafficBacking;               // ��ͨΥ�µ����������

        public int       abTrafficOverStopLine;          // �Ƿ�Я����ͨѹֹͣ����Ϣ
        public int       emTrafficOverStopLine;          // ��ͨѹֹͣ�߼������

        public int       abTrafficParkingOnYellowBox;    // �Ƿ�Я����ͨ��������ץ����Ϣ
        public int       emTrafficParkingOnYellowBox;    // ��ͨ��������ץ�ļ������

        public int       abTrafficParkingSpaceParking;   // �Ƿ�Я����ͨ��λ�г���Ϣ
        public int       emTrafficParkingSpaceParking;   // ��ͨ��λ�г��������

        public int       abTrafficParkingSpaceNoParking; // �Ƿ�Я����ͨ��λ�޳���Ϣ
        public int       emTrafficParkingSpaceNoParking; // ��ͨ��λ�޳��������

        public int       abTrafficParkingSpaceOverLine;  // �Ƿ�Я����ͨ��λ�г�ѹ����Ϣ
        public int       emTrafficParkingSpaceOverLine;  // ��ͨ��λ�г�ѹ�߼������

        public int       abParkingSpaceDetection;        // �Ƿ�Я����ͨ��ͣ��λ״̬�����Ϣ
        public int       emParkingSpaceDetection;        // ��ͨ��ͣ��λ״̬���������

        public int       abTrafficRestrictedPlate;       // �Ƿ�Я����ͨ���޳�����Ϣ
        public int       emTrafficRestrictedPlate;       // ��ͨ���޳��Ƽ������

        public int       abTrafficWithoutSafeBelt;       // �Ƿ�Я����ͨ��ϵ��ȫ����Ϣ
        public int       emTrafficWithoutSafeBelt;       // ��ͨ��ϵ��ȫ���������

        public int       abTrafficNoPassing;             // �Ƿ�Я����ͨ������Ϣ
        public int       emTrafficNoPassing;             // ��ͨ���м������

        public int       abVehicleAnalyse;               // �Ƿ�Я����ͨ����������������Ϣ
        public int       emVehicleAnalyse;               // ��ͨ���������������

        public int       abCrossLineDetection;           // �Ƿ�Я����ͨ��������Ϣ
        public int       emCrossLineDetection;           // ��ͨ�����߼������

        public int       abCrossFenceDetection;          // �Ƿ�Я����ͨ��ԽΧ����Ϣ
        public int       emCrossFenceDetection;          // ��ͨ��ԽΧ���������

        public int       abCrossRegionDetection;         // �Ƿ�Я����ͨ��������Ϣ
        public int       emCrossRegionDetection;         // ��ͨ�������������

        public int       abPasteDetection;               // �Ƿ�Я����ͨATM������Ϣ
        public int       emPasteDetection;               // ��ͨATM�����������

        public int       abLeftDetection;                // �Ƿ�Я����ͨ��Ʒ������Ϣ
        public int       emLeftDetection;                // ��ͨ��Ʒ�����������

        public int       abPreservation;                 // �Ƿ�Я����ͨ��Ʒ��ȫ��Ϣ
        public int       emPreservation;                 // ��ͨ��Ʒ��ȫ�������

        public int       abTakenAwayDetection;           // �Ƿ�Я����ͨ��Ʒ������Ϣ
        public int       emTakenAwayDetection;           // ��ͨ��Ʒ���Ƽ������

        public int       abStayDetection;                // �Ƿ�Я����ͨͣ��/������Ϣ
        public int       emStayDetection;                // ��ͨͣ��/�����������

        public int       abParkingDetection;             // �Ƿ�Я����ͨ�Ƿ�ͣ����Ϣ
        public int       emParkingDetection;             // ��ͨ�Ƿ�ͣ���������

        public int       abWanderDetection;              // �Ƿ�Я����ͨ�ǻ���Ϣ
        public int       emWanderDetection;              // ��ͨ�ǻ��������

        public int       abMoveDetection;                // �Ƿ�Я����ͨ�˶���Ϣ
        public int       emMoveDetection;                // ��ͨ�˶��������

        public int       abTailDetection;                // �Ƿ�Я����ͨβ����Ϣ
        public int       emTailDetection;                // ��ͨβ��������

        public int       abRioterDetection;              // �Ƿ�Я����ͨ�ۼ���Ϣ
        public int       emRioterDetection;              // ��ͨ�ۼ��������

        public int       abFightDetection;               // �Ƿ�Я����ͨ�����Ϣ
        public int       emFightDetection;               // ��ͨ��ܼ������

        public int       abRetrogradeDetection;          // �Ƿ�Я����ͨ������Ϣ
        public int       emRetrogradeDetection;          // ��ͨ���м������

        public int       abFireDetection;                // �Ƿ�Я����ͨ������Ϣ
        public int       emFireDetection;                // ��ͨ����������

        public int       abSmokeDetection;               // �Ƿ�Я����ͨ������Ϣ
        public int       emSmokeDetection;               // ��ͨ����������

        public int       abNumberStat;                   // �Ƿ�Я����ͨ����ͳ����Ϣ
        public int       emNumberStat;                   // ��ͨ����ͳ�Ƽ������

        public int       abVideoAbnormalDetection;       // �Ƿ�Я����ͨ��Ƶ�쳣��Ϣ
        public int       emVideoAbnormalDetection;       // ��ͨ��Ƶ�쳣�������

        public int       abPrisonerRiseDetection;        // �Ƿ�Я��������������������Ϣ
        public int       emPrisonerRiseDetection;        // ����������������������

        public int       abFaceDetection;                // �Ƿ�Я�����������Ϣ
        public int       emFaceDetection;                // �������������

        public int       abFaceRecognition;              // �Ƿ�Я������ʶ����Ϣ
        public int       emFaceRecognition;              // ����ʶ��������

        public int       abDensityDetection;             // �Ƿ�Я���ܼ��ȼ����Ϣ
        public int       emDensityDetection;             // �ܼ��ȼ��������

        public int       abQueueDetection;               // �Ƿ�Я���ŶӼ����Ϣ
        public int       emQueueDetection;               // �ŶӼ��������

        public int       abClimbDetection;               // �Ƿ�Я���ʸ߼����Ϣ
        public int       emClimbDetection;               // �ʸ߼������

        public int       abLeaveDetection;               // �Ƿ�Я����ڼ����Ϣ
        public int       emLeaveDetection;               // ��ڼ������

        public int       abVehicleOnPoliceCar;           // �Ƿ�Я�����ؾ�����Ϣ
        public int       emVehicleOnPoliceCar;           // ���ؾ����������

        public int       abVehicleOnBus;                 // �Ƿ�Я�����ع�����Ϣ
        public int       emVehicleOnBus;                 // ���ع����������

        public int       abVehicleOnSchoolBus;           // �Ƿ�Я������У����Ϣ
        public int       emVehicleOnSchoolBus;           // ����У���������  

    	public int		 abStandUpDetection;		     // �Ƿ�Я��ѧ��������Ϣ
    	public int		 emStandUpDetection;		     // ѧ�������������    	
    }
    
    // MixModeConfig�й��ڳ���������Ϣ
    public static class MIX_MODE_LANE_INFO extends Structure {
    	 public  int                nLaneNum;                           // �������ø���
    	 public  TRAFFIC_EVENT_CHECK_INFO[]   stCheckInfo = (TRAFFIC_EVENT_CHECK_INFO[]) new TRAFFIC_EVENT_CHECK_INFO().toArray(MAX_LANE_CONFIG_NUMBER);     // �������ö�Ӧ�¼������Ϣ
    }
    
    // MixModeConfig ���ģʽΥ������
    public static class MIX_MODE_CONFIG extends Structure {
    	public int                         bLaneDiffEnable;                    // �Ƿ񰴳�������
    	public MIX_MODE_LANE_INFO          stLaneInfo;
    	public TRAFFIC_EVENT_CHECK_INFO    stCheckInfo;
    	
    }
    
    // CFG_CMD_TRAFFICGLOBAL ��ͨȫ���������ñ�
    public static class CFG_TRAFFICGLOBAL_INFO extends Structure 
    {
    	public VIOLATIONCODE_INFO     stViolationCode;                            // Υ�´������ñ�                          
        public int                    bEnableRedList;                             // ʹ�ܺ�������⣬ʹ�ܺ������ڳ���Υ�²��ϱ�

        public int                    abViolationTimeSchedule;                    // �Ƿ�Я��Υ��ץ���Զ���ʱ������
        public VIOLATION_TIME_SCHEDULE stViolationTimeSchedule;                   // Υ��ץ���Զ���ʱ������
        
        public int                    abEnableBlackList;                          // �Ƿ�Я��ʹ�ܺ����������Ϣ
        public int                    bEnableBlackList;                           // ʹ�ܺ��������

        public int                    abPriority;                                 // �Ƿ�Я��Υ�����ȼ�����
        public int            		  nPriority;                                  // Υ�����ȼ�����
        public byte[]                 szPriority = new byte[MAX_PRIORITY_NUMBER*CFG_COMMON_STRING_256]; // Υ�����ȼ�, 0Ϊ������ȼ�    

        public int                    abNamingFormat;                             // �Ƿ�Я��ͼƬ������ʽ����
        public TRAFFIC_NAMING_FORMAT  stNamingFormat;                             	  // ͼƬ������ʽ��������

        public int                    abVideoNamingFormat;                        // �Ƿ�Я��¼��������ʽ����
        public TRAFFIC_NAMING_FORMAT  stVideoNamingFormat;                        // ¼��������ʽ��������

        public int                    abCalibration;                              // �Ƿ�Я���궨��Ϣ
        public TRAFFIC_CALIBRATION_INFO stCalibration;                             // �궨��Ϣ
        
        public int                    abAddress;                                  // �Ƿ�Я����ѯ��ַ����
        public byte[]                 szAddress = new byte[CFG_COMMON_STRING_256];           // ��ѯ��ַ��UTF-8����

        public int                    abTransferPolicy;                           // �Ƿ�Я��������Բ���
        public int      			  emTransferPolicy;                           // �������, EM_TRANSFER_POLICY

        public int                    abSupportModeMaskConfig;                    // �Ƿ�Я��Υ������ 
        public TRAFFIC_EVENT_CHECK_MASK stSupportModeMaskConfig;                   // Υ������֧�ֵļ��ģʽ��������

        public int                    abIsEnableLightState;                       // �Ƿ�Я������״̬
        public ENABLE_LIGHT_STATE_INFO stIsEnableLightState;                       // ��ͨȫ�����ö�ӦͼƬ������ʽ��������
        
        public int                    abMixModeInfo;                              // �Ƿ��л��ģʽ����
        public MIX_MODE_CONFIG         stMixModeInfo;                              // ���ģʽ����
    }
    
    // �ֶ�ץ�Ĳ���
    public static class MANUAL_SNAP_PARAMETER extends Structure
    {
    	public int                   nChannel;               			// ץͼͨ��,��0��ʼ
    	public byte[]                bySequence = new byte[64];	        // ץͼ���к��ַ���
    	public byte[]                byReserved = new byte[60];         // �����ֶ�
    }
    
    // ��Ƶͳ��С����Ϣ
    public static class NET_VIDEOSTAT_SUBTOTAL extends Structure
    {
    	 public int                 nTotal;                         // �豸���к�����ͳ������
         public int                 nHour;                          // Сʱ�ڵ�������
         public int                 nToday;                         // �����������
         public int                 nOSD;							// ͳ������,����OSD��ʾ, ���ֶ����
         public byte[]              reserved = new byte[252];
    }

    // ��Ƶͳ��ժҪ��Ϣ
    public static class NET_VIDEOSTAT_SUMMARY extends Structure
    {
    	public int                     nChannelID;                 	// ͨ����
        public byte[]                  szRuleName = new byte[32];	// ��������
        public NET_TIME_EX             stuTime;                    	// ͳ��ʱ��
        public NET_VIDEOSTAT_SUBTOTAL  stuEnteredSubtotal;         	// ����С��
        public NET_VIDEOSTAT_SUBTOTAL  stuExitedSubtotal;          	// ��ȥС��
        public byte[]                  reserved = new byte[512];
    }

    // CLIENT_AttachVideoStatSummary ���
    public static class NET_IN_ATTACH_VIDEOSTAT_SUM extends Structure
    {
    	 public int                   	dwSize;
         public int                     nChannel;                    // ��Ƶͨ����         
         public fVideoStatSumCallBack   cbVideoStatSum;              // ��Ƶͳ��ժҪ��Ϣ�ص�
         public NativeLong              dwUser;                      // �û�����                  
         
         public NET_IN_ATTACH_VIDEOSTAT_SUM()
         {
        	 this.dwSize = this.size();
         }
    }
    // CLIENT_AttachVideoStatSummary ����
    public static class NET_OUT_ATTACH_VIDEOSTAT_SUM extends Structure
    {
    	public int 					dwSize;
    	
    	public NET_OUT_ATTACH_VIDEOSTAT_SUM()
    	{
    		this.dwSize = this.size();
    	}
   
    }

    // �ӿ�(CLIENT_StartFindNumberStat)�������
    public static class NET_IN_FINDNUMBERSTAT extends Structure 
    {
        public int                 dwSize;                     // �˽ṹ���С
        public int                 nChannelID;                 // Ҫ���в�ѯ��ͨ����
        public NET_TIME            stStartTime;                // ��ʼʱ�� ��ʱ��ȷ��Сʱ
        public NET_TIME            stEndTime;                  // ����ʱ�� ��ʱ��ȷ��Сʱ
        public int                 nGranularityType;           // ��ѯ����0:����,1:Сʱ,2:��,3:��,4:��,5:��,6:��
        public int                 nWaittime;                  // �ȴ��������ݵĳ�ʱʱ��
        
        public NET_IN_FINDNUMBERSTAT() {
        	this.dwSize = this.size();
        }
    }

    // �ӿ�(CLIENT_StartFindNumberStat)�������
    public static class NET_OUT_FINDNUMBERSTAT extends Structure 
    {
        public int               dwSize;                     // �˽ṹ���С
        public int               dwTotalCount;               // ���ϴ˴β�ѯ�����Ľ��������
        
        public NET_OUT_FINDNUMBERSTAT() {
        	this.dwSize = this.size();
		}
    }


    // �ӿ�(CLIENT_DoFindNumberStat)�������
    public static class NET_IN_DOFINDNUMBERSTAT extends Structure 
    {
        public int               dwSize;                     // �˽ṹ���С
        public int        		 nBeginNumber;               // [0, totalCount-1], ��ѯ��ʼ���,��ʾ��beginNumber����¼��ʼ,ȡcount����¼����; 
        public int        		 nCount;                     // ÿ�β�ѯ������ͳ������
        public int               nWaittime;                  // �ȴ��������ݵĳ�ʱʱ��            
        
        public NET_IN_DOFINDNUMBERSTAT() {
        	this.dwSize = this.size();
		}
    }

    public static class NET_NUMBERSTAT extends Structure 
    {
        public int      dwSize;
        public int      nChannelID;                           	  //ͳ��ͨ����
        public byte[]   szRuleName = new byte[NET_CHAN_NAME_LEN]; //��������
        public NET_TIME stuStartTime;                        	  //��ʼʱ��
        public NET_TIME stuEndTime;                          	  //����ʱ��
        public int      nEnteredSubTotal;                    	  //��������С��
        public int      nExitedSubtotal;                     	  //��ȥ����С��
        public int      nAvgInside;                          	  //ƽ����������(��ȥ��ֵ)
        public int      nMaxInside;                         	  //���������
        public int      nEnteredWithHelmet;                		  //����ȫñ��������С��
        public int      nEnteredWithoutHelmet;                	  //������ȫñ��������С��
        public int      nExitedWithHelmet;                        //����ȫñ��ȥ����С��
        public int      nExitedWithoutHelmet;                     //������ȫñ��ȥ����С��
        
        public NET_NUMBERSTAT() {
        	this.dwSize = this.size();
        }
        
        public static class ByReference extends NET_NUMBERSTAT implements Structure.ByReference { }
    }

    // �ӿ�(CLIENT_DoFindNumberStat)�������
    public static class NET_OUT_DOFINDNUMBERSTAT extends Structure 
    {
        public int                          dwSize;                // �˽ṹ���С
        public int                 			nCount;                // ��ѯ��������ͳ����Ϣ����
        public Pointer   					pstuNumberStat;        // ��������ͳ����Ϣ����, NET_NUMBERSTAT ���� 
        public int                 			nBufferLen;            // �û�������ڴ��С,��NET_NUMBERSTAT�е�dwsize��СΪ��λ
    
        public NET_OUT_DOFINDNUMBERSTAT() {
        	this.dwSize = this.size();
        }
    }
    
    public static class CONNECT_STATE extends Structure
    {
        public static final int CONNECT_STATE_UNCONNECT = 0;
        public static final int CONNECT_STATE_CONNECTING = 1;
        public static final int CONNECT_STATE_CONNECTED = 2;
        public static final int CONNECT_STATE_ERROR = 255;
    }

    // ��������ͷ״̬��ѯ
    public static class NET_DEV_VIRTUALCAMERA_STATE_INFO extends Structure
    {
        public int 				 nStructSize;							 //�ṹ���С
        public int 				 nChannelID;							 //ͨ����
        public int 			 	 emConnectState;						 //����״̬, ȡֵ��ΧΪCONNECT_STATE�е�ֵ
        public int 				 uiPOEPort;								 //����������ͷ�����ӵ�POE�˿ں�,0��ʾ����POE����, ����Ϊunsigned int
        public byte[] 			 szDeviceName = new byte[64];			 //�豸����
        public byte[] 			 szDeviceType = new byte[128];			 //�豸����
        public byte[] 			 szSystemType = new byte[128];			 //ϵͳ�汾
        public byte[] 			 szSerialNo = new byte[NET_SERIALNO_LEN];//���к�
        public int				 nVideoInput;							 //��Ƶ����
        public int 				 nAudioInput;							 //��Ƶ����
        public int     			 nAlarmOutput;							 //�ⲿ����
        
        public NET_DEV_VIRTUALCAMERA_STATE_INFO()
        {
        	this.nStructSize = this.size();
        }
    }
    
    // ¼���ļ�����
    public static class NET_RECORD_TYPE extends Structure
    {
    	public final static int NET_RECORD_TYPE_ALL = 0; 		 // ����¼��
    	public final static int NET_RECORD_TYPE_NORMAL = 1; 	 // ��ͨ¼��
    	public final static int NET_RECORD_TYPE_ALARM = 2; 		 // �ⲿ����¼��
    	public final static int NET_RECORD_TYPE_MOTION = 3; 	 // ���챨��¼��
    }
    
    // �Խ���ʽ
    public static class EM_USEDEV_MODE extends Structure
    {
    	public static final int NET_TALK_CLIENT_MODE 	  = 0;   // ���ÿͻ��˷�ʽ���������Խ�
    	public static final int NET_TALK_SERVER_MODE 	  = 1;   // ���÷�������ʽ���������Խ�
    	public static final int NET_TALK_ENCODE_TYPE 	  = 2;   // ���������Խ������ʽ(��Ӧ NETDEV_TALKDECODE_INFO)
    	public static final int NET_ALARM_LISTEN_MODE 	  = 3;   // ���ñ������ķ�ʽ
    	public static final int NET_CONFIG_AUTHORITY_MODE = 4;   // ����ͨ��Ȩ�޽������ù���
    	public static final int NET_TALK_TALK_CHANNEL 	  = 5;   // ���öԽ�ͨ��(0~MaxChannel-1)
    	public static final int NET_RECORD_STREAM_TYPE	  = 6;   // ���ô���ѯ����ʱ��طŵ�¼����������(0-��������,1-������,2-������)  
    	public static final int NET_TALK_SPEAK_PARAM      = 7;   // ���������Խ���������,��Ӧ�ṹ�� NET_SPEAK_PARAM
    	public static final int NET_RECORD_TYPE           = 8;   // ���ð�ʱ��¼��طż����ص�¼���ļ�����(���  NET_RECORD_TYPE)
    	public static final int NET_TALK_MODE3            = 9;   // ���������豸�������Խ�����, ��Ӧ�ṹ�� NET_TALK_EX
    	public static final int NET_PLAYBACK_REALTIME_MODE = 10; // ����ʵʱ�طŹ���(0-�ر�,1����)
    	public static final int NET_TALK_TRANSFER_MODE    = 11;  // ���������Խ��Ƿ�Ϊת��ģʽ, ��Ӧ�ṹ�� NET_TALK_TRANSFER_PARAM
    	public static final int NET_TALK_VT_PARAM         = 12;  // ����VT�Խ�����, ��Ӧ�ṹ�� NET_VT_TALK_PARAM
    	public static final int NET_TARGET_DEV_ID         = 13;  // ����Ŀ���豸��ʾ��, ���Բ�ѯ��ϵͳ����(��0-ת��ϵͳ������Ϣ)
    }
    
    // ������������
    public static class NET_TALK_CODING_TYPE extends Structure 
    {
    	public static final int NET_TALK_DEFAULT = 0;            // ��ͷPCM
    	public static final int NET_TALK_PCM = 1;                // ��ͷPCM
    	public static final int	NET_TALK_G711a = 2;              // G711a
    	public static final int NET_TALK_AMR = 3;                // AMR
    	public static final int	NET_TALK_G711u = 4;              // G711u
    	public static final int	NET_TALK_G726 = 5;               // G726
    	public static final int	NET_TALK_G723_53 = 6;            // G723_53
    	public static final int NET_TALK_G723_63 = 7;            // G723_63
    	public static final int	NET_TALK_AAC = 8;                // AAC
    	public static final int	NET_TALK_OGG = 9;                // OGG
    	public static final int	NET_TALK_G729 = 10;              // G729
    	public static final int	NET_TALK_MPEG2 = 11;             // MPEG2
    	public static final int	NET_TALK_MPEG2_Layer2 = 12;      // MPEG2-Layer2
    	public static final int	NET_TALK_G722_1 = 13;            // G.722.1
    	public static final int	NET_TALK_ADPCM = 21;             // ADPCM
		public static final int	NET_TALK_MP3   = 22;             // MP3
    }
   
    // �豸֧�ֵ������Խ�����
    public static class NETDEV_TALKFORMAT_LIST extends Structure 
    {
    	public int 						 nSupportNum;                                                  				    // ����
        public NETDEV_TALKDECODE_INFO[] type = (NETDEV_TALKDECODE_INFO[])new NETDEV_TALKDECODE_INFO().toArray(64);   // ��������
        
        public byte[] reserved = new byte[64];
    }
    
    // ����������Ϣ  
    public static class NETDEV_TALKDECODE_INFO extends Structure 
    {
    	public int                 encodeType;                       // ��������, encodeType��ӦNET_TALK_CODING_TYPE
        public int                 nAudioBit;                        // λ��,��8��16, Ŀǰֻ����16
        public int                 dwSampleRate;                     // ������,��8000��16000, Ŀǰֻ����16000
        public int                 nPacketPeriod;                    // �������, ��λms, Ŀǰֻ����25
        public byte[]    		   reserved = new byte[60];    
    }
    
    // �����Խ���������
    public static class NET_SPEAK_PARAM extends Structure 
    {
    	public int 				  dwSize;                     		// �ṹ���С
        public int 				  nMode;                      		// 0���Խ���Ĭ��ģʽ��,1���������Ӻ����л����Խ�Ҫ��������
        public int 				  nSpeakerChannel;           		// ������ͨ����,����ʱ��Ч
        public boolean 			  bEnableWait;            			// �����Խ�ʱ�Ƿ�ȴ��豸����Ӧ,Ĭ�ϲ��ȴ�.TRUE:�ȴ�;FALSE:���ȴ�
                                               						// ��ʱʱ����CLIENT_SetNetworkParam����,��ӦNET_PARAM��nWaittime�ֶ�
        public NET_SPEAK_PARAM()
	    {
	    	this.dwSize = this.size();
	    }
    }
    
    // �Ƿ��������Խ���ת��ģʽ
    public static class NET_TALK_TRANSFER_PARAM extends Structure 
    {
    	public int 				 dwSize;
        public boolean 			 bTransfer;                 	   // �Ƿ��������Խ�ת��ģʽ, TRUE: ����ת��
        
        public NET_TALK_TRANSFER_PARAM()
	    {
	    	this.dwSize = this.size();
	    }
    }
    
    // Ԥ������,��ӦCLIENT_RealPlayEx�ӿ�
    public static class NET_RealPlayType extends Structure
    {
    	public static final int NET_RType_Realplay   = 0; // ʵʱԤ��
    	public static final int NET_RType_Multiplay  = 1; // �໭��Ԥ��
    	public static final int NET_RType_Realplay_0 = 2; // ʵʱ����-������ ,��ͬ��NET_RType_Realplay
    	public static final int NET_RType_Realplay_1 = 3; // ʵʱ����-������1
    	public static final int NET_RType_Realplay_2 = 4; // ʵʱ����-������2
    	public static final int NET_RType_Realplay_3 = 5; // ʵʱ����-������3    
    	public static final int NET_RType_Multiplay_1 = 6; // �໭��Ԥ����1����
    	public static final int NET_RType_Multiplay_4 = 7; // �໭��Ԥ����4����
    	public static final int NET_RType_Multiplay_8 = 8; // �໭��Ԥ����8����
    	public static final int NET_RType_Multiplay_9 = 9; // �໭��Ԥ����9����
    	public static final int NET_RType_Multiplay_16 = 10; // �໭��Ԥ����16����
    	public static final int NET_RType_Multiplay_6 = 11;  // �໭��Ԥ����6����
    	public static final int NET_RType_Multiplay_12 = 12; // �໭��Ԥ����12����
    	public static final int NET_RType_Multiplay_25 = 13; // �໭��Ԥ����25����
    	public static final int NET_RType_Multiplay_36 = 14; // �໭��Ԥ����36����
    }
    
    // �ص���Ƶ����֡��֡�����ṹ��
    public static class tagVideoFrameParam extends Structure
    {
        public byte                  encode;                 // ��������
        public byte                  frametype;              // I = 0, P = 1, B = 2...
        public byte                  format;                 // PAL - 0, NTSC - 1
        public byte                  size;                   // CIF - 0, HD1 - 1, 2CIF - 2, D1 - 3, VGA - 4, QCIF - 5, QVGA - 6 ,
                                                             // SVCD - 7,QQVGA - 8, SVGA - 9, XVGA - 10,WXGA - 11,SXGA - 12,WSXGA - 13,UXGA - 14,WUXGA - 15, LFT - 16, 720 - 17, 1080 - 18 ,1_3M-19
    												 		 // 2M-20, 5M-21;��size=255ʱ����Ա����width,height ��Ч
        public int                   fourcc;                 // �����H264��������Ϊ0������ֵΪ*( DWORD*)"DIVX"����0x58564944
        public short				 width;					 // ����λ�����أ���size=255ʱ��Ч
    	public short				 height;				 // �ߣ���λ�����أ���size=255ʱ��Ч
        public NET_TIME              struTime;               // ʱ����Ϣ
    }
    
    // �ص���Ƶ����֡��֡�����ṹ��
    public static class tagCBPCMDataParam extends Structure 
    {
        public byte                channels;                // ������
        public byte                samples;                 // ���� 0 - 8000, 1 - 11025, 2 - 16000, 3 - 22050, 4 - 32000, 5 - 44100, 6 - 48000
        public byte                depth;                   // ������� ȡֵ8����16�ȡ�ֱ�ӱ�ʾ
        public byte                param1;                  // 0 - ָʾ�޷���,1-ָʾ�з���
        public int                 reserved;                // ����
    }
    
    // ��Ƶ���ӶϿ��¼�����
    public static class EM_REALPLAY_DISCONNECT_EVENT_TYPE extends Structure 
    {
        public static final int DISCONNECT_EVENT_REAVE 		= 0;                 // ��ʾ�߼��û���ռ�ͼ��û���Դ
        public static final int DISCONNECT_EVENT_NETFORBID  = 1;                 // ��ֹ����
        public static final int DISCONNECT_EVENT_SUBCONNECT = 2;                 // ��̬�����ӶϿ�
    }
    
    // ��ص�ѹ���ͱ���
    public static class ALARM_BATTERYLOWPOWER_INFO extends Structure
    {
	    public int dwSize;			//�ṹ���С
	    public int nAction;			//0:��ʼ1:ֹͣ
	    public int nBatteryLeft;	//ʣ������ٷֱ�,��λ%
	    public NET_TIME stTime;		//�¼�����ʱ��
	    public int nChannelID;		//ͨ����,��ʶ���豸���,��0��ʼ
	    
	    public ALARM_BATTERYLOWPOWER_INFO()
	    {
	    	this.dwSize = this.size();
	    }
    }
    
    // �¶ȹ��߱���
    public static class ALARM_TEMPERATURE_INFO extends Structure
    {
	    public int dwSize;//�ṹ���С
	    public byte[] szSensorName = new byte[NET_MACHINE_NAME_NUM];//�¶ȴ���������
	    public int nChannelID;//ͨ����
	    public int nAction;//0:��ʼ1:ֹͣ
	    public float fTemperature;//��ǰ�¶�ֵ,��λ���϶�
	    public NET_TIME stTime;//�¼�����ʱ��
	    
	    public ALARM_TEMPERATURE_INFO()
	    {
	    	this.dwSize = this.size();
	    }
    }
    
    // ��ͨ������Ϣ
    public static class NET_CLIENT_STATE_EX extends Structure 
    {
        public int                channelcount;
        public int                alarminputcount;
        public byte[]             alarm       		= new byte[32];        // �ⲿ����
        public byte[]       	  motiondection 	= new byte[32];        // ��̬���
        public byte[]             videolost 		= new byte[32];        // ��Ƶ��ʧ
        public byte[]             bReserved 		= new byte[32];
    } 
   
    // ��Ƶ�ڵ�����״̬��Ϣ��Ӧ�ṹ��
    public static class NET_CLIENT_VIDEOBLIND_STATE extends Structure
    {
	    public int dwSize;
	    public int channelcount;
	    public int[] dwAlarmState = new int[NET_MAX_CHANMASK];//ÿһ��int��λ��ʾ32ͨ���ı���״̬,0-��ʾ�ޱ���,1-��ʾ�б���
	    
	    public NET_CLIENT_VIDEOBLIND_STATE()
	    {
	    	this.dwSize = this.size();
	    }
    }
    
    // ��Ƶ��ʧ����״̬��Ϣ��Ӧ�ṹ��
    public static class NET_CLIENT_VIDEOLOST_STATE extends Structure
    {
	    public int	    dwSize;
	    public int	    channelcount;
	    public int[]	dwAlarmState = new int[NET_MAX_CHANMASK];//ÿһ��int��λ��ʾ32ͨ���ı���״̬��ֻ��dwAlarmState[0]��Ч��,0-��ʾ�ޱ���,1-��ʾ�б���
	    
	    public NET_CLIENT_VIDEOLOST_STATE()
	    {
	    	this.dwSize = this.size();
	    }
    }
    
    // ������բ����(��Ӧ CTRLTYPE_CTRL_OPEN_STROBE ����)
    public static class NET_CTRL_OPEN_STROBE extends Structure 
    {
    	public int dwSize;
    	public int nChannelId;                      // ͨ���� 
    	public byte[] szPlateNumber = new byte[64]; // ���ƺ���
 
    	public NET_CTRL_OPEN_STROBE() {
    		this.dwSize = this.size();
    	}
    }
    
    // �رյ�բ����(��Ӧ  CTRLTYPE_CTRL_CLOSE_STROBE ����)
    public static class NET_CTRL_CLOSE_STROBE extends Structure 
    {
    	public int	dwSize;
    	public int	nChannelId; // ͨ����
    	
    	public NET_CTRL_CLOSE_STROBE() {
    		this.dwSize = this.size();
    	}
    }
    
    // ����״̬ (��Ӧ   CTRLTYPE_TRIGGER_ALARM_OUT ����)
    public static class ALARMCTRL_PARAM extends Structure 
    {
    	public int	dwSize;
    	public int	nAlarmNo; // ����ͨ����,��0��ʼ
    	public int	nAction; // 1����������,0��ֹͣ����
    	
    	public ALARMCTRL_PARAM() {
    		this.dwSize = this.size();
    	}
    }
    
    // ��ѯ IVS ǰ���豸���
    public static class NET_IN_IVS_REMOTE_DEV_INFO extends Structure 
    {
        public int                     dwSize;                         // �ýṹ���С   
        public int                     nChannel;                       // ͨ����
        
        public NET_IN_IVS_REMOTE_DEV_INFO() {
        	this.dwSize = this.size();
        }
    }
    
    // ��ѯ IVS ǰ���豸����
    public static class NET_OUT_IVS_REMOTE_DEV_INFO extends Structure   
    {
        public int                   dwSize;                         	// �ýṹ���С 
        public int     			     nPort;								// �˿�
        public byte[]				 szIP = new byte[64];	            // �豸IP
        public byte[]				 szUser = new byte[64];	            // �û���
    	public byte[]				 szPassword = new byte[64];         // ����    
        public byte[]				 szAddress = new byte[128];	        // ��������ص�
    
        public NET_OUT_IVS_REMOTE_DEV_INFO() {
        	this.dwSize = this.size();
        }
    }
    
    // ��������Ӧ��ʽö������
    public static class NET_SENSE_METHOD extends Structure
    {
    	public static final int NET_SENSE_UNKNOWN = -1;//δ֪����
    	public static final int NET_SENSE_DOOR	= 0; //�Ŵ�
    	public static final int NET_SENSE_PASSIVEINFRA = 1; //��������
    	public static final int NET_SENSE_GAS = 2; //����
    	public static final int NET_SENSE_SMOKING = 3; //�̸�
    	public static final int	NET_SENSE_WATER = 4; //ˮ��
    	public static final int	NET_SENSE_ACTIVEFRA = 5; //��������
    	public static final int	NET_SENSE_GLASS = 6; //��������
    	public static final int	NET_SENSE_EMERGENCYSWITCH = 7; //��������
    	public static final int	NET_SENSE_SHOCK = 8; //��
    	public static final int	NET_SENSE_DOUBLEMETHOD = 9; //˫��(����+΢��)
    	public static final int	NET_SENSE_THREEMETHOD = 10; //������
    	public static final int	NET_SENSE_TEMP = 11; //�¶�
    	public static final int	NET_SENSE_HUMIDITY = 12; //ʪ��
    	public static final int	NET_SENSE_WIND = 13; //����
    	public static final int	NET_SENSE_CALLBUTTON = 14; //���а�ť
    	public static final int	NET_SENSE_GASPRESSURE = 15; //����ѹ��
    	public static final int	NET_SENSE_GASCONCENTRATION = 16; //ȼ��Ũ��
    	public static final int	NET_SENSE_GASFLOW = 17; //��������
    	public static final int	NET_SENSE_OTHER = 18; //����
    	public static final int	NET_SENSE_OIL = 19; //�������,���͡����͵ȳ������ͼ��
    	public static final int	NET_SENSE_MILEAGE = 20; //��������
    	public static final int	NET_SENSE_URGENCYBUTTON = 21; //������ť
    	public static final int	NET_SENSE_STEAL = 22; //����
    	public static final int	NET_SENSE_PERIMETER = 23; //�ܽ�
    	public static final int	NET_SENSE_PREVENTREMOVE = 24; //����
    	public static final int	NET_SENSE_DOORBELL = 25; //����
    	public static final int	NET_SENSE_ALTERVOLT = 26; //������ѹ������
    	public static final int	NET_SENSE_DIRECTVOLT = 27; //ֱ����ѹ������
    	public static final int	NET_SENSE_ALTERCUR = 28; //��������������
    	public static final int	NET_SENSE_DIRECTCUR = 29; //ֱ������������
    	public static final int	NET_SENSE_RSUGENERAL = 30; //������ͨ��ģ����4~20mA��0~5V
    	public static final int	NET_SENSE_RSUDOOR = 31; //�������Ž���Ӧ
    	public static final int	NET_SENSE_RSUPOWEROFF = 32; //�����˶ϵ��Ӧ
    	public static final int	NET_SENSE_TEMP1500 = 33;//1500�¶ȴ�����
    	public static final int	NET_SENSE_TEMPDS18B20 = 34;//DS18B20�¶ȴ�����
    	public static final int	NET_SENSE_HUMIDITY1500 = 35; //1500ʪ�ȴ�����
    	public static final int	NET_SENSE_NUM = 36; //ö����������
    }
    
    //-------------------------------��������---------------------------------
	// ��̨����
	public static class NET_PTZ_LINK extends Structure
	{
		public int iType;//0-None,1-Preset,2-Tour,3-Pattern
		public int iValue;
	}

	////////////////////////////////HDVRר��//////////////////////////////////
    // ����������չ�ṹ��
    public static class NET_MSG_HANDLE_EX extends Structure
    {
        /* ��Ϣ����ʽ,����ͬʱ���ִ���ʽ,����
         * 0x00000001 - �����ϴ�
         * 0x00000002 - ����¼��
         * 0x00000004 - ��̨����
         * 0x00000008 - �����ʼ�
         * 0x00000010 - ������Ѳ
         * 0x00000020 - ������ʾ
         * 0x00000040 - �������
         * 0x00000080 - Ftp�ϴ�
         * 0x00000100 - ����
         * 0x00000200 - ������ʾ
         * 0x00000400 - ץͼ
        */
		/*��ǰ������֧�ֵĴ���ʽ,��λ�����ʾ*/
		public int dwActionMask;
		/*��������,��λ�����ʾ,���嶯������Ҫ�Ĳ����ڸ��Ե�����������*/
		public int dwActionFlag;
		/*�������������ͨ��,�������������,Ϊ1��ʾ���������*/
		public byte[] byRelAlarmOut = new byte[NET_MAX_ALARMOUT_NUM_EX];
		public int dwDuration;/*��������ʱ��*/
		/*����¼��*/
		public byte[] byRecordChannel = new byte[NET_MAX_VIDEO_IN_NUM_EX];/*����������¼��ͨ��,Ϊ1��ʾ������ͨ��*/
		public int dwRecLatch;/*¼�����ʱ��*/
		/*ץͼͨ��*/
		public byte[] bySnap = new byte[NET_MAX_VIDEO_IN_NUM_EX];
		/*��Ѳͨ��*/
		public byte[] byTour = new byte[NET_MAX_VIDEO_IN_NUM_EX];/*��Ѳͨ��0-31·*/
		/*��̨����*/
		public NET_PTZ_LINK[] struPtzLink = (NET_PTZ_LINK[])new NET_PTZ_LINK().toArray(NET_MAX_VIDEO_IN_NUM_EX);
		public int dwEventLatch;/*������ʼ��ʱʱ��,sΪ��λ,��Χ��0~15,Ĭ��ֵ��0*/
		/*�����������������ͨ��,�������������,Ϊ1��ʾ���������*/
		public byte[] byRelWIAlarmOut = new byte[NET_MAX_ALARMOUT_NUM_EX];
		public byte bMessageToNet;
		public byte bMMSEn;/*���ű���ʹ��*/
		public byte bySnapshotTimes;/*���ŷ���ץͼ����*/
		public byte bMatrixEn;/*!<����ʹ��*/
		public int dwMatrix;/*!<��������*/
		public byte bLog;/*!<��־ʹ��,Ŀǰֻ����WTN��̬�����ʹ��*/
		public byte bSnapshotPeriod;/*!<ץͼ֡���,ÿ������֡ץһ��ͼƬ,һ��ʱ����ץ�ĵ���������ץͼ֡���йء�0��ʾ����֡,����ץ�ġ�*/
		public byte[] byTour2 = new byte[NET_MAX_VIDEO_IN_NUM_EX];/*��Ѳͨ��32-63·*/
		public byte byEmailType;/*<0,ͼƬ����,1,¼�񸽼�>*/
		public byte byEmailMaxLength;/*<����¼��ʱ����󳤶�,��λMB>*/
		public byte byEmailMaxTime;/*<������¼��ʱ���ʱ�䳤��,��λ��>*/
		public byte[] byReserved = new byte[475];
    }

    public static class EM_NET_DEFENCE_AREA_TYPE extends Structure
    {
    	public static final int EM_NET_DEFENCE_AREA_TYPE_UNKNOW = 0; //δ֪
    	public static final int EM_NET_DEFENCE_AREA_TYPE_INTIME = 1; //��ʱ����
    	public static final int EM_NET_DEFENCE_AREA_TYPE_DELAY = 2; //��ʱ����
    	public static final int EM_NET_DEFENCE_AREA_TYPE_FULLDAY = 3; //24Сʱ����
    	public static final int EM_NET_DEFENCE_AREA_TYPE_Follow = 4; //�������
    	public static final int EM_NET_DEFENCE_AREA_TYPE_MEDICAL = 5; //ҽ�ƽ�������
    	public static final int EM_NET_DEFENCE_AREA_TYPE_PANIC = 6; //�ֻŷ���
    	public static final int EM_NET_DEFENCE_AREA_TYPE_FIRE = 7; //�𾯷���
    	public static final int EM_NET_DEFENCE_AREA_TYPE_FULLDAYSOUND = 8; //24Сʱ��������
    	public static final int EM_NET_DEFENCE_AREA_TYPE_FULLDATSLIENT = 9; //24Сʱ��������
    	public static final int EM_NET_DEFENCE_AREA_TYPE_ENTRANCE1 = 10; //�������1
    	public static final int EM_NET_DEFENCE_AREA_TYPE_ENTRANCE2 = 11; //�������2
    	public static final int EM_NET_DEFENCE_AREA_TYPE_INSIDE = 12; //�ڲ�����
    	public static final int EM_NET_DEFENCE_AREA_TYPE_OUTSIDE = 13; //�ⲿ����
    	public static final int EN_NET_DEFENCE_AREA_TYPE_PEOPLEDETECT = 14; //��Ա������
    }

    // ���ر����¼�(��NET_ALARM_ALARM_EX����)
    public static class ALARM_ALARM_INFO_EX2 extends Structure
    {
    	public int dwSize;
    	public int nChannelID;						//ͨ����
    	public int nAction;							//0:��ʼ, 1:ֹͣ
    	public NET_TIME stuTime;					//�����¼�������ʱ��
    	public int emSenseType;						//����������, ȡֵ��ΧΪ  NET_SENSE_METHOD �е�ֵ
    	public NET_MSG_HANDLE_EX stuEventHandler;	//������Ϣ
    	public int emDefenceAreaType;				//��������, ȡֵ����ΪEM_NET_DEFENCE_AREA_TYPE�е�ֵ
    	public int nEventID;					    //�¼�ID
    	public ALARM_ALARM_INFO_EX2() {
    		this.dwSize = this.size();
    	}
    }
    
    // ������״̬�仯�¼�����Ϣ
    public static class ALARM_ARMMODE_CHANGE_INFO extends Structure
    {
        public int                 dwSize;
        public NET_TIME            stuTime;        // �����¼�������ʱ��
        public int                 bArm;           // �仯���״̬,��Ӧ NET_ALARM_MODE
        public int 			       emSceneMode;    // �龰ģʽ����Ӧ  NET_SCENE_MODE
        public int                 dwID;           // ID��, ң������Ż���̵�ַ, emTriggerModeΪNET_EM_TRIGGER_MODE_NET����ʱΪ0
        public int       		   emTriggerMode;  // ������ʽ,��Ӧ  NET_EM_TRIGGER_MODE
        
        public ALARM_ARMMODE_CHANGE_INFO() {
        	this.dwSize = this.size();
        }
    }
    
    // ������ģʽ
    public static class NET_ALARM_MODE extends Structure 
    {
        public static final int NET_ALARM_MODE_UNKNOWN    = -1;          // δ֪
        public static final int NET_ALARM_MODE_DISARMING  = 0;           // ����
        public static final int NET_ALARM_MODE_ARMING	  = 1;           // ����
        public static final int NET_ALARM_MODE_FORCEON	  = 2;           // ǿ�Ʋ���
        public static final int NET_ALARM_MODE_PARTARMING = 3;           // ���ֲ���
    }
    
    // ����������ģʽ
    public static class NET_SCENE_MODE extends Structure 
    {
    	public static final int NET_SCENE_MODE_UNKNOWN   = 0;            // δ֪����
    	public static final int NET_SCENE_MODE_OUTDOOR   = 1;            // ���ģʽ
    	public static final int NET_SCENE_MODE_INDOOR    = 2;            // ����ģʽ
    	public static final int NET_SCENE_MODE_WHOLE     = 3;            // ȫ��ģʽ
    	public static final int NET_SCENE_MODE_RIGHTNOW  = 4;            // ����ģʽ
    	public static final int NET_SCENE_MODE_SLEEPING  = 5;            // ����ģʽ
    	public static final int NET_SCENE_MODE_CUSTOM    = 6;            // �Զ���ģʽ
    }
    
    // ������ʽ
    public static class NET_EM_TRIGGER_MODE extends Structure 
    { 
    	public static final int NET_EM_TRIGGER_MODE_UNKNOWN 		= 0;
    	public static final int NET_EM_TRIGGER_MODE_NET			    = 1;   // �����û�(ƽ̨��Web)
    	public static final int NET_EM_TRIGGER_MODE_KEYBOARD		= 2;   // ����
    	public static final int NET_EM_TRIGGER_MODE_REMOTECONTROL	= 3;   // ң����
    }
    
    // ���������¼�����
    public static class ALARM_RCEMERGENCY_CALL_INFO extends Structure 
    {
    	public int                       dwSize;
	    public int                       nAction;                // -1:δ֪ 0:��ʼ 1:ֹͣ
	    public int                  	 emType;                 // ��������,��Ӧ EM_RCEMERGENCY_CALL_TYPE
	    public NET_TIME                  stuTime;                // �¼�����ʱ��
	    public int   					 emMode;                 // ������ʽ����Ӧ EM_RCEMERGENCY_MODE_TYPE
	    public int                       dwID;                   // ���ڱ�ʾ��ͬ�Ľ����¼�(ֻ��emMode��ң��������ʱ��Ч, ��ʾң�����ı��, 0��ʾ��ЧID)
	    
	    public ALARM_RCEMERGENCY_CALL_INFO() {
	    	this.dwSize = this.size();
	    }
    }
    
    // ���������¼�����
    public static class EM_RCEMERGENCY_CALL_TYPE extends Structure 
    {
    	public static final int EM_RCEMERGENCY_CALL_UNKNOWN   = 0;
    	public static final int EM_RCEMERGENCY_CALL_FIRE	  = 1;             // ��
    	public static final int EM_RCEMERGENCY_CALL_DURESS	  = 2;             // в��
    	public static final int EM_RCEMERGENCY_CALL_ROBBER	  = 3;             // �˾�
    	public static final int EM_RCEMERGENCY_CALL_MEDICAL	  = 4;             // ҽ��
    	public static final int EM_RCEMERGENCY_CALL_EMERGENCY = 5;             // ����
    }
    
    // ������ʽ
    public static class EM_RCEMERGENCY_MODE_TYPE extends Structure 
    {
    	public static final int EM_RCEMERGENCY_MODE_UNKNOWN          = 0;
    	public static final int EM_RCEMERGENCY_MODE_KEYBOARD		 = 1;       // ����
    	public static final int EM_RCEMERGENCY_MODE_WIRELESS_CONTROL = 2;       // ң����
    }
    
    /////////////////////////////////////////////////////
    ////////�û���Ϣ�����Ӧ�ӿ�CLIENT_QueryUserInfoNew/////////
    // �û���Ϣ��
    public static class USER_MANAGE_INFO_NEW extends Structure {
    	public int 						dwSize; 																				 // �ṹ���С
    	public int 						dwRightNum;  																			 // Ȩ����Ϣ��Ч����
    	public OPR_RIGHT_NEW[] 			rightList = (OPR_RIGHT_NEW[])new OPR_RIGHT_NEW().toArray(NET_NEW_MAX_RIGHT_NUM); 		 // Ȩ����Ϣ����Ч������ dwRightNum ��Ա����, �û�Ȩ�޸�������NET_NEW_MAX_RIGHT_NUM = 1024
    	public int 						dwGroupNum; 																			 // �û�����Ϣ��Ч����
    	public USER_GROUP_INFO_NEW[] 	groupList = (USER_GROUP_INFO_NEW[])new USER_GROUP_INFO_NEW().toArray(NET_MAX_GROUP_NUM);  // �û�����Ϣ���˲�����������ʹ��groupListEx
    	public int 						dwUserNum;  																			  // �û���
    	public USER_INFO_NEW[] 			userList = (USER_INFO_NEW[])new USER_INFO_NEW().toArray(NET_MAX_USER_NUM); 				  // �û��б� �û���������NET_MAX_USER_NUM=200
    	public int 						dwFouctionMask; 																		  // ���룺 0x00000001 - ֧���û����ã� 0x00000002 - �����޸���ҪУ��
    	public byte 					byNameMaxLength;  																		  // ֧�ֵ��û�����󳤶�
    	public byte 					byPSWMaxLength; 																		  // ֧�ֵ�������󳤶�
    	public byte[] 					byReserve = new byte[254];
    	public USER_GROUP_INFO_EX2[]    groupListEx = (USER_GROUP_INFO_EX2[])new USER_GROUP_INFO_EX2().toArray(NET_MAX_GROUP_NUM); // �û�����Ϣ��չ, �û����������NET_MAX_GROUP_NUM=20
    	
    	public USER_MANAGE_INFO_NEW() {
    		this.dwSize = this.size();
    	}  	
    }
    
    // Ȩ����Ϣ
    public static class OPR_RIGHT_NEW extends Structure {
    	public int 						dwSize;										  //�ṹ���С
    	public int 						dwID; 										  //Ȩ��ID��ÿ�� Ȩ�޶��и��Ե�ID
    	public byte[] 					name = new byte[NET_RIGHT_NAME_LENGTH]; 	  //���� Ȩ�������� NET_RIGHT_NAME_LENGTH=32
    	public byte[] 					memo = new byte[NET_MEMO_LENGTH];			  //˵����ע����NET_MEMO_LENGTH=32
    	
    	public OPR_RIGHT_NEW() {
    		this.dwSize = this.size();
    	}
    }
    
    // �û�����Ϣ
    public static class USER_GROUP_INFO_NEW extends Structure {
    	public int 						dwSize;
        public int 						dwID; 											// �û���ID�� ÿ���û��鶼�и��Ե�ID
        public byte[] 					name = new byte[NET_USER_NAME_LENGTH_EX]; 		// �û�������/NET_USER_NAME_LENGTH_EX=16
        public int 						dwRightNum; 									// �û���Ȩ����Ч����
        public int[] 					rights = new int[NET_NEW_MAX_RIGHT_NUM];		// �û���֧��Ȩ������
        public byte[] 					memo = new byte[NET_MEMO_LENGTH]; 				// �û��鱸ע˵��
        public USER_GROUP_INFO_NEW() {
    		this.dwSize = this.size();
    	}
    }
    
    // �û�����Ϣ��չ���û������ӳ�
    public static class USER_GROUP_INFO_EX2 extends Structure {
    	public int 						 dwSize; 										// �ṹ���С
        public int 						 dwID; 											// ID
        public byte[] 					 name = new byte[NET_NEW_USER_NAME_LENGTH];     // �û��� ����NET_NEW_USER_NAME_LENGTH=128
        public int 						 dwRightNum;  									// Ȩ������
        public int[] 					 rights = new int[NET_NEW_MAX_RIGHT_NUM]; 		// �û�Ȩ�� �������� NET_NEW_MAX_RIGHT_NUM = 1024
        public byte[]					 memo = new byte[NET_MEMO_LENGTH]; 				// ˵���� ��ע����NET_MEMO_LENGTH=32
        
        public USER_GROUP_INFO_EX2() {
    		this.dwSize = this.size();
    	}
    }
    
    // �û���Ϣ�ṹ��
    public static class USER_INFO_NEW extends Structure {
    	public int 						dwSize; 									   // �ṹ���С
        public int 						dwID; 										   // �û�ID
        public int 						dwGroupID; 									   // �û�����ID
        public byte[] 					name = new byte[NET_NEW_USER_NAME_LENGTH];     // �û����ƣ�����NET_NEW_USER_NAME_LENGTH=128
        public byte[] 					passWord = new byte[NET_NEW_USER_PSW_LENGTH];  // �û����룬NET_NEW_USER_PSW_LENGTH=128
        public int 						dwRightNum;  								   // �û�Ȩ����Ч����
        public int[] 					rights = new int[NET_NEW_MAX_RIGHT_NUM];       // �û�֧��Ȩ�����飬�������� NET_NEW_MAX_RIGHT_NUM = 1024
        public byte[] 					memo = new byte[NET_MEMO_LENGTH]; 			   // �û���ע˵���� ��ע����NET_MEMO_LENGTH=32
        public int 						dwFouctionMask;          					   // ����,0x00000001 - ֧���û�����
        public NET_TIME 				stuTime;           							   // ����޸�ʱ��
        public byte 					byIsAnonymous;         					       // �Ƿ����������¼, 0:����������¼, 1: ����������¼
        public byte[] 					byReserve = new byte[7];  					   // �����ֽ�
        
        public USER_INFO_NEW() {
    		this.dwSize = this.size();
    	}
    }
    
    
    //------------------------��������ؽṹ��-------------------------
    // CLIENT_FindRecord�ӿ��������
    public static class NET_IN_FIND_RECORD_PARAM extends Structure {
        public int                       dwSize;          							 // �ṹ���С
        public int                       emType;          							 // ����ѯ��¼����,emType��Ӧ  EM_NET_RECORD_TYPE
        public Pointer                   pQueryCondition;							 // ��ѯ���Ͷ�Ӧ�Ĳ�ѯ���� =1ʱ���ǰ������˻���¼, ��ѯ������Ӧ FIND_RECORD_TRAFFICREDLIST_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_TRAFFIC_LIST_RECORD �ṹ��    
        
        public NET_IN_FIND_RECORD_PARAM() {
        	this.dwSize = this.size();
        }
    }
    
    // ��ͨ�ڰ������˻���¼��ѯ����
    public static class FIND_RECORD_TRAFFICREDLIST_CONDITION extends Structure {
    	public int          dwSize;
        public byte[]       szPlateNumber = new byte[NET_MAX_PLATE_NUMBER_LEN];      // ���ƺ�
        public byte[]       szPlateNumberVague = new byte[NET_MAX_PLATE_NUMBER_LEN]; // ���ƺ���ģ����ѯ
        public int          nQueryResultBegin;                          			 // ��һ�������ؽ���ڲ�ѯ����е�ƫ���� 
        public boolean      bRapidQuery;       										 // �Ƿ���ٲ�ѯ, TRUE:Ϊ����,���ٲ�ѯʱ���ȴ���������ɾ���Ĳ�����ɡ�Ĭ��Ϊ�ǿ��ٲ�ѯ
        
        public FIND_RECORD_TRAFFICREDLIST_CONDITION() {
        	this.dwSize = this.size();
        }
    }
   
    // ��¼������
    public static class EM_NET_RECORD_TYPE extends Structure {
        public static final int NET_RECORD_UNKNOWN = 0;
        public static final int NET_RECORD_TRAFFICREDLIST = 1; 					 // ��ͨ�������˻���¼, ��ѯ������Ӧ FIND_RECORD_TRAFFICREDLIST_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_TRAFFIC_LIST_RECORD �ṹ��    
        public static final int NET_RECORD_TRAFFICBLACKLIST = 2;  				 // ��ͨ�������˺ż�¼,��ѯ������Ӧ FIND_RECORD_TRAFFICREDLIST_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_TRAFFIC_LIST_RECORD �ṹ��       
        public static final int NET_RECORD_BURN_CASE = 3;      					 // ��¼������¼,��ѯ������Ӧ FIND_RECORD_BURN_CASE_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_BURN_CASE_INFO �ṹ��
        public static final int NET_RECORD_ACCESSCTLCARD = 4;  					 // �Ž���,��ѯ������Ӧ FIND_RECORD_ACCESSCTLCARD_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_RECORDSET_ACCESS_CTL_CARD �ṹ��
        public static final int NET_RECORD_ACCESSCTLPWD = 5;      				 // �Ž�����,��ѯ������Ӧ FIND_RECORD_ACCESSCTLPWD_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_RECORDSET_ACCESS_CTL_PWD
        public static final int NET_RECORD_ACCESSCTLCARDREC = 6; 				 // �Ž������¼������ͬʱ�����ź�ʱ��β�ѯ,������NET_RECORD_ACCESSCTLCARDREC_EX��ѯ��,��ѯ������Ӧ FIND_RECORD_ACCESSCTLCARDREC_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_RECORDSET_ACCESS_CTL_CARDREC �ṹ�� 
        public static final int NET_RECORD_ACCESSCTLHOLIDAY = 7; 				 // ���ռ�¼��,��ѯ������Ӧ FIND_RECORD_ACCESSCTLHOLIDAY_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_RECORDSET_HOLIDAY �ṹ��
        public static final int NET_RECORD_TRAFFICFLOW_STATE = 8;  				 // ��ѯ��ͨ������¼,��ѯ������Ӧ FIND_RECORD_TRAFFICFLOW_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_RECORD_TRAFFIC_FLOW_STATE �ṹ��
        public static final int NET_RECORD_VIDEOTALKLOG = 9;    				 // ͨ����¼,��ѯ������Ӧ FIND_RECORD_VIDEO_TALK_LOG_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_RECORD_VIDEO_TALK_LOG �ṹ��
        public static final int NET_RECORD_REGISTERUSERSTATE = 10;  			 // ״̬��¼,��ѯ������Ӧ FIND_RECORD_REGISTER_USER_STATE_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_RECORD_REGISTER_USER_STATE �ṹ��
        public static final int NET_RECORD_VIDEOTALKCONTACT = 11;  				 // ��ϵ�˼�¼,��ѯ������Ӧ FIND_RECORD_VIDEO_TALK_CONTACT_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_RECORD_VIDEO_TALK_CONTACT �ṹ��
        public static final int NET_RECORD_ANNOUNCEMENT = 12;					 // �����¼,��ѯ������Ӧ FIND_RECORD_ANNOUNCEMENT_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_RECORD_ANNOUNCEMENT_INFO �ṹ��    														
        public static final int NET_RECORD_ALARMRECORD = 13; 					 // ������¼,��ѯ������Ӧ FIND_RECORD_ALARMRECORD_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_RECORD_ALARMRECORD_INFO �ṹ��
        public static final int NET_RECORD_COMMODITYNOTICE = 14;  				 // �·���Ʒ��¼,��ѯ������Ӧ FIND_RECORD_COMMODITY_NOTICE_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_RECORD_COMMODITY_NOTICE �ṹ��                                                          
        public static final int NET_RECORD_HEALTHCARENOTICE = 15;  				 // ������Ϣ��¼,��ѯ������Ӧ FIND_RECORD_HEALTH_CARE_NOTICE_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_RECORD_HEALTH_CARE_NOTICE �ṹ��
        public static final int NET_RECORD_ACCESSCTLCARDREC_EX = 16; 			 // �Ž������¼(��ѡ�񲿷�������ѯ,�������NET_RECORD_ACCESSCTLCARDREC),��ѯ������Ӧ FIND_RECORD_ACCESSCTLCARDREC_CONDITION_EX �ṹ��,��¼��Ϣ��Ӧ NET_RECORDSET_ACCESS_CTL_CARDREC �ṹ��
        public static final int NET_RECORD_GPS_LOCATION = 17;  					 // GPSλ����Ϣ��¼, ֻʵ��import��clear,��¼��Ϣ��Ӧ NET_RECORD_GPS_LOCATION_INFO �ṹ��
        public static final int NET_RECORD_RESIDENT = 18;      					 // ���ⷿ�⻧��Ϣ,��ѯ������Ӧ FIND_RECORD_RESIDENT_CONDTION�ṹ��, ��¼��Ϣ��Ӧ NET_RECORD_RESIDENT_INFO �ṹ��
        public static final int NET_RECORD_SENSORRECORD = 19;   			 	 // ��������ݼ�¼,��ѯ������Ӧ FIND_RECORD_SENSORRECORD_CONDITION �ṹ��,��¼��Ϣ��Ӧ NET_RECORD_SENSOR_RECORD �ṹ��      
        public static final int NET_RECORD_ACCESSQRCODE = 20;  					 // ���Ŷ�ά���¼��,��¼��Ϣ��Ӧ NET_RECORD_ACCESSQRCODE_INFO�ṹ��
    }
 
    //��ͨ�ڰ�������¼��Ϣ
    public static class NET_TRAFFIC_LIST_RECORD extends Structure {
    	  public int                      dwSize; 	
    	  public int                	  nRecordNo;                                		 // ֮ǰ��ѯ���ļ�¼��
    	  public byte[]      			  szMasterOfCar = new byte[NET_MAX_NAME_LEN];        // ��������
    	  public byte[]      			  szPlateNumber = new byte[NET_MAX_PLATE_NUMBER_LEN];// ���ƺ��� 
    	  public int          			  emPlateType;                               		 // ��������,��ӦEM_NET_PLATE_TYPE
    	  public int          			  emPlateColor;                              		 // ������ɫ ����ӦEM_NET_PLATE_COLOR_TYPE
    	  public int          			  emVehicleType;                             		 // �������� ����ӦEM_NET_VEHICLE_TYPE
    	  public int        			  emVehicleColor;                         		     // ������ɫ����ӦEM_NET_VEHICLE_COLOR_TYPE
    	  public NET_TIME                 stBeginTime;                       				 // ��ʼʱ��
    	  public NET_TIME                 stCancelTime;                       				 // ����ʱ��
    	  public int                      nAuthrityNum;                       				 // Ȩ�޸���
    	  public NET_AUTHORITY_TYPE[]  	  stAuthrityTypes = (NET_AUTHORITY_TYPE[])new NET_AUTHORITY_TYPE().toArray(NET_MAX_AUTHORITY_LIST_NUM); // Ȩ���б� , ����������
    	  public int           		  	  emControlType;                    			     // �������� ,���������У���ӦEM_NET_TRAFFIC_CAR_CONTROL_TYPE
    	  
    	  public static class ByReference extends NET_TRAFFIC_LIST_RECORD implements Structure.ByReference {}
    	  public static class ByValue extends NET_TRAFFIC_LIST_RECORD implements Structure.ByValue {}
    	  
    	  public NET_TRAFFIC_LIST_RECORD() {
    		  this.dwSize = this.size();
    	  }
    }
 
    //Ȩ���б� , ����������
    public static class NET_AUTHORITY_TYPE extends Structure {
    	  public int                     dwSize; 
    	  public int              		 emAuthorityType;                 		 //Ȩ�����ͣ���ӦEM_NET_AUTHORITY_TYPE
    	  public boolean         		 bAuthorityEnable;                 		 //Ȩ��ʹ��
    	  
    	  public NET_AUTHORITY_TYPE() {
    		  this.dwSize = this.size();
    	  }
    }

    //Ȩ������
    public static class EM_NET_AUTHORITY_TYPE extends Structure {
    	public static final int     	NET_AUTHORITY_UNKNOW = 0;
    	public static final int		    NET_AUTHORITY_OPEN_GATE = 1;             //��բȨ��
    }

    // CLIENT_FindRecord�ӿ��������
    public static class NET_OUT_FIND_RECORD_PARAM extends Structure {
    	 public int                     dwSize;          						// �ṹ���С
    	 public NativeLong              lFindeHandle;    						// ��ѯ��¼���,Ψһ��ʶĳ�β�ѯ
    	 
    	 public NET_OUT_FIND_RECORD_PARAM() {
    		 this.dwSize = this.size();
    	 }
    }
    
    // CLIENT_FindNextRecord�ӿ��������
    public static class NET_IN_FIND_NEXT_RECORD_PARAM extends Structure {
        public int                      dwSize;          						// �ṹ���С
        public NativeLong               lFindeHandle;    						// ��ѯ���
        public int                      nFileCount;      						// ��ǰ���ѯ�ļ�¼����
        
        public NET_IN_FIND_NEXT_RECORD_PARAM() {
        	this.dwSize = this.size();
        }
    }
    
    //CLIENT_FindNextRecord�ӿ��������
    public static class NET_OUT_FIND_NEXT_RECORD_PARAM extends Structure {
        public int                     dwSize;          						// �ṹ���С
        public Pointer                 pRecordList;     				   	 	// ��¼�б�,�û������ڴ棬��Ӧ ��ͨ�ڰ�������¼��Ϣ NET_TRAFFIC_LIST_RECORD
        public int                     nMaxRecordNum;   						// �б��¼��
        public int                     nRetRecordNum;   						// ��ѯ���ļ�¼����,����ѯ��������С�����ѯ������ʱ,��ѯ����
        
        public NET_OUT_FIND_NEXT_RECORD_PARAM() {
        	this.dwSize = this.size();
        }
    }
    
    // CLIENT_OperateTrafficList�ӿ��������,
    public static class NET_IN_OPERATE_TRAFFIC_LIST_RECORD extends Structure {
        public int                       dwSize;
        public int                       emOperateType;  					 // emOperateType��ӦEM_RECORD_OPERATE_TYPE
        public int                       emRecordType;    					 // Ҫ������¼��Ϣ����,emRecordType��ӦEM_NET_RECORD_TYPE
        public Pointer                   pstOpreateInfo;  				   	// ��Ӧ ���NET_INSERT_RECORD_INFO/ ɾ��NET_REMOVE_RECORD_INFO / �޸�NET_UPDATE_RECORD_INFO
        
        public NET_IN_OPERATE_TRAFFIC_LIST_RECORD() {
        	this.dwSize = this.size();
        }
    }
    // ���
    public static class NET_INSERT_RECORD_INFO extends Structure {
        public int                       			dwSize;
        public NET_TRAFFIC_LIST_RECORD.ByReference  pRecordInfo = new NET_TRAFFIC_LIST_RECORD.ByReference();      		// ��¼������Ϣ
                    
        public NET_INSERT_RECORD_INFO () {
        	this.dwSize = this.size();
        }
    }
    // ɾ��
    public static class NET_REMOVE_RECORD_INFO extends Structure {
        public int                      dwSize;
        public int                      nRecordNo;      			    	 // ֮ǰ��ѯ���ļ�¼�ţ���ӦNET_TRAFFIC_LIST_RECORD���nRecordNo
        
        public NET_REMOVE_RECORD_INFO() {
        	this.dwSize = this.size();
        }
    }
    // �޸�
    public static class NET_UPDATE_RECORD_INFO extends Structure{
        public int                  			    dwSize;
        public NET_TRAFFIC_LIST_RECORD.ByReference 	pRecordInfo;    	   // ��¼������Ϣ ����Ӧ
        
        public NET_UPDATE_RECORD_INFO() {
        	this.dwSize = this.size();
        }
    }
    
    // �ڰ�������������
    public static class EM_RECORD_OPERATE_TYPE extends Structure {
        public static final int NET_TRAFFIC_LIST_INSERT = 0;               // ���Ӽ�¼����
        public static final int NET_TRAFFIC_LIST_UPDATE = 1;               // ���¼�¼����
        public static final int NET_TRAFFIC_LIST_REMOVE = 2;               // ɾ����¼����
        public static final int NET_TRAFFIC_LIST_MAX = 3;
    }
    
    // CLIENT_OperateTrafficList�ӿ��������,�ֽ׶�ʵ�ֵĲ����ӿ���,ֻ�з���nRecordNo�Ĳ���,stRetRecord��ʱ������,��null
    public static class NET_OUT_OPERATE_TRAFFIC_LIST_RECORD extends Structure {
        public int                     dwSize;
        public int                     nRecordNo;        //��¼�� 
        
        public NET_OUT_OPERATE_TRAFFIC_LIST_RECORD() {
        	this.dwSize = this.size();
        }
    }
    
    // ��¼����������
    public static class NET_CTRL_RECORDSET_PARAM extends Structure {
        public int               dwSize;
        public int 			     emType;                         // ��¼����Ϣ����,��ӦEM_NET_RECORD_TYPE
        public Pointer           pBuf;                           // ����\����\��ѯ\����ʱ,Ϊ��¼����Ϣ����,���EM_NET_RECORD_TYPEע��
                                                                 // ɾ��ʱ,Ϊ��¼���(int��)
        public int               nBufLen;                        // ��¼����Ϣ�����С
        
        public NET_CTRL_RECORDSET_PARAM() {
        	this.dwSize = this.size();
        }
    }
    
    // �ڰ������ϴ�
    public static class NETDEV_BLACKWHITE_LIST_INFO extends Structure {
        public byte[]        						  szFile = new byte[MAX_PATH_STOR];      // �ڰ������ļ�·��
        public int                                    nFileSize;            				 // �����ļ���С
        public byte                  			      byFileType;         					 // ��ǰ�ļ�����,0-������,1-������ 
        public byte                   			      byAction;            					 // ����,0-����,1-׷��
        public byte[]      		   					  byReserved = new byte[126];            // ����
    }
    
    // GPS��Ϣ(�����豸)
    public static class GPS_Info extends Structure {
        public NET_TIME           revTime;                          // ��λʱ��
        public byte[]             DvrSerial = new byte[50];         // �豸���к�
        public double             longitude;                     	// ����(��λ�ǰ����֮��,��Χ0-360��)
        public double             latidude;                     	// γ��(��λ�ǰ����֮��,��Χ0-180��)
        public double             height;                      	    // �߶�(��)
        public double             angle;                        	// �����(��������Ϊԭ��,˳ʱ��Ϊ��)
        public double             speed;                        	// �ٶ�(��λ�Ǻ���,speed/1000*1.852����/Сʱ)
        public short              starCount;                     	// ��λ����,�޷���
        public int           	  antennaState;                 	// ����״̬(true ��,false ��)
        public int                orientationState;              	// ��λ״̬(true ��λ,false ����λ)
        
        public static class ByValue extends GPS_Info implements Structure.ByValue { }
    }
    
    // ����״̬��Ϣ
    public static class ALARM_STATE_INFO extends Structure {
        public int                nAlarmCount;                       // �����ı����¼�����
        public int[]              nAlarmState = new int[128];        // �����ı����¼�����
        public byte[]             byRserved   = new byte[128];       // �����ֽ�
        
        public static class ByValue extends ALARM_STATE_INFO implements Structure.ByValue { }
    }
    
    // ��ӦCLIENT_SearchDevicesByIPs�ӿ�
    public static class DEVICE_IP_SEARCH_INFO extends Structure {
        public int               dwSize;                                    		 		// �ṹ���С
        public int               nIpNum;                                				    // ��ǰ������IP����
        public byte[][]          szIP        = new byte[NET_MAX_SAERCH_IP_NUM][64];         // �����������IP��Ϣ
        
        public DEVICE_IP_SEARCH_INFO() {
        	this.dwSize = this.size();
        }
    }
    
    // CLIENT_UploadRemoteFile �ӿ��������(�ϴ��ļ����豸)
    public static class NET_IN_UPLOAD_REMOTE_FILE extends Structure {
        public int               dwSize;
        public String            pszFileSrc;                     	// Դ�ļ�·��
        public String         	 pszFileDst;                     	// Ŀ���ļ�·��
        public String         	 pszFolderDst;                   	// Ŀ���ļ���·������ΪNULL, NULLʱ�豸ʹ��Ĭ��·��
        public int          	 nPacketLen;                     	// �ļ��ְ���С(�ֽ�): 0��ʾ���ְ�
        
        public NET_IN_UPLOAD_REMOTE_FILE(){
        	this.dwSize = this.size();
        }
    } 
    
    // CLIENT_UploadRemoteFile �ӿ��������(�ϴ��ļ����豸)
    public static class NET_OUT_UPLOAD_REMOTE_FILE extends Structure {
        public int               dwSize;
        
        public NET_OUT_UPLOAD_REMOTE_FILE() {
        	this.dwSize = this.size();
        }
    }
    
    // CLIENT_ParkingControlAttachRecord()�ӿ��������
    public static class NET_IN_PARKING_CONTROL_PARAM extends Structure {
        public int                              dwSize;
        public fParkingControlRecordCallBack    cbCallBack;                 // ���ݻص�����
        public NativeLong                       dwUser;                     // �û��������
        
        public NET_IN_PARKING_CONTROL_PARAM() {
        	this.dwSize = this.size();
        }
    } 

    // CLIENT_ParkingControlDetachRecord()�ӿ��������
    public static class NET_OUT_PARKING_CONTROL_PARAM extends Structure {
        public int    							dwSize;
        
        public NET_OUT_PARKING_CONTROL_PARAM() {
        	this.dwSize = this.size();
        }
    }
    
    // ������¼��Ϣ
    public static class NET_CAR_PASS_ITEM extends Structure {
        public int                      dwSize; 
        public NET_TIME                 stuTime;          // ����ʱ��
        public int                      dwCardNo;         // ����
        public int      			    emCardType;       // ����ͣ��ϵͳ����ڻ�IC���û�����,��Ӧ NET_ECK_IC_CARD_USER_TYPE
        public int     				    emFlag;           // ������¼���ͣ���Ӧ NET_ECK_CAR_PASS_FLAG
        
        public static class ByReference extends NET_CAR_PASS_ITEM implements Structure.ByReference {}
        
        public NET_CAR_PASS_ITEM(){
        	this.dwSize = this.size();
        }
    }
    
    // ����ͣ��ϵͳ����ڻ�IC���û�����
    public static class NET_ECK_IC_CARD_USER_TYPE extends Structure {
    	public static final int NET_ECK_IC_CARD_USER_UNKNOWN     = 0;
    	public static final int NET_ECK_IC_CARD_USER_ALL         = 1;               // ȫ������
    	public static final int NET_ECK_IC_CARD_USER_TEMP		 = 2;               // ��ʱ�û�
    	public static final int NET_ECK_IC_CARD_USER_LONG		 = 3;               // �����û�
    	public static final int NET_ECK_IC_CARD_USER_ADMIN		 = 4;               // ����Ա
    	public static final int NET_ECK_IC_CARD_USER_BLACK_LIST  = 5;               // ������
    }
    
    // ����ͣ��ϵͳ����ڻ��쳣������¼����
    public static class NET_ECK_CAR_PASS_FLAG extends Structure {
    	public static final int NET_ECK_CAR_PASS_FLAG_NORMAL   = 0;                 // ����
    	public static final int NET_ECK_CAR_PASS_FLAG_ABNORMAL = 1;                 // �쳣
    	public static final int NET_ECK_CAR_PASS_FLAG_ALL      = 2;                 // ȫ��
    } 

    // CLIENT_ParkingControlStartFind�ӿ��������******************
    public static class NET_IN_PARKING_CONTROL_START_FIND_PARAM extends Structure {
        public int                      dwSize;          // �ṹ���С
        public int                      bSearchCount;    // ��ѯ��¼�����Ƿ���Ч
        public int                      dwSearchCount;   // ��ѯ��¼����, ��ֵ��Χ1~100
        public int                      bBegin;          // ��ѯ��ʼʱ���Ƿ���Ч
        public NET_TIME                 stuBegin;        // ��ѯ��ʼʱ��
        public int                      bEnd;            // ��ѯ����ʱ���Ƿ���Ч
        public NET_TIME                 stuEnd;          // ��ѯ����ʱ��
        public int                      bCardType;       // �������Ƿ���Ч
        public int 					    emCardType;      // ������,��Ӧ NET_ECK_IC_CARD_USER_TYPE
        public int                      bFlag;           // ��������Ƿ���Ч
        public int                      emFlag;          // ������ǣ���Ӧ NET_ECK_CAR_PASS_FLAG
        
        public NET_IN_PARKING_CONTROL_START_FIND_PARAM() {
        	this.dwSize = this.size();
        }
    } 

    // CLIENT_ParkingControlStartFind�ӿ��������
    public static class NET_OUT_PARKING_CONTROL_START_FIND_PARAM extends Structure {
        public int                     dwSize;          // �ṹ���С
        public int                     dwTotalCount;    // ���ϴ˴β�ѯ�����Ľ��������
        
        public NET_OUT_PARKING_CONTROL_START_FIND_PARAM(){
        	this.dwSize = this.size();
        }
    }
    
    // CLIENT_ParkingControlDoFind�ӿ��������*******************
    public static class NET_IN_PARKING_CONTROL_DO_FIND_PARAM extends Structure {
        public int                     dwSize;          // �ṹ���С
        public int                     dwFileCount;     // ��ǰ���ѯ�ļ�¼����
        
        public NET_IN_PARKING_CONTROL_DO_FIND_PARAM(){
        	this.dwSize = this.size();
        }
    } 

    // CLIENT_ParkingControlDoFind�ӿ��������
    public static class NET_OUT_PARKING_CONTROL_DO_FIND_PARAM extends Structure{
        public int                             dwSize;          // �ṹ���С
        public NET_CAR_PASS_ITEM.ByReference   pstuRecordList = new NET_CAR_PASS_ITEM.ByReference();  // ��¼�б�,�û������ڴ�
        public int                     		   nMaxRecordNum;   // �б��¼��
        public int                     	 	   nRetRecordNum;   // ��ѯ���ļ�¼����,����ѯ��������С�����ѯ������ʱ,��ѯ����
        
        public NET_OUT_PARKING_CONTROL_DO_FIND_PARAM(){
        	this.dwSize = this.size();
        }
    } 
    
    // CLIENT_ParkingControlAttachParkInfo()�ӿ��������
    public static class NET_IN_PARK_INFO_PARAM extends Structure 
    {
        public int                             dwSize;
        public NET_PARK_INFO_FILTER            stuFilter;
        public fParkInfoCallBack               cbCallBack;        // ���ݻص�����
        public NativeLong                      dwUser;            // �û��������
        
        public NET_IN_PARK_INFO_PARAM() {
        	this.dwSize = this.size();
        }
    } 
    
    // CLIENT_ParkingControlAttachParkInfo()�ӿ��������
    public static class NET_OUT_PARK_INFO_PARAM extends Structure
    {
        public int    				dwSize;
        
        public NET_OUT_PARK_INFO_PARAM() {
        	this.dwSize = this.size();
        }
    }
    
    // ��λ�������Ϣ��ѯ����
    public static class NET_PARK_INFO_FILTER extends Structure
    {
        public int           dwSize; 
        public int           dwNum;                               // ��λ�������������
        public int[] 		 emType = new int[NET_ECK_PARK_DETECTOR_TYPE.NET_ECK_PARK_DETECTOR_TYPE_ALL];   // ��λ���������
        
        public NET_PARK_INFO_FILTER() {
        	this.dwSize = this.size();
        }
    } 
    
    // ��λ���������
    public static class NET_ECK_PARK_DETECTOR_TYPE extends Structure
    {
        public static final int NET_ECK_PARK_DETECTOR_TYPE_SONIC  = 0;         // ������̽����
        public static final int NET_ECK_PARK_DETECTOR_TYPE_CAMERA = 1;         // ��������
        public static final int NET_ECK_PARK_DETECTOR_TYPE_ALL	  = 2;
    } 
    
    // ��λ��Ϣ
    public static class NET_PARK_INFO_ITEM extends Structure
    {
        public int                 dwSize; 
        public byte[]              szParkNo = new byte[NET_COMMON_STRING_32];   // ��λ��
        public int  			   emState;                         			// ��λ״̬,��Ӧ  NET_ECK_PARK_STATE
        public int                 dwScreenIndex;                   			// ��λ����ʾ��Ӧ���յ���������
        public int                 dwFreeParkNum;                   			// ������ʾ�ĵ�ǰ���೵λ��Ŀ
        
        public NET_PARK_INFO_ITEM(){
        	this.dwSize = this.size();
        }
    }
    
    // ����ͣ��ϵͳ��λ״̬
    public static class NET_ECK_PARK_STATE extends Structure
    {
        public static final int NET_ECK_PARK_STATE_UNKOWN = 0;
        public static final int NET_ECK_PARK_STATE_PARK   = 1;       // ��λ�г�
        public static final int NET_ECK_PARK_STATE_NOPARK = 2;       // ��λ�޳�
    } 
    
    // ����ͣ��ϵͳ����ڻ����ó�λ��Ϣ ���� DH_CTRL_ECK_SET_PARK_INFO
    public static class NET_CTRL_ECK_SET_PARK_INFO_PARAM extends Structure
    {
        public int           	dwSize;
        public int              nScreenNum;                                     // ������, ������ ECK_SCREEN_NUM_MAX
        public int[]            nScreenIndex = new int[ECK_SCREEN_NUM_MAX];     // ����, ÿ��Ԫ�ر�ʾ�����
        public int[]            nFreeParkNum = new int[ECK_SCREEN_NUM_MAX];     // ��Ӧ�������µĿ��೵λ��
                                                                				// ���Ⱥ��±���nScreenIndexһ��,ÿ��Ԫ�ر�ʾ��Ӧ�����µĿ��೵λ
        public NET_CTRL_ECK_SET_PARK_INFO_PARAM(){
        	this.dwSize = this.size();
        }
    }
    
    // CLIENT_PowerControl�ӿ��������(����ǽ��Դ����)
    public static class NET_IN_WM_POWER_CTRL extends Structure
    {
        public int              	dwSize;
        public int                  nMonitorWallID;             // ����ǽ���
        public String          		pszBlockID;                 // ����ID, NULL/""-��������
        public int                  nTVID;                      // ��ʾ��Ԫ���, -1��ʾ������������ʾ��Ԫ
        public int                  bPowerOn;                   // �Ƿ�򿪵�Դ
        
        public NET_IN_WM_POWER_CTRL() {
        	this.dwSize = this.size();
        }
    } 

    // CLIENT_PowerControl�ӿ��������(����ǽ��Դ����)
    public static class NET_OUT_WM_POWER_CTRL extends Structure
    {
        public int                 dwSize;
        
        public NET_OUT_WM_POWER_CTRL() {
        	this.dwSize = this.size();
        }
    } 
    
    // CLIENT_LoadMonitorWallCollection�ӿ��������(�������ǽԤ��)
    public static class NET_IN_WM_LOAD_COLLECTION extends Structure
    {
        public int                dwSize;
        public int                nMonitorWallID;             // ����ǽ���
        public String         	  pszName;                    // Ԥ������
        
        public NET_IN_WM_LOAD_COLLECTION() {
        	this.dwSize = this.size();
        }
    } 

    // CLIENT_LoadMonitorWallCollection�ӿ��������(�������ǽԤ��)
    public static class NET_OUT_WM_LOAD_COLLECTION extends Structure
    {
        public int               dwSize;
        
        public NET_OUT_WM_LOAD_COLLECTION() {
        	this.dwSize = this.size();
        }
    } 

    // CLIENT_SaveMonitorWallCollection�ӿ��������(�������ǽԤ��)
    public static class NET_IN_WM_SAVE_COLLECTION extends Structure
    {
        public int               dwSize;
        public int               nMonitorWallID;             // ����ǽ���
        public String            pszName;                    // Ԥ������
        public String            pszControlID;               // ����id
        
        public NET_IN_WM_SAVE_COLLECTION() {
        	this.dwSize = this.size();
        }
    } 

    // CLIENT_SaveMonitorWallCollection�ӿ��������(�������ǽԤ��)
    public static class NET_OUT_WM_SAVE_COLLECTION extends Structure
    {
        public int               dwSize;
        
        public NET_OUT_WM_SAVE_COLLECTION() {
        	this.dwSize = this.size();
        }
    }
    
    // �ָ�ģʽ
    public static class NET_SPLIT_MODE extends Structure
    {
        public static final int NET_SPLIT_1 = 1;                                   // 1����
        public static final int NET_SPLIT_2 = 2;                                   // 2����
        public static final int NET_SPLIT_4 = 4;                                   // 4����
        public static final int NET_SPLIT_6 = 6;                                   // 6����
        public static final int NET_SPLIT_8 = 8;                                   // 8����
        public static final int NET_SPLIT_9 = 9;                                   // 9����
        public static final int NET_SPLIT_12 = 12;                                 // 12����
        public static final int NET_SPLIT_16 = 16;                                 // 16����
        public static final int NET_SPLIT_20 = 20;                                 // 20����
        public static final int NET_SPLIT_25 = 25;                                 // 25����
        public static final int NET_SPLIT_36 = 36;                                 // 36����
        public static final int NET_SPLIT_64 = 64;                                 // 64����
        public static final int NET_SPLIT_144 = 144;                               // 144����
        public static final int NET_PIP_1 = NET_SPLIT_PIP_BASE + 1;                // ���л�ģʽ, 1��ȫ������+1��С���洰��
        public static final int NET_PIP_3 = NET_SPLIT_PIP_BASE + 3;                // ���л�ģʽ, 1��ȫ������+3��С���洰��
        public static final int NET_SPLIT_FREE = NET_SPLIT_PIP_BASE * 2;           // ���ɿ���ģʽ,�������ɴ������رմ���,�������ô���λ�ú�Z�����
        public static final int NET_COMPOSITE_SPLIT_1 = NET_SPLIT_PIP_BASE * 3 + 1;// �ں�����Ա1�ָ�
        public static final int NET_COMPOSITE_SPLIT_4 = NET_SPLIT_PIP_BASE * 3 + 4;// �ں�����Ա4�ָ�
    }
    
    // ���鴰����Ϣ
    public static class NET_WINDOW_COLLECTION extends Structure
    {
        public int               dwSize;
        public int               nWindowID;                      // ����ID
        public int               bWndEnable;                     // �����Ƿ���Ч
        public DH_RECT          stuRect;                        // ��������, ���ɷָ�ģʽ����Ч
        public int               bDirectable;                    // �����Ƿ�����ֱͨ����
        public int               nZOrder;                        // ����Z����
        public int               bSrcEnable;                     // ��ʾԴ�Ƿ���Ч
        public byte[]            szDeviceID = new byte[NET_DEV_ID_LEN_EX]; // �豸ID
        public int               nVideoChannel;                  // ��Ƶͨ����
        public int               nVideoStream;                   // ��Ƶ��������
        public int               nAudioChannel;                  // ��Ƶͨ��
        public int               nAudioStream;                   // ��Ƶ��������
        public int               nUniqueChannel;                 // �豸��ͳһ��ŵ�Ψһͨ����
        
        public static class ByReference extends NET_WINDOW_COLLECTION implements Structure.ByReference{}
        
        public NET_WINDOW_COLLECTION() {
        	this.dwSize = this.size();
        }
    } 
    
    // �����ղ�
    public static class NET_BLOCK_COLLECTION extends Structure
    {
        public int                	 			  dwSize;
        public int        			  			  emSplitMode;                      						  // �ָ�ģʽ����Ӧ  NET_SPLIT_MODE
        public NET_WINDOW_COLLECTION[] 			  stuWnds       = (NET_WINDOW_COLLECTION[])new NET_WINDOW_COLLECTION().toArray(NET_MAX_SPLIT_WINDOW);  // ������Ϣ����
        public int                   			  nWndsCount;                    							  // ��������
        public byte[]                			  szName        = new byte[NET_DEVICE_NAME_LEN];    				  // �ղؼ�����
        public int                   			  nScreen;                       							  // ���ͨ����, ����ƴ����
        public byte[]                  			  szCompositeID = new byte[NET_DEV_ID_LEN_EX]; 				  // ƴ����ID    
        public NET_WINDOW_COLLECTION.ByReference  pstuWndsEx;                  							      // ������Ϣ����ָ��, ���û������ڴ�. ��stuWnds�����С������ʱ����ʹ��
        public int                  			  nMaxWndsCountEx;               							  // ��󴰿�����, �û���д. pstuWndsEx�����Ԫ�ظ���
        public int                  			  nRetWndsCountEx;               							  // ���ش�������
        
        public NET_BLOCK_COLLECTION() {
        	this.dwSize = this.size();
        }
    }

    // ����ǽ��ʾ��Ԫ
   public static class NET_MONITORWALL_OUTPUT extends Structure
    {
        public int               dwSize;
        public byte[]            szDeviceID = new byte[NET_DEV_ID_LEN];          // �豸ID, ����ʱΪ""
        public int               nChannel;                           		     // ͨ����
        public byte[]            szName 	= new byte[NET_DEV_NAME_LEN];        // ��Ļ����
        
        public static class ByReference extends NET_MONITORWALL_OUTPUT implements Structure.ByReference{}
        
        public NET_MONITORWALL_OUTPUT() {
        	this.dwSize = this.size();
        }
    } 

    // ����ǽ��ʾ����
    public static class NET_MONITORWALL_BLOCK extends Structure
    {
        public int               dwSize;
        public byte[]            szName 	   = new byte[NET_DEV_NAME_LEN];   // ��������
        public byte[]            szCompositeID = new byte[NET_DEV_ID_LEN];     // ƴ����ID
        public byte[]            szControlID   = new byte[NET_DEV_ID_LEN];     // ����ID
        public int               nSingleOutputWidth;             			   // ������ʾ��Ԫ��ռ����������
        public int               nSingleOutputHeight;            			   // ������ʾ��Ԫ��ռ����������
        public DH_RECT          stuRect;                        			   // ��������
        public NET_TSECT[][]     stuPowerSchedule = new NET_TSECT[NET_TSCHE_DAY_NUM][NET_TSCHE_SEC_NUM]; // ����ʱ���, ��һά��Ԫ�ر�ʾ����~�����ͽڼ���
        public NET_MONITORWALL_OUTPUT.ByReference  pstuOutputs;                // ��ʾ��Ԫ����, �û������ڴ�
        public int               nMaxOutputCount;                			   // ��ʾ��Ԫ�����С, �û���д
        public int               nRetOutputCount;                			   // ���ص���ʾ��Ԫ����
        
        public static class ByReference extends NET_MONITORWALL_BLOCK implements Structure.ByReference{}
        
        public NET_MONITORWALL_BLOCK() {
        	this.dwSize = this.size();
        }
    } 

    // ����ǽ����
    public static class NET_MONITORWALL extends Structure
    {
        public int                dwSize;
        public byte[]             szName = new byte[NET_DEV_NAME_LEN];      // ����
        public int                nGridLine;                      			// ��������
        public int                nGridColume;                   		 	// ��������
        public NET_MONITORWALL_BLOCK.ByReference   pstuBlocks;              // ��ʾ��������, �û������ڴ�
        public int                nMaxBlockCount;                 			// ��ʾ���������С, �û���д
        public int                nRetBlockCount;                 			// ���ص���ʾ��������
        public int                bDisable;                       			// �Ƿ����, 0-�õ���ǽ��Ч, 1-�õ���ǽ��Ч
        public byte[]             szDesc = new byte[NET_COMMON_STRING_256]; // ����ǽ������Ϣ
        
        public NET_MONITORWALL() {
        	this.dwSize = this.size();
        }
    } 

    // ����ǽԤ��
    public static class NET_MONITORWALL_COLLECTION extends Structure
    {
        public int                	  dwSize;
        public byte[]                 szName 	  = new byte[NET_DEVICE_NAME_LEN];    			// ����ǽԤ������
        public NET_BLOCK_COLLECTION[] stuBlocks	  = (NET_BLOCK_COLLECTION[])new NET_BLOCK_COLLECTION().toArray(NET_MAX_BLOCK_NUM);// ��������
        public int                 	  nBlocksCount;                  							// ��������
        public byte[]                 szControlID = new byte[NET_DEV_ID_LEN_EX]; 				// ����ID
        public NET_MONITORWALL        stuMonitorWall;                							// ����ǽ����
        
        public NET_MONITORWALL_COLLECTION() {
        	this.dwSize = this.size();
        }
    } 

    // CLIENT_GetMonitorWallCollections�ӿ��������(��ȡ����ǽԤ����Ϣ)
    public static class NET_IN_WM_GET_COLLECTIONS extends Structure
    {
        public int                	dwSize;
        public int                  nMonitorWallID;                // ����ǽID
        
        public NET_IN_WM_GET_COLLECTIONS() {
        	this.dwSize = this.size();
        }
    } 

    // CLIENT_GetMonitorWallCollections�ӿ��������(��ȡ����ǽԤ����Ϣ)
    public static class NET_OUT_WM_GET_COLLECTIONS extends Structure
    {
        public int                   dwSize;    
        public Pointer			   	 pCollections;  		      // ����ǽԤ������, ��Ӧ   NET_MONITORWALL_COLLECTION ָ��
        public int                   nMaxCollectionsCount;   	  // ����ǽԤ�������С
        public int                   nCollectionsCount;      	  // ����ǽԤ������
        
        public NET_OUT_WM_GET_COLLECTIONS() {
        	this.dwSize = this.size();
        }
    } 
    
    // ����Ȩ����֤��Ϣ
    public static class NET_CASCADE_AUTHENTICATOR extends Structure
    {
        public int                  dwSize;
        public byte[]               szUser	   = new byte[NET_NEW_USER_NAME_LENGTH];    // �û���
        public byte[]               szPwd  	   = new byte[NET_NEW_USER_PSW_LENGTH];     // ����
        public byte[]               szSerialNo = new byte[NET_SERIALNO_LEN];        	// �豸���к�
        
        public NET_CASCADE_AUTHENTICATOR() {
        	this.dwSize = this.size();
        }
    }
    

    public static class EM_SRC_PUSHSTREAM_TYPE extends Structure
    {   
        public static final int EM_SRC_PUSHSTREAM_AUTO			= 0;       // �豸�˸�������ͷ�Զ�ʶ��Ĭ��ֵ
        public static final int EM_SRC_PUSHSTREAM_HIKVISION		= 1;       // ����˽������
        public static final int EM_SRC_PUSHSTREAM_PS			= 2;       // PS��
        public static final int EM_SRC_PUSHSTREAM_TS			= 3;       // TS��
        public static final int EM_SRC_PUSHSTREAM_SVAC			= 4;       // SVAC����
    }
    
    // ��ʾԴ
    public static class NET_SPLIT_SOURCE extends Structure
    {
        public int               	dwSize;
        public int                  bEnable;                                // ʹ��
        public byte[]               szIp 	= new byte[NET_MAX_IPADDR_LEN]; // IP, �ձ�ʾû������
        public byte[]               szUser  = new byte[NET_USER_NAME_LENGTH];// �û���, ����ʹ��szUserEx
        public byte[]               szPwd 	= new byte[NET_USER_PSW_LENGTH]; // ����, ����ʹ��szPwdEx
        public int                  nPort;                                  // �˿�
        public int                  nChannelID;                             // ͨ����
        public int                  nStreamType;                            // ��Ƶ����, -1-�Զ�, 0-������, 1-������1, 2-������2, 3-������3, 4-snap, 5-Ԥ��
        public int                  nDefinition;                            // ������, 0-����, 1-����
        public int  				emProtocol;                             // Э������,��Ӧ   NET_DEVICE_PROTOCOL
        public byte[]               szDevName  = new byte[NET_DEVICE_NAME_LEN]; // �豸����
        public int                  nVideoChannel;                          // ��Ƶ����ͨ����
        public int                  nAudioChannel;                          // ��Ƶ����ͨ����
        //--------------------------------------------------------------------------------------
        // ����ֻ�Խ�������Ч
        public int                 bDecoder;                                // �Ƿ��ǽ�����
        public byte                byConnType;                              // -1: auto, 0��TCP��1��UDP��2���鲥
        public byte                byWorkMode;                              // 0��ֱ����1��ת��
        public short               wListenPort;                             // ָʾ��������Ķ˿�,ת��ʱ��Ч; byConnTypeΪ�鲥ʱ,����Ϊ�ಥ�˿�
        public byte[]              szDevIpEx  = new byte[NET_MAX_IPADDR_OR_DOMAIN_LEN]; // szDevIp��չ,ǰ��DVR��IP��ַ(������������)
        public byte                bySnapMode;                              // ץͼģʽ(nStreamType==4ʱ��Ч) 0����ʾ����һ֡,1����ʾ��ʱ��������
        public byte                byManuFactory;                           // Ŀ���豸����������, ����ο�EM_IPC_TYPE��
        public byte                byDeviceType;                            // Ŀ���豸���豸����, 0:IPC
        public byte                byDecodePolicy;                          // Ŀ���豸�Ľ������, 0:������ǰ
                                                                    	    // 1:ʵʱ�ȼ��� 2:ʵʱ�ȼ���
                                                                    	    // 3:ʵʱ�ȼ��� 4:Ĭ�ϵȼ�
                                                                    	    // 5:�����ȼ��� 6:�����ȼ���
                                                                    	    // 7:�����ȼ���
        //--------------------------------------------------------------------------------------
        public int                dwHttpPort;                               // Http�˿ں�, 0-65535
        public int                dwRtspPort;                               // Rtsp�˿ں�, 0-65535
        public byte[]             szChnName  = new byte[NET_DEVICE_NAME_LEN]; // Զ��ͨ������, ֻ�ж�ȡ�������Ʋ�Ϊ��ʱ�ſ����޸ĸ�ͨ��������
        public byte[]             szMcastIP  = new byte[NET_MAX_IPADDR_LEN];  // �ಥIP��ַ, byConnTypeΪ�鲥ʱ��Ч
        public byte[]             szDeviceID = new byte[NET_DEV_ID_LEN_EX];   // �豸ID, ""-null, "Local"-����ͨ��, "Remote"-Զ��ͨ��, ������������RemoteDevice�е��豸ID
        public int                bRemoteChannel;                           // �Ƿ�Զ��ͨ��(ֻ��)
        public int        		  nRemoteChannelID;                         // Զ��ͨ��ID(ֻ��), bRemoteChannel=TRUEʱ��Ч
        public byte[]             szDevClass = new byte[NET_DEV_TYPE_LEN];  // �豸����, ��IPC, DVR, NVR��
        public byte[]             szDevType  = new byte[NET_DEV_TYPE_LEN];  // �豸�����ͺ�, ��IPC-HF3300
        public byte[]             szMainStreamUrl = new byte[MAX_PATH];     // ������url��ַ, byManuFactoryΪDH_IPC_OTHERʱ��Ч
        public byte[]             szExtraStreamUrl = new byte[MAX_PATH];    // ������url��ַ, byManuFactoryΪDH_IPC_OTHERʱ��Ч
        public int                nUniqueChannel;                           // �豸��ͳһ��ŵ�Ψһͨ����, ֻ��
        public NET_CASCADE_AUTHENTICATOR  stuCascadeAuth;                   // ������֤��Ϣ, �豸IDΪ"Local/Cascade/SerialNo"ʱ��Ч, ����SerialNo���豸���к�
        public int                nHint;                                    // 0-��ͨ��ƵԴ, 1-������ƵԴ
        public int                nOptionalMainUrlCount;                    // ������������ַ����
        public byte[]             szOptionalMainUrls = new byte[NET_MAX_OPTIONAL_URL_NUM*MAX_PATH];  // ������������ַ�б�
        public int                nOptionalExtraUrlCount;                   // ���ø�������ַ����
        public byte[]             szOptionalExtraUrls = new byte[NET_MAX_OPTIONAL_URL_NUM*MAX_PATH]; // ���ø�������ַ�б�
        //--------------------------------------------------------------------------------------
        //Э���������ֶ�
        public int                nInterval;                                // ��Ѳʱ����   ��λ����
        public byte[]             szUserEx = new byte[NET_NEW_USER_NAME_LENGTH]; // �û���
        public byte[]             szPwdEx  = new byte[NET_NEW_USER_PSW_LENGTH];  // ����
        public int  			  emPushStream;          			        // ������ʽ����������,ֻ��byConnTypeΪTCP-Push��UDP-Push���и��ֶ�,��Ӧ  EM_SRC_PUSHSTREAM_TYPE
        
        public NET_SPLIT_SOURCE() {
        	this.dwSize = this.size();
        }
    } 
    
    // �����ӿ���Ϣ
    public static class NET_MATRIX_CARD extends Structure
    {
        public int               dwSize;
        public int               bEnable;                                // �Ƿ���Ч
        public int               dwCardType;                             // �ӿ�����
        public byte[]            szInterface = new byte[NET_MATRIX_INTERFACE_LEN];   // �źŽӿ�����, "CVBS", "VGA", "DVI"...
        public byte[]            szAddress   = new byte[NET_MAX_IPADDR_OR_DOMAIN_LEN]; // �豸ip������, ������ӿڵ��ӿ�����Ϊ��
        public int               nPort;                                  // �˿ں�, ������ӿڵ��ӿ�����Ϊ0
        public int               nDefinition;                            // ������, 0=����, 1=����
        public int               nVideoInChn;                            // ��Ƶ����ͨ����
        public int               nAudioInChn;                            // ��Ƶ����ͨ����
        public int               nVideoOutChn;                           // ��Ƶ���ͨ����
        public int               nAudioOutChn;                           // ��Ƶ���ͨ����
        public int               nVideoEncChn;                           // ��Ƶ����ͨ����
        public int               nAudioEncChn;                           // ��Ƶ����ͨ����
        public  int              nVideoDecChn;                           // ��Ƶ����ͨ����
        public  int              nAudioDecChn;                           // ��Ƶ����ͨ����
        public int               nStauts;                                // ״̬: -1-δ֪, 0-����, 1-����Ӧ, 2-�������, 3-��ͻ, 4-��������, 5-��·״̬�쳣, 6-�Ӱ屳��δ���, 7-����汾����
        public int               nCommPorts;                             // ������
        public int               nVideoInChnMin;                         // ��Ƶ����ͨ������Сֵ
        public int               nVideoInChnMax;                         // ��Ƶ����ͨ�������ֵ
        public int               nAudioInChnMin;                         // ��Ƶ����ͨ������Сֵ
        public int               nAudioInChnMax;                         // ��Ƶ����ͨ�������ֵ
        public int               nVideoOutChnMin;                        // ��Ƶ���ͨ������Сֵ
        public  int              nVideoOutChnMax;                        // ��Ƶ���ͨ�������ֵ
        public  int              nAudioOutChnMin;                        // ��Ƶ���ͨ������Сֵ
        public int               nAudioOutChnMax;                        // ��Ƶ���ͨ�������ֵ    
        public int               nVideoEncChnMin;                        // ��Ƶ����ͨ������Сֵ
        public int               nVideoEncChnMax;                        // ��Ƶ����ͨ�������ֵ
        public int               nAudioEncChnMin;                        // ��Ƶ����ͨ������Сֵ
        public int               nAudioEncChnMax;                        // ��Ƶ����ͨ�������ֵ
        public  int              nVideoDecChnMin;                        // ��Ƶ����ͨ������Сֵ
        public int               nVideoDecChnMax;                        // ��Ƶ����ͨ�������ֵ
        public int               nAudioDecChnMin;                        // ��Ƶ����ͨ������Сֵ
        public int               nAudioDecChnMax;                        // ��Ƶ����ͨ�������ֵ
        public int               nCascadeChannels;                       // ����ͨ����
        public int               nCascadeChannelBitrate;                 // ����ͨ������, ��λMbps
        public int               nAlarmInChnCount;                       // ��������ͨ����
        public int               nAlarmInChnMin;                         // ��������ͨ������Сֵ
        public int               nAlarmInChnMax;                         // ��������ͨ�������ֵ
        public int               nAlarmOutChnCount;                      // �������ͨ����
        public int               nAlarmOutChnMin;                        // ��������ͨ������Сֵ
        public  int              nAlarmOutChnMax;                        // ��������ͨ�������ֵ
        public int               nVideoAnalyseChnCount;                  // ���ܷ���ͨ����
        public  int              nVideoAnalyseChnMin;                    // ���ܷ���ͨ������Сֵ
        public int               nVideoAnalyseChnMax;                    // ���ܷ���ͨ�������ֵ
        public int               nCommPortMin;                           // ���ں���Сֵ
        public int               nCommPortMax;                           // ���ں����ֵ
        public byte[]            szVersion 	   = new byte[NET_COMMON_STRING_32];    // �汾��Ϣ
        public NET_TIME          stuBuildTime;                           // ����ʱ��
        public byte[]            szBIOSVersion = new byte[NET_COMMON_STRING_64];    // BIOS�汾��
        public byte[]			 szMAC         = new byte[NET_MACADDR_LEN];			// MAC��ַ
        
        public NET_MATRIX_CARD() {
        	this.dwSize = this.size();
        }
    } 
    
    // �����ӿ��б�
    public static class NET_MATRIX_CARD_LIST extends Structure
    {
        public int               dwSize;
        public int               nCount;                                 				// �ӿ�����
        public NET_MATRIX_CARD[] stuCards = new NET_MATRIX_CARD[NET_MATRIX_MAX_CARDS];  // �ӿ��б�
        
        public NET_MATRIX_CARD_LIST() {
        	this.dwSize = this.size();
        }
    } 
    
    
    /************************************************************************
     ** �ص�
     ***********************************************************************/
    //JNA CALLBACK��������,���߻ص�
    public interface fDisConnect extends StdCallCallback {
        public void invoke(NativeLong lLoginID, String pchDVRIP, int nDVRPort, NativeLong dwUser);
    }

    // �������ӻָ��ص�����ԭ��
    public interface fHaveReConnect extends StdCallCallback {
        public void invoke(NativeLong lLoginID, String pchDVRIP, int nDVRPort, NativeLong dwUser);
    }
    
    // ��Ϣ�ص�����ԭ��(pBuf�ڴ���SDK�ڲ������ͷ�)
    public interface fMessCallBack extends StdCallCallback{
        public boolean invoke( NativeLong lCommand , NativeLong lLoginID , Pointer pStuEvent , int dwBufLen , String strDeviceIP ,  NativeLong nDevicePort , NativeLong dwUser);
    }
    
    public interface fFaceFindState extends StdCallCallback {
        // pstStates ָ��NET_CB_FACE_FIND_STATE��ָ��
        public void invoke(NativeLong lLoginID, NativeLong lAttachHandle, Pointer pstStates, int nStateNum, NativeLong dwUser);
    }
    
    // ���ܷ������ݻص�;nSequence��ʾ�ϴ�����ͬͼƬ�����Ϊ0ʱ��ʾ�ǵ�һ�γ��֣�Ϊ2��ʾ���һ�γ��ֻ������һ�Σ�Ϊ1��ʾ�˴�֮����
    // int nState = *(int*) reserved ��ʾ��ǰ�ص����ݵ�״̬, Ϊ0��ʾ��ǰ����Ϊʵʱ���ݣ�Ϊ1��ʾ��ǰ�ص��������������ݣ�Ϊ2ʱ��ʾ�������ݴ��ͽ���
    public interface fAnalyzerDataCallBack extends StdCallCallback {
        public int invoke(NativeLong lAnalyzerHandle, int dwAlarmType, Pointer pAlarmInfo, Pointer pBuffer, int dwBufSize, Pointer dwUser, int nSequence, Pointer reserved);
    }
    
    // ץͼ�ص�����ԭ��(pBuf�ڴ���SDK�ڲ������ͷ�)
    // EncodeType �������ͣ�10����ʾjpegͼƬ      0��mpeg4
    public interface fSnapRev extends StdCallCallback{
        public void invoke( NativeLong lLoginID ,Pointer pBuf, int RevLen, int EncodeType, NativeLong CmdSerial, NativeLong dwUser);
    }
    
    // �첽�����豸�ص�(pDevNetInfo�ڴ���SDK�ڲ������ͷ�)
    public interface fSearchDevicesCB extends StdCallCallback{
        public void invoke(Pointer pDevNetInfo, Pointer pUserData);
    }
    
    // ��ʱ��طŽ��Ȼص�����ԭ��
    public interface fTimeDownLoadPosCallBack extends StdCallCallback {    
        public void invoke(NativeLong lPlayHandle, int dwTotalSize, int dwDownLoadSize, int index, NET_RECORDFILE_INFO.ByValue recordfileinfo, NativeLong dwUser);
    } 
    
    // �ط����ݻص�����ԭ��
    public interface fDataCallBack extends StdCallCallback {
        public int invoke(NativeLong lRealHandle, int dwDataType, Pointer pBuffer, int dwBufSize, NativeLong dwUser);
    }
    
    // �طŽ��Ȼص�����ԭ��
    public interface fDownLoadPosCallBack extends StdCallCallback {
    	public void invoke(NativeLong lPlayHandle, int dwTotalSize, int dwDownLoadSize, NativeLong dwUser);
    }
    
    // ��Ƶͳ��ժҪ��Ϣ�ص�����ԭ�Σ�lAttachHandle �� CLIENT_AttachVideoStatSummary ����ֵ
    public interface fVideoStatSumCallBack extends StdCallCallback {
    	public void invoke(NativeLong lAttachHandle, NET_VIDEOSTAT_SUMMARY pBuf, int dwBufLen, NativeLong dwUser);
    }
    
    // �û��Զ�������ݻص�   lTalkHandle��CLIENT_StartTalkEx�ķ���ֵ 
    // byAudioFlag��   0��ʾ�Ǳ���¼����ɼ�����Ƶ���� ��  1��ʾ�յ����豸����������Ƶ����
    public interface pfAudioDataCallBack extends StdCallCallback {
    	public void invoke(NativeLong lTalkHandle, Pointer pDataBuf, int dwBufSize, byte byAudioFlag, NativeLong dwUser);
    }

    // lHandle���ļ������� ��nTransType���ļ��������ͣ�nState���ļ�����״̬��
    public interface fTransFileCallBack extends StdCallCallback {
    	public void invoke(NativeLong lHandle, int nTransType, int nState, int nSendSize, int nTotalSize, NativeLong  dwUser);
    }    
    
    // GPS��Ϣ���Ļص�--��չ
    public interface fGPSRevEx extends StdCallCallback { 
    	public void invoke(NativeLong lLoginID, GPS_Info.ByValue GpsInfo, ALARM_STATE_INFO.ByValue stAlarmInfo, NativeLong dwUserData, Pointer reserved);
    }
    
    // ʵʱ�������ݻص�����--��չ(pBuffer�ڴ���SDK�ڲ������ͷ�)
    // lRealHandleʵʱ����           dwDataType: 0-ԭʼ����   1-֡����    2-yuv����   3-pcm��Ƶ����
    // pBuffer��ӦBYTE*
    // param:������Ϊ0(ԭʼ����)��2(YUV����) ʱΪ0�����ص�����������Ϊ1ʱparamΪһ��tagVideoFrameParam�ṹ��ָ�롣
    // param:������������3ʱ,paramҲ��һ��tagCBPCMDataParam�ṹ��ָ�� 
    public interface fRealDataCallBackEx extends StdCallCallback {
    	public void invoke(NativeLong lRealHandle, int dwDataType, Pointer pBuffer, int dwBufSize, int param, NativeLong dwUser);
    }

    // ��Ƶ���ӶϿ��ص�����, (param�ڴ���SDK�ڲ������ͷ� )
    // lOperateHandle��ؾ��   dwEventType��ӦEM_REALPLAY_DISCONNECT_EVENT_TYPE   param��Ӧvoid*,�¼�����
    public interface fRealPlayDisConnect extends StdCallCallback {
    	public void invoke(NativeLong lOperateHandle, int dwEventType, Pointer param, NativeLong dwUser);
    }
    
    // ���Ĺ�����¼���ݻص�����ԭ��     lAttachHandleΪCLIENT_ParkingControlAttachRecord����ֵ
    public interface fParkingControlRecordCallBack extends StdCallCallback {
    	public void invoke(NativeLong lLoginID, NativeLong lAttachHandle, NET_CAR_PASS_ITEM pInfo, int nBufLen, NativeLong dwUser);
    }
    
    // ���ĳ�λ��Ϣ�ص�����ԭ��
    public interface fParkInfoCallBack extends StdCallCallback {
    	public void invoke(NativeLong lLoginID, NativeLong lAttachHandle, NET_PARK_INFO_ITEM pInfo, int nBufLen, NativeLong dwUser);
    }
  
    

    /************************************************************************
     ** �ӿ�
     ***********************************************************************/
    //  JNAֱ�ӵ��÷������壬cbDisConnectʵ����������ص�Java���룬��Ϊ�������ʹ�����·�ʽ���ж��塣
    public boolean CLIENT_Init(fDisConnect cbDisConnect, NativeLong dwUser);
    
    //  JNAֱ�ӵ��÷������壬SDK�˳�����
    public void CLIENT_Cleanup();
    
    //  JNAֱ�ӵ��÷������壬���ö��������ɹ��ص����������ú�SDK�ڲ������Զ�����
    public void CLIENT_SetAutoReconnect(fHaveReConnect cbAutoConnect, NativeLong dwUser);
    
    // ���غ���ִ��ʧ�ܴ���
    public int CLIENT_GetLastError();

    // ���������豸��ʱʱ��ͳ��Դ���
    public void CLIENT_SetConnectTime(int nWaitTime, int nTryTimes);

    // ���õ�½���绷��
    public void CLIENT_SetNetworkParam(NET_PARAM pNetParam);

    // ��ȡSDK�İ汾��Ϣ
    public int CLIENT_GetSDKVersion();
    
    //  JNAֱ�ӵ��÷������壬��½�ӿ�
    public NativeLong CLIENT_LoginEx(String pchDVRIP, short wDVRPort, String pchUserName, String pchPassword, int nSpecCap, Pointer pCapParam, NET_DEVICEINFO lpDeviceInfo, int[] error/*= 0*/);
    
    //  JNAֱ�ӵ��÷������壬��½��չ�ӿ�///////////////////////////////////////////////////
    //  nSpecCap ��Ӧ  EM_LOGIN_SPAC_CAP_TYPE ��½����
    public NativeLong CLIENT_LoginEx2(String pchDVRIP, short wDVRPort, String pchUserName, String pchPassword, int nSpecCap, Pointer pCapParam, NET_DEVICEINFO_Ex lpDeviceInfo, int[] error/*= 0*/);
    
    //  JNAֱ�ӵ��÷������壬���豸ע��
    public boolean CLIENT_Logout(NativeLong lLoginID);
    
    // ��ȡ����
    // error Ϊ�豸���صĴ����룺 0-�ɹ� 1-ʧ�� 2-���ݲ��Ϸ� 3-��ʱ�޷����� 4-û��Ȩ��
    public boolean CLIENT_GetNewDevConfig(NativeLong lLoginID , String szCommand , int nChannelID , byte[] szOutBuffer , int dwOutBufferSize , int[] error , int waiitime);
    
    // ��������
    public boolean CLIENT_SetNewDevConfig(NativeLong lLoginID , String szCommand , int nChannelID , byte[] szInBuffer, int dwInBufferSize, int[] error, int[] restart, int waittime );
    
    // ������ѯ����������Ϣ
    public boolean CLIENT_ParseData(String szCommand, byte[] szInBuffer, Pointer lpOutBuffer, int dwOutBufferSize, Pointer pReserved);

    // ���Ҫ���õ�������Ϣ
    public boolean CLIENT_PacketData(String szCommand, Pointer lpInBuffer, int dwInBufferSize, byte[] szOutBuffer, int dwOutBufferSize);

    // ���ñ����ص�����
    public void  CLIENT_SetDVRMessCallBack(fMessCallBack cbMessage , NativeLong dwUser);
    
    // ���豸���ı���--��չ
    public boolean  CLIENT_StartListenEx(NativeLong lLoginID);

    /////////////////////////////////����ʶ��ӿ�/////////////////////////////////////////
    //����ʶ�����ݿ���Ϣ�������������,�޸ĺ�ɾ����
    // pstInParamָ��NET_IN_OPERATE_FACERECONGNITIONDB���͵�ָ��
    // pstOutParamָ��NET_OUT_OPERATE_FACERECONGNITIONDB���͵�ָ��
    public boolean  CLIENT_OperateFaceRecognitionDB(NativeLong lLoginID, final NET_IN_OPERATE_FACERECONGNITIONDB pstInParam, NET_OUT_OPERATE_FACERECONGNITIONDB pstOutParam, int nWaitTime);
    
    //��������ѯ����ʶ���� 
    // pstInParamָ��NET_IN_STARTFIND_FACERECONGNITION���͵�ָ��
    // pstOutParamָ��NET_OUT_STARTFIND_FACERECONGNITION���͵�ָ��
    public boolean  CLIENT_StartFindFaceRecognition(NativeLong lLoginID, final NET_IN_STARTFIND_FACERECONGNITION pstInParam, NET_OUT_STARTFIND_FACERECONGNITION pstOutParam, int nWaitTime);
    
    //��������ʶ����:nFilecount:��Ҫ��ѯ������, ����ֵΪý���ļ����� ����ֵ<nFilecount����Ӧʱ����ڵ��ļ���ѯ���(ÿ�����ֻ�ܲ�ѯ20����¼)
    // pstInParamָ��NET_IN_DOFIND_FACERECONGNITION���͵�ָ��
    // pstOutParamָ��NET_OUT_DOFIND_FACERECONGNITION���͵�ָ��
    public boolean  CLIENT_DoFindFaceRecognition(final NET_IN_DOFIND_FACERECONGNITION pstInParam, NET_OUT_DOFIND_FACERECONGNITION pstOutParam, int nWaitTime);
    
    //������ѯ
    public boolean  CLIENT_StopFindFaceRecognition(NativeLong lFindHandle);
    
    //�������(����һ�Ŵ�ͼ,�����ͼ�б�������������ͼƬ)
    // pstInParamָ��NET_IN_DETECT_FACE���͵�ָ��
    // pstOutParamָ��NET_OUT_DETECT_FACE���͵�ָ��
    public boolean  CLIENT_DetectFace(NativeLong lLoginID, final NET_IN_DETECT_FACE pstInParam, NET_OUT_DETECT_FACE pstOutParam, int nWaitTime);
    
    //����ʶ����Ա��������������,�޸ĺ�ɾ����
    // pstInParamָ��NET_IN_OPERATE_FACERECONGNITION_GROUP���͵�ָ��
    // pstOutParamָ��NET_OUT_OPERATE_FACERECONGNITION_GROUP���͵�ָ��
    public boolean  CLIENT_OperateFaceRecognitionGroup(NativeLong lLoginID, final NET_IN_OPERATE_FACERECONGNITION_GROUP pstInParam, NET_OUT_OPERATE_FACERECONGNITION_GROUP pstOutParam, int nWaitTime);
    
    //��ѯ����ʶ����Ա����Ϣ
    // pstInParamָ��NET_IN_FIND_GROUP_INFO���͵�ָ��
    // pstOutParamָ��NET_OUT_FIND_GROUP_INFO���͵�ָ��
    public boolean  CLIENT_FindGroupInfo(NativeLong NativeLong, final NET_IN_FIND_GROUP_INFO pstInParam, NET_OUT_FIND_GROUP_INFO pstOutParam, int nWaitTime);
    
    //����ͨ����Ա����Ϣ
    // pstInParamָ��NET_IN_SET_GROUPINFO_FOR_CHANNEL���͵�ָ��
    // pstOutParamָ��NET_OUT_SET_GROUPINFO_FOR_CHANNEL���͵�ָ��
    public boolean CLIENT_SetGroupInfoForChannel(NativeLong lLoginID, final NET_IN_SET_GROUPINFO_FOR_CHANNEL pstInParam, NET_OUT_SET_GROUPINFO_FOR_CHANNEL pstOutParam, int nWaitTime);
    
    //����������ѯ״̬
    // pstInParamָ��NET_IN_FACE_FIND_STATE���͵�ָ��
    // pstOutParamָ��NET_OUT_FACE_FIND_STATE���͵�ָ��
    public NativeLong CLIENT_AttachFaceFindState(NativeLong lLoginID, final NET_IN_FACE_FIND_STATE pstInParam, NET_OUT_FACE_FIND_STATE pstOutParam, int nWaitTime);
    
    //ȡ������������ѯ״̬,lAttachHandleΪCLIENT_AttachFaceFindState���صľ��
    public boolean CLIENT_DetachFaceFindState(NativeLong lAttachHandle);
    
    // ����־����
    // pstLogPrintInfoָ��LOG_SET_PRINT_INFO��ָ��
    public boolean CLIENT_LogOpen(LOG_SET_PRINT_INFO pstLogPrintInfo);

    // �ر���־����
    public boolean CLIENT_LogClose();
    
    // ����ѯ������ѯ�ļ�
    // pQueryConditionΪvoid *, �������͸���emType������ȷ��,��Ӧ EM_FILE_QUERY_TYPE
    // reservedΪvoid *, �������͸���emType������ȷ��
    public NativeLong CLIENT_FindFileEx(NativeLong lLoginID, int emType, Pointer pQueryCondition, Pointer reserved, int waittime);
    
    // ��ȡ���ϲ�ѯ�������ļ�����
    // reservedΪvoid *
    public boolean CLIENT_GetTotalFileCount(NativeLong lFindHandle, int[] pTotalCount,  Pointer reserved, int waittime);
    
    // ���ò�ѯ��ת����
    // reservedΪvoid *
    public boolean  CLIENT_SetFindingJumpOption(NativeLong lFindHandle, NET_FINDING_JUMP_OPTION_INFO pOption, Pointer reserved, int waittime);
    
    // �����ļ�:nFilecount:��Ҫ��ѯ������, ����ֵΪý���ļ����� ����ֵ<nFilecount����Ӧʱ����ڵ��ļ���ѯ���
    // pMediaFileInfoΪvoid *
    // reservedΪvoid *
    public int CLIENT_FindNextFileEx(NativeLong lFindHandle, int nFilecount, Pointer pMediaFileInfo, int maxlen, Pointer reserved, int waittim);
    
    // ����¼���ļ�����
    public boolean CLIENT_FindCloseEx(NativeLong lFindHandle);
    
    // ʵʱ�ϴ����ܷ������ݣ�ͼƬ(��չ�ӿ�,bNeedPicFile��ʾ�Ƿ���ͼƬ�ļ�,Reserved����ΪRESERVED_PARA) 
    // bNeedPicFileΪBOOL���ͣ�ȡֵ��ΧΪ0����1
    public NativeLong CLIENT_RealLoadPictureEx(NativeLong lLoginID, int nChannelID, int dwAlarmType, int bNeedPicFile, fAnalyzerDataCallBack cbAnalyzerData, Pointer dwUser, Pointer Reserved);
    
    // ֹͣ�ϴ����ܷ������ݣ�ͼƬ
    public boolean CLIENT_StopLoadPic(NativeLong lAnalyzerHandle);
    
    // ����ץͼ�ص�����
    public void CLIENT_SetSnapRevCallBack(fSnapRev OnSnapRevMessage, NativeLong dwUser);
    
    // ץͼ������չ�ӿ�
    public boolean CLIENT_SnapPictureEx(NativeLong lLoginID, SNAP_PARAMS stParam, int[] reserved);
    
    // �첽������������IPC��NVS���豸
    public NativeLong CLIENT_StartSearchDevices(fSearchDevicesCB cbSearchDevices, Pointer pUserData, Pointer szLocalIp);
    
    // ֹͣ�첽������������IPC��NVS���豸
    public boolean CLIENT_StopSearchDevices(NativeLong lSearchHandle);
    
    // ͬ�������������豸IP (pIpSearchInfo�ڴ����û������ͷ�)
    // szLocalIpΪ����IP���ɲ�������
    public boolean CLIENT_SearchDevicesByIPs(DEVICE_IP_SEARCH_INFO pIpSearchInfo, fSearchDevicesCB cbSearchDevices, NativeLong dwUserData, String szLocalIp, int dwWaitTime);

    // ��ʼʵʱ����
    // rType  : NET_RealPlayType    ���ؼ�ؾ��
    public NativeLong CLIENT_RealPlayEx(NativeLong lLoginID, int nChannelID, HWND hWnd, int rType);
    
    // ֹͣʵʱԤ��--��չ     lRealHandleΪCLIENT_RealPlayEx�ķ���ֵ
    public boolean CLIENT_StopRealPlayEx(NativeLong lRealHandle);
    
    // ��ʼʵʱ����֧�����������ص��ӿ�     rType  : NET_RealPlayType   ���ؼ�ؾ��
    public NativeLong CLIENT_StartRealPlay(NativeLong lLoginID, int nChannelID, HWND hWnd, int rType, fRealDataCallBackEx cbRealData, fRealPlayDisConnect cbDisconnect, NativeLong dwUser, int dwWaitTime);

    // ֹͣʵʱԤ��
    public boolean CLIENT_StopRealPlay(NativeLong lRealHandle);
    
    // ����ʵʱ�������ݻص�������չ�ӿ�    lRealHandle��ؾ��
    public boolean CLIENT_SetRealDataCallBackEx(NativeLong lRealHandle, fRealDataCallBackEx cbRealData, NativeLong dwUser, int dwFlag);
    
    // ����ͼ��������
    // ��Ҫ����ͼ��ĵȼ�(0-6),��levelΪ0ʱ��ͼ������������levelΪ6ʱ��ͼ����ʵʱ��Level��Ĭ��ֵΪ3��ע�⣺ֱ�ӽ�������Ч 
    public boolean CLIENT_AdjustFluency(NativeLong lRealHandle, int nLevel);
    
    // ��������Ϊ�ļ�
    public boolean CLIENT_SaveRealData(NativeLong lRealHandle, String pchFileName);

    // ������������Ϊ�ļ�
    public boolean CLIENT_StopSaveRealData(NativeLong lRealHandle);
    
    // ������
    public boolean CLIENT_OpenSound(NativeLong hPlayHandle);
    
    // �ر�����
    public boolean CLIENT_CloseSound();
    
    // ��ȡ������Ч��ʾԴ
    // pInParam  ��Ӧ  NET_IN_MATRIX_GET_CAMERAS
    // pOutParam ��Ӧ  NET_OUT_MATRIX_GET_CAMERAS
    public boolean CLIENT_MatrixGetCameras(NativeLong lLoginID, NET_IN_MATRIX_GET_CAMERAS pInParam, NET_OUT_MATRIX_GET_CAMERAS pOutParam, int nWaitTime);

    // ץͼͬ���ӿ�,��ͼƬ����ֱ�ӷ��ظ��û�
    public boolean CLIENT_SnapPictureToFile(NativeLong lLoginID, final NET_IN_SNAP_PIC_TO_FILE_PARAM pInParam, NET_OUT_SNAP_PIC_TO_FILE_PARAM pOutParam, int nWaitTime);
    
    // ��ѯʱ����ڵ�����¼���ļ�
    // nRecordFileType ¼������ 0:����¼��  1:�ⲿ����  2:��̬��ⱨ��  3:���б���  4:���Ų�ѯ   5:���������ѯ   6:¼��λ����ƫ��������   8:�����Ų�ѯͼƬ(Ŀǰ��HB-U��NVS�����ͺŵ��豸֧��)  9:��ѯͼƬ(Ŀǰ��HB-U��NVS�����ͺŵ��豸֧��)  10:���ֶβ�ѯ    15:�����������ݽṹ(��������)  16:��ѯ����͸��������¼���ļ�
    // nriFileinfo ���ص�¼���ļ���Ϣ����һ�� NET_RECORDFILE_INFO �ṹ���� 
    // maxlen �� nriFileinfo�������󳤶�(��λ�ֽڣ�������(100~200)*sizeof(NET_RECORDFILE_INFO)֮��) 
    // filecount���ص��ļ���������������������ֻ�ܲ鵽������Ϊֹ��¼���¼; 
    // bTime �Ƿ�ʱ���(Ŀǰ��Ч) 
    public boolean CLIENT_QueryRecordFile(NativeLong lLoginID, int nChannelId, int nRecordFileType, NET_TIME tmStart, NET_TIME tmEnd, String pchCardid, NET_RECORDFILE_INFO[] stFileInfo, int maxlen, IntByReference filecount, int waittime, boolean bTime);
    
    // ��ѯʱ������Ƿ���¼���ļ�   bResult���������true��¼��falseû¼��
    public boolean CLIENT_QueryRecordTime(NativeLong lLoginID, int nChannelId, int nRecordFileType, NET_TIME tmStart, NET_TIME tmEnd, String pchCardid, IntByReference bResult, int waittime);
    
    // ͨ��ʱ������¼��--��չ
    // nRecordFileType ��Ӧ EM_QUERY_RECORD_TYPE
    public NativeLong CLIENT_DownloadByTimeEx(
            NativeLong lLoginID, 
            int nChannelId, 
            int nRecordFileType, 
            NET_TIME tmStart, 
            NET_TIME tmEnd, 
            String sSavedFileName, 
            fTimeDownLoadPosCallBack cbTimeDownLoadPos, 
            NativeLong dwUserData, 
            fDataCallBack fDownLoadDataCallBack, 
            NativeLong dwDataUser, 
            Pointer pReserved);
    
    // ֹͣ¼������
    public boolean CLIENT_StopDownload(NativeLong lFileHandle);

    // ��̨������չ�ӿ�,֧����ά���ٶ�λ,����
    // dwStop����ΪBOOL, ȡֵ0����1
    // dwPTZCommandȡֵΪNET_EXTPTZ_ControlType�е�ֵ������NET_PTZ_ControlType�е�ֵ
    public boolean CLIENT_DHPTZControlEx2(NativeLong lLoginID, int nChannelID, int dwPTZCommand, int lParam1, int lParam2, int lParam3, int dwStop, Pointer param4);
       
    // �豸����(param�ڴ����û������ͷ�)  emType��Ӧ ö�� CtrlType
    public boolean CLIENT_ControlDevice(NativeLong lLoginID, int emType, Pointer param, int waittime);
    
    // �豸������չ�ӿڣ����� CLIENT_ControlDevice (pInBuf, pOutBuf�ڴ����û������ͷ�)
    // emType��ȡֵΪCtrlType�е�ֵ
    public boolean CLIENT_ControlDeviceEx(NativeLong lLoginID, int emType, Pointer pInBuf, Pointer pOutBuf, int nWaitTime);
    
    // ��ѯ������Ϣ(lpOutBuffer�ڴ����û������ͷ�)
    public boolean CLIENT_GetDevConfig(NativeLong lLoginID, int dwCommand, int lChannel, Pointer lpOutBuffer, int dwOutBufferSize, Pointer lpBytesReturned,int waittime);

    // ����������Ϣ(lpInBuffer�ڴ����û������ͷ�)
    public boolean CLIENT_SetDevConfig(NativeLong lLoginID, int dwCommand, int lChannel, Pointer lpInBuffer, int dwInBufferSize, int waittime);
    
    // ��ѯ�豸״̬(pBuf�ڴ����û������ͷ�)
    // pBufָ��char *,�������
    // pRetLenָ��int *;���������ʵ�ʷ��ص����ݳ��ȣ���λ�ֽ�
    public boolean CLIENT_QueryDevState(NativeLong lLoginID, int nType, Pointer pBuf, int nBufLen, Pointer pRetLen, int waittime);
    
    // ��ȡ�豸�����ӿ�
    // pInBufָ��void*����������ṹ��ָ��       pOutBufָ��void*����������ṹ��ָ��
    public boolean CLIENT_GetDevCaps(NativeLong lLoginID, int nType, Pointer pInBuf, Pointer pOutBuf, int nWaitTime);
    
    // ֹͣ���ı���
    public boolean CLIENT_StopListen(NativeLong lLoginID);
    
    // ��ϵͳ������ѯ�ӿڣ���ѯϵͳ������Ϣ(��Json��ʽ�����������SDK)(szOutBuffer�ڴ����û������ͷ�)
    // szCommand: ��Ӧ����鿴����
    // szOutBuffer: ��ȡ������Ϣ, ͨ�� CLIENT_ParseData ����
    // error ָ�� int * �� ���������0��ʾ�豸���صģ�С��0��ʾ���岻��������У�������
    public boolean CLIENT_QueryNewSystemInfo(NativeLong lLoginID, String szCommand, int nChannelID, byte[] szOutBuffer, int dwOutBufferSize, int[] error, int waittime);
    
    // ������Ƶͳ��ժҪ��Ϣ
    public NativeLong CLIENT_AttachVideoStatSummary(NativeLong lLoginID, NET_IN_ATTACH_VIDEOSTAT_SUM pInParam, NET_OUT_ATTACH_VIDEOSTAT_SUM pOutParam, int nWaitTime);

    // ȡ��������Ƶͳ��ժҪ��Ϣ��lAttachHandleΪCLIENT_AttachVideoStatSummary�ķ���ֵ
    public boolean CLIENT_DetachVideoStatSummary(NativeLong lAttachHandle);
    
    // ��ʼ��ѯ��Ƶͳ����Ϣ/��ȡ����ͳ����Ϣ
    public NativeLong CLIENT_StartFindNumberStat(NativeLong lLoginID, NET_IN_FINDNUMBERSTAT pstInParam, NET_OUT_FINDNUMBERSTAT pstOutParam);

    // ������ѯ��Ƶͳ��/������ѯ����ͳ��
    public int CLIENT_DoFindNumberStat(NativeLong lFindHandle, NET_IN_DOFINDNUMBERSTAT pstInParam, NET_OUT_DOFINDNUMBERSTAT pstOutParam);

    // ������ѯ��Ƶͳ��/������ѯ����ͳ��
    public boolean CLIENT_StopFindNumberStat(NativeLong lFindHandle);
   
    // ���������Խ�ģʽ,�ͻ��˷�ʽ���Ƿ�������ʽ
    // emType : ��ʽ���� ���� EM_USEDEV_MODE
    public boolean CLIENT_SetDeviceMode(NativeLong lLoginID, int emType, Pointer pValue);
    
    ///////////////// ¼��ط���ؽӿ� ///////////////////////
    // ��ʱ�䷽ʽ�ط�--��չ�ӿ�
    public NativeLong CLIENT_PlayBackByTimeEx(NativeLong lLoginID, int nChannelID, NET_TIME lpStartTime, NET_TIME lpStopTime, HWND hWnd, 
                                               fDownLoadPosCallBack cbDownLoadPos, NativeLong dwPosUser, 
                                               fDataCallBack fDownLoadDataCallBack, NativeLong dwDataUser);
    // ֹͣ¼��طŽӿ�
    public boolean CLIENT_StopPlayBack(NativeLong lPlayHandle);
    
    // ��ȡ�ط�OSDʱ��
    public boolean CLIENT_GetPlayBackOsdTime(NativeLong lPlayHandle, NET_TIME lpOsdTime, NET_TIME lpStartTime, NET_TIME lpEndTime);

    // ��ͣ��ָ�¼��ط�
    // bPause: 1 - ��ͣ	0 - �ָ� 
    public boolean CLIENT_PausePlayBack(NativeLong lPlayHandle, int bPause);
    
    // ���¼��ط�
    public boolean CLIENT_FastPlayBack(NativeLong lPlayHandle);

    // ����¼��ط�
    public boolean CLIENT_SlowPlayBack(NativeLong lPlayHandle);
 
    // �ָ������ط��ٶ�
    public boolean CLIENT_NormalPlayBack(NativeLong lPlayHandle);
    
    // ��ѯ�豸��ǰʱ��
    public boolean CLIENT_QueryDeviceTime(NativeLong lLoginID, NET_TIME pDeviceTime, int waittime);
    
    // �����豸��ǰʱ��
    public boolean CLIENT_SetupDeviceTime(NativeLong lLoginID, NET_TIME pDeviceTime);
    
    // ������ȡ�ɫ�ȡ��Աȶȡ����ͶȵĲ���      
    // param1/param2/param3/param4 �ĸ�������Χ0~255
  	public boolean CLIENT_ClientGetVideoEffect(NativeLong lPlayHandle, byte[] param1, byte[] param2, byte[] param3, byte[] param4);

  	// �������ȡ�ɫ�ȡ��Աȶȡ����ͶȵĲ���    
  	// nBrightness/nContrast/nHue/nSaturation�ĸ�����Ϊ unsigned byte ��Χ0~255
  	public boolean CLIENT_ClientSetVideoEffect(NativeLong lPlayHandle, byte nBrightness, byte nContrast, byte nHue, byte nSaturation);    

	//------------------------�û�����-----------------------
	// ��ѯ�û���Ϣ--���֧��64ͨ���豸  
	// pReservedָ��void*  
	public boolean CLIENT_QueryUserInfoNew(NativeLong lLoginID, USER_MANAGE_INFO_NEW info, Pointer pReserved, int nWaittime);
	
	// �����û���Ϣ�ӿ�--�����豸�û�--���֧��64ͨ���豸
	// opParamָ��void*           subParamָ��void*   
	// pReservedָ��void*       
	// opParam�������û���Ϣ�����뻺�壩��subParam�������û���Ϣ�ĸ������뻺�壩��Ӧ�ṹ������USER_GROUP_INFO_NEW��USER_INFO_NEW
	public boolean CLIENT_OperateUserInfoNew(NativeLong lLoginID, int nOperateType, Pointer opParam, Pointer subParam, Pointer pReserved, int nWaittime);
	
	
	//----------------------�����Խ�--------------------------
	// ���豸���������Խ�����          pfcb���û��Զ�������ݻص��ӿ�
	public NativeLong CLIENT_StartTalkEx(NativeLong lLoginID, pfAudioDataCallBack pfcb, NativeLong dwUser);
	
	// ֹͣ�����Խ�        lTalkHandle�����Խ��������CLIENT_StartTalkEx�ķ��� ֵ
    public boolean CLIENT_StopTalkEx(NativeLong lTalkHandle);

    // ��������¼������(ֻ��Windowsƽ̨����Ч)��¼���ɼ���������Ƶ����ͨ��CLIENT_StartTalkEx�Ļص������ص����û�����Ӧ������CLIENT_RecordStopEx
    // lLoginID��CLIENT_Login�ķ���ֵ 
    public boolean CLIENT_RecordStartEx(NativeLong lLoginID);

    // ֹͣ����¼��(ֻ��Windowsƽ̨����Ч)����Ӧ������CLIENT_RecordStartEx��
    public boolean CLIENT_RecordStopEx(NativeLong lLoginID);
    
    // ���豸�����û�����Ƶ���ݣ���������ݿ����Ǵ�CLIENT_StartTalkEx�Ļص��ӿ��лص�����������
    public NativeLong CLIENT_TalkSendData(NativeLong lTalkHandle, Pointer pSendBuf, int dwBufSize);
    
    // ������Ƶ������չ�ӿ�(ֻ��Windowsƽ̨����Ч)    pAudioDataBuf��Ҫ��������Ƶ�������� 
    public boolean CLIENT_AudioDecEx(NativeLong lTalkHandle, Pointer pAudioDataBuf, int dwBufSize);
    
    //-------------------������-------------------------
    // ����ѯ������ѯ��¼          pInParam��ѯ��¼����        pOutParam���ز�ѯ���  
    // �����ȵ��ñ��ӿڻ�ò�ѯ������ٵ���  CLIENT_FindNextRecord������ȡ��¼�б���ѯ��Ͽ��Ե���CLIENT_FindRecordClose�رղ�ѯ����� 
    public boolean CLIENT_FindRecord(NativeLong lLoginID, NET_IN_FIND_RECORD_PARAM pInParam, NET_OUT_FIND_RECORD_PARAM pOutParam, int waittime);
    
    // ���Ҽ�¼:nFilecount:��Ҫ��ѯ������, ����ֵΪý���ļ����� ����ֵС��nFilecount����Ӧʱ����ڵ��ļ���ѯ���
    public boolean CLIENT_FindNextRecord(NET_IN_FIND_NEXT_RECORD_PARAM pInParam, NET_OUT_FIND_NEXT_RECORD_PARAM pOutParam, int waittime);
    
    // ������¼����,lFindHandle��CLIENT_FindRecord�ķ���ֵ 
    public boolean CLIENT_FindRecordClose(NativeLong lFindHandle);
    
    // �ڰ��������� ,pstOutParam = null;
    public boolean CLIENT_OperateTrafficList(NativeLong lLoginID ,  NET_IN_OPERATE_TRAFFIC_LIST_RECORD pstInParam, NET_OUT_OPERATE_TRAFFIC_LIST_RECORD pstOutParam , int waittime);
    
    // �ļ��ϴ����ƽӿڣ��������ϴ���Ҫ�����������ʹ�ã�CLIENT_FileTransmit�� NET_DEV_BLACKWHITETRANS_START��  NET_DEV_BLACKWHITETRANS_SEND��   NET_DEV_BLACKWHITETRANS_STOP��������ʾ
    public NativeLong CLIENT_FileTransmit(NativeLong lLoginID, int nTransType, Pointer szInBuf, int nInBufLen, fTransFileCallBack cbTransFile, NativeLong dwUserData, int waittime);    

  	// ��ѯ�豸��Ϣ
  	public boolean CLIENT_QueryDevInfo(NativeLong lLoginID, int nQueryType, Pointer pInBuf, Pointer pOutBuf, Pointer pReservedL, int nWaitTime);
  	
  	// ------------------����GPS------------------------- 	
  	// ����GPS���Ļص�����--��չ
  	public void CLIENT_SetSubcribeGPSCallBackEX(fGPSRevEx OnGPSMessage, NativeLong dwUser);
  	
  	// GPS��Ϣ����       
  	// bStart:�����Ƕ��Ļ���ȡ��          InterTime:����ʱ����GPS����Ƶ��(��λ��)
  	// KeepTime:���ĳ���ʱ��(��λ��) ֵΪ-1ʱ,����ʱ��Ϊ����ֵ,����Ϊ���ö���     
  	public boolean CLIENT_SubcribeGPS (NativeLong lLoginID, int bStart, int KeepTime, int InterTime);
	
    // ͬ���ļ��ϴ�, ֻ������С�ļ�
  	public boolean CLIENT_UploadRemoteFile(NativeLong lLoginID, NET_IN_UPLOAD_REMOTE_FILE pInParam, NET_OUT_UPLOAD_REMOTE_FILE pOutParam, int nWaitTime);

    // ������¼����
  	public NativeLong CLIENT_ParkingControlAttachRecord(NativeLong lLoginID, NET_IN_PARKING_CONTROL_PARAM pInParam, NET_OUT_PARKING_CONTROL_PARAM pOutParam, int nWaitTime);
  	
  	// ȡ��������¼����
  	public boolean CLIENT_ParkingControlDetachRecord(NativeLong lAttachHandle);
 
    // ��ʼ������¼��ѯ
  	public NativeLong CLIENT_ParkingControlStartFind(NativeLong lLoginID, NET_IN_PARKING_CONTROL_START_FIND_PARAM pInParam, NET_OUT_PARKING_CONTROL_START_FIND_PARAM pOutParam, int waittime);

  	// ��ȡ������¼
  	public boolean CLIENT_ParkingControlDoFind(NativeLong lFindeHandle, NET_IN_PARKING_CONTROL_DO_FIND_PARAM pInParam, NET_OUT_PARKING_CONTROL_DO_FIND_PARAM pOutParam, int waittime);

  	// ����������¼��ѯ
  	public boolean CLIENT_ParkingControlStopFind(NativeLong lFindHandle);
  	
  	// ��λ״̬����,pInParam��pOutParam�ڴ����û������ͷ�
  	public NativeLong CLIENT_ParkingControlAttachParkInfo(NativeLong lLoginID, NET_IN_PARK_INFO_PARAM pInParam, NET_OUT_PARK_INFO_PARAM pOutParam, int nWaitTime);

  	// ȡ����λ״̬����
  	public boolean CLIENT_ParkingControlDetachParkInfo(NativeLong lAttachHandle);
  	
  	// ��Դ����,pInParam��pOutParam�ڴ����û������ͷ�
  	public boolean CLIENT_PowerControl(NativeLong lLoginID, NET_IN_WM_POWER_CTRL pInParam, NET_OUT_WM_POWER_CTRL pOutParam, int nWaitTime);

  	// ����/����Ԥ��,pInParam��pOutParam�ڴ����û������ͷ�
  	public boolean CLIENT_LoadMonitorWallCollection(NativeLong lLoginID, NET_IN_WM_LOAD_COLLECTION pInParam, NET_OUT_WM_LOAD_COLLECTION pOutParam, int nWaitTime);
  	public boolean CLIENT_SaveMonitorWallCollection(NativeLong lLoginID, NET_IN_WM_SAVE_COLLECTION pInParam, NET_OUT_WM_SAVE_COLLECTION pOutParam, int nWaitTime);
  	
  	// ��ȡ����ǽԤ��,pInParam��pOutParam�ڴ����û������ͷ�
  	public boolean CLIENT_GetMonitorWallCollections(NativeLong lLoginID, NET_IN_WM_GET_COLLECTIONS pInParam, NET_OUT_WM_GET_COLLECTIONS pOutParam, int nWaitTime);

  	// ��ѯ/������ʾԴ(pstuSplitSrc�ڴ����û������ͷ�),  nWindowΪ-1��ʾ���д��� ; pstuSplitSrc ��Ӧ NET_SPLIT_SOURCE ָ��
  	public boolean CLIENT_GetSplitSource(NativeLong lLoginID, int nChannel, int nWindow, NET_SPLIT_SOURCE[] pstuSplitSrc, int nMaxCount, IntByReference pnRetCount, int nWaitTime);
  	public boolean CLIENT_SetSplitSource(NativeLong lLoginID, int nChannel, int nWindow, NET_SPLIT_SOURCE[] pstuSplitSrc, int nSrcCount, int nWaitTime);

  	// ��ѯ�����ӿ���Ϣ(pstuCardList�ڴ����û������ͷ�)
  	public boolean CLIENT_QueryMatrixCardInfo(NativeLong lLoginID, NET_MATRIX_CARD_LIST pstuCardList, int nWaitTime);
  	
  	// ����ָ�������ܷ������� - ͼƬ
  	// emType �ο� EM_FILE_QUERY_TYPE
  	public NativeLong CLIENT_DownloadMediaFile(NativeLong lLoginID, int emType, Pointer lpMediaFileInfo, String sSavedFileName, fDownLoadPosCallBack cbDownLoadPos, Pointer dwUserData,  Pointer reserved);

  	// ֹͣ��������
  	public boolean CLIENT_StopDownloadMediaFile(NativeLong lFileHandle);


}

