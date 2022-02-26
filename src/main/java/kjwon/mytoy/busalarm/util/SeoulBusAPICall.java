package kjwon.mytoy.busalarm.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class SeoulBusAPICall {


    String secretKey = "hqVi%2FbqOLd9g7fZMvFzc63NlvrwGzzKBpBG9HQxjmxDylJ9B%2BgRW%2FLt%2FpzvT4jWRU8UIC%2Bcc6tCHWiLkkULm1A%3D%3D";
    String stationInfoPath = "http://ws.bus.go.kr/api/rest/stationinfo/";
    String busPosPath = "http://ws.bus.go.kr/api/rest/buspos/";
    String busRouteInfoPath = "http://ws.bus.go.kr/api/rest/busRouteInfo/";
    String arrivePath = "http://ws.bus.go.kr/api/rest/arrive/";

     /*Search Station Info*/
    /**
     * 정류소 명칭 검색
     * @param stSrch : 정류소명 검색어
     */
    public Document getStationByNameList(String stSrch) throws IOException {
        return Jsoup.connect(stationInfoPath + "getStationByName"
                        + "?serviceKey=" + secretKey
                        + "&stSrch=" + stSrch
                )
                .get();
    }

    /**
     * 정류소고유번호를 입력받아 버스도착정보목록을 조회한다.
     * @param arsId : 정류소고유번호
     */
    public Document getStationByUidItem(String arsId) throws IOException {
        return Jsoup.connect(stationInfoPath + "getStationByUid"
                        + "?serviceKey=" + secretKey
                        + "&arsId=" + arsId
                )
                .get();
    }

    /**
     * 정류소고유번호를 입력받아 경유하는 노선목록을 조회한다.
     * @param arsId : 정류소고유번호
     */
    public Document getRouteByStationList(String arsId) throws IOException {
        return Jsoup.connect(stationInfoPath + "getRouteByStation"
                        + "?serviceKey=" + secretKey
                        + "&arsId=" + arsId
                )
                .get();
    }

    /**
     * 정류소고유번호와 노선id를 입력받아 첫차/막차예정시간을 조회한다.
     * @param arsId : 정류소고유번호
     * @param busRouteId : 노선ID
     */
    public Document getBustimeByStationList(String arsId, String busRouteId) throws IOException {
        return Jsoup.connect(stationInfoPath + "getBustimeByStation"
                        + "?serviceKey=" + secretKey
                        + "&arsId=" + arsId
                        + "&busRouteId=" + busRouteId
                )
                .get();
    }

    /**
     * 저상버스가 운행되는 정류소 명칭 검색
     * @param stSrch : 정류소명 검색어
     */
    public Document getLowStationByNameList(String stSrch) throws IOException {
        return Jsoup.connect(stationInfoPath + "getLowStationByName"
                        + "?serviceKey=" + secretKey
                        + "&stSrch=" + stSrch
                )
                .get();
    }

    /**
     * 고유번호를 입력받은 정류소의 저상버스 도착정보 제공
     * @param arsId : 정류소고유번호
     */
    public Document getLowStaionByUidList(String arsId) throws IOException {
        return Jsoup.connect(stationInfoPath + "getLowStationByUid"
                        + "?serviceKey=" + secretKey
                        + "&arsId=" + arsId
                )
                .get();
    }

    /**
     * 좌표기반 근접 정류소 조회
     * @param tmX : 기준위치 X
     * @param tmY : 기준위치 Y
     * @param radius : 단위 m(미터)
     */
    public Document getStaionsByPosList(String tmX, String tmY, String radius) throws IOException {
        return Jsoup.connect(stationInfoPath + "getStationByPos"
                        + "?serviceKey=" + secretKey
                        + "&tmX=" + tmX
                        + "&tmY=" + tmY
                        + "&radius=" + radius
                )
                .get();
    }

    /*Search Bus Pos*/
    public Document getBusPosByRouteStList(String busRouteId, String startOrd, String endOrd) throws IOException {
        return Jsoup.connect(busPosPath + "getBusPosByRouteSt"
                        + "?serviceKey=" + secretKey
                        + "&busRouteId=" + busRouteId
                        + "&startOrd=" + startOrd
                        + "&endOrd=" + endOrd
                )
                .get();
    }

    /*Search busRoute Info*/

    /**
     * 노선별 경유 정류소 조회 서비스
     * @param busRouteId 노선ID
     * */
    public Document getStaionsByRouteList(String busRouteId) throws IOException {
        return Jsoup.connect(busRouteInfoPath + "getStaionByRoute"
                        + "?serviceKey=" + secretKey
                        + "&busRouteId=" + busRouteId
                )
                .get();
    }

    /**
     * 노선 기본정보 조회
     * @param busRouteId 노선ID
     * */
    public Document getRouteInfoItem(String busRouteId) throws IOException {
        return Jsoup.connect(busRouteInfoPath + "getRouteInfo"
                        + "?serviceKey=" + secretKey
                        + "&busRouteId=" + busRouteId
                )
                .get();
    }

    /**
     * 노선의 지도상 경로를 리턴한다.
     * @param busRouteId 노선ID
     * */
    public Document getRoutePathList(String busRouteId) throws IOException {
        return Jsoup.connect(busRouteInfoPath + "getRoutePath"
                        + "?serviceKey=" + secretKey
                        + "&busRouteId=" + busRouteId
                )
                .get();
    }

    /**
     * 노선번호에 해당하는 노선 목록 조회
     * @param strSrch 검색할 노선번호
     * */
    public Document getBusRouteList(String strSrch) throws IOException {
        return Jsoup.connect(busRouteInfoPath + "getBusRouteList"
                        + "?serviceKey=" + secretKey
                        + "&strSrch=" + strSrch
                )
                .get();
    }
    /*Search Arrive Info*/

    /**
     * 경유노선 전체 정류소 도착예정정보를 조회한다
     * @param busRouteId 노선 ID
     * */
    public Document getArrInfoByRouteAllList(String busRouteId) throws IOException {
        return Jsoup.connect(arrivePath + "getArrInfoByRouteAll"
                        + "?serviceKey=" + secretKey
                        + "&busRouteId=" + busRouteId
                )
                .get();
    }

    /**
     * 한 정류소의 특정노선의 도착예정정보를 조회한다
     * @param stId 정류소 ID
     * @param busRouteId 노선 ID
     * @param ord 정류소 순번
     * */
    public Document getArrInfoByRouteList(String stId, String busRouteId, String ord) throws IOException {
        return Jsoup.connect(arrivePath + "getArrInfoByRouteAll"
                        + "?serviceKey=" + secretKey
                        + "&stId=" + stId
                        + "&busRouteId=" + busRouteId
                        + "&ord=" + ord
                )
                .get();
    }

    /**
     * 정류소ID로 저상버스 도착예정정보를 조회한다
     * @param stId 정류소 ID
     * */
    public Document getLowArrInfoByStIdList(String stId) throws IOException {
        return Jsoup.connect(arrivePath + "getArrInfoByRouteAll"
                        + "?serviceKey=" + secretKey
                        + "&stId=" + stId
                )
                .get();
    }
    /**
     * 한 정류소의 특정노선의 저상버스 도착예정정보를 조회한다
     * @param stId 정류소 ID
     * @param busRouteId 노선 ID
     * @param ord 정류소 순번
     * */
    public Document getLowArrInfoByRouteList(String stId, String busRouteId, String ord) throws IOException {
        return Jsoup.connect(arrivePath + "getArrInfoByRouteAll"
                        + "?serviceKey=" + secretKey
                        + "&stId=" + stId
                        + "&busRouteId=" + busRouteId
                        + "&ord=" + ord
                )
                .get();
    }

    /*get*/
    public static Elements getItemList(Document document){
        return document.select("itemList");
    }

    public static String getArsId(Element element){
        Elements e = element.select("arsId");
        return Objects.requireNonNull(e.first()).html();
    }

    public static String getBusRouteId(Element element){
        Elements e = element.select("busRouteId");
        return Objects.requireNonNull(e.first()).html();
    }

    public static String getDirection(Element element){
        Elements e = element.select("direction");
        return Objects.requireNonNull(e.first()).html();
    }

    public static String getSationName(Element element){
        Elements e = element.select("stationNm");
        return Objects.requireNonNull(e.first()).html();
    }

    public static String getArriveMsg1(Element element){
        Elements e = element.select("arrmsg1");
        return Objects.requireNonNull(e.first()).html();
    }

    public static String getArriveMsg2(Element element){
        Elements e = element.select("arrmsg2");
        return Objects.requireNonNull(e.first()).html();
    }

    public static String getRtName(Element element){
        Elements e = element.select("rtNm");
        return Objects.requireNonNull(e.first()).html();
    }
    public static String getGpsX(Element element){
        Elements e = element.select("gpsX");
        return Objects.requireNonNull(e.first()).html();
    }
    public static String getGpsY(Element element){
        Elements e = element.select("gpsY");
        return Objects.requireNonNull(e.first()).html();
    }
    public static String getHeaderMsg(Document element){
        Elements e = element.select("headerMsg");
        return Objects.requireNonNull(e.first()).html();
    }
}
