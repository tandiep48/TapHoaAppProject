package com.example.taphoaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.taphoaapp.Basket.DataCommunication;
import com.example.taphoaapp.DetailProduct.DetailProductActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements DataCommunication {


    private TabLayout mTabLayout;
    private ViewPager viewMain;
    private BottomNavigationView bottomnavigation;
    private String passName,passCategory,passcolor,passsize  , order,active ,PrevActive, ActiPrev,userID;
    private int passPrice,passquantity,passSoluong;
    Bundle extras;
    Context mContext;
    DataCommunication mCallback;
    String PasssPassword;
    public boolean getaddtoBasket,getaddDonHang;
    Intent i ;
    String STT,STT2,Cate;
    Integer stt,stt2;
    List<String>Category;


    public boolean getaddmybasket() {
        return getaddtoBasket;
    }
    public void changeAddmyDonhang(boolean add) {
        this.getaddDonHang = add;
    }

    public boolean getaddmyDonhang() {
        return getaddDonHang;
    }

    @Override
    public void attachBaseContext(Context context) {
        mContext = context;
        super.attachBaseContext(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (DataCommunication) this;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataCommunication");
        }
    }
public void changeFragZeroTwo(int pos) {
        if (viewMain != null)
        viewMain.setCurrentItem(pos);
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Category =new ArrayList<>(Arrays.asList("quanao","electron","vanphong","sach"));

//        List<product_item> TAOproducts = new ArrayList<>();
//
//        List<String> COLOR = new ArrayList<>();
//        List<String> COLOR4 = new ArrayList<>();
//        List<String> COLOR2 = new ArrayList<>(Arrays.asList("Xám đậm","Xám nhạt","Trắng"));
//        List<String> COLOR3 = new ArrayList<>(Arrays.asList("Đen","Trắng","Xanh Than"));
//        List<String> COLOR5 = new ArrayList<>(Arrays.asList("XANH","HỒNG","TÍM"));
//        List<String> SIZE = new ArrayList<String>();
//        List<String> SIZE2 = new ArrayList<String>(Arrays.asList("40 Trang","80 trang"));
//        List<String> SIZE3 = new ArrayList<String>(Arrays.asList("A","B","C"));
//        List<String> SIZE4 = new ArrayList<String>(Arrays.asList("A5","A6","A7"));
//        List<String> SIZE5 = new ArrayList<String>();
//        COLOR.add("Trắng");
//        COLOR.add("Xanh");
//        SIZE.add("M");
//        SIZE.add("L");
//        SIZE.add("XL");
//        Map<String, Object> data = new HashMap<>();
//        Map<String, Object> data2 = new HashMap<>();
//
//        TAOproducts.add(new product_item());
//
////
//        TAOproducts.add(new product_item("vanphong","51",63000,132000,
//                "https://cf.shopee.vn/file/e8c95c1a2c766b92468957f1d80d41a5",
//                "https://cf.shopee.vn/file/10d622f4ecf009277a099f498de9dca0","https://cf.shopee.vn/file/fab9521c8200f56aa512e005089dee20",
//                "https://cf.shopee.vn/file/4ae9b3106f1f8b8ede23de88da962910",COLOR4,
//                "Vở có đường kẻ 4 ô ly vuông (2.5x2.5)mm rõ ràng, đều đặn\n" +
//                        "Giấy có độ trắng tự nhiên - chống mỏi mắt\n" +
//                        "-Kích thước: 170x240 mm\n" +
//                        "-Giấy định lượng: 120 g/m2\n" +
//                        "-Độ trắng: 95ISO\n" +
//                        " Nhà sản xuất: Công ty CP Giấy Hải Tiến\n" +
//                        "- Xuất xứ: Việt Nam \n" +
//                        "-Mẫu giao ngẫu nhiên\n" +
//                        "\n" +
//                        "Mặt giấy láng mịn, viết êm tay, phù hợp cho học sinh tiểu học luyện chữ.\n" +
//                        "Giấy bám mực tốt, không thấm, không nhòe khi viết.\n" +
//                        "\n" +
//                        "Thông tin thương hiệu\n" +
//                        "Công ty cổ phần Giấy Hải Tiến đang hoạt động sản xuất kinh doanh tại KCN Sài Đồng B, quận Long Biên, thành phố Hà Nội với quy mô hơn 500 CBCNV và hơn 30.000m2. Từ một cơ sở sản xuất nhỏ, sau hơn 30 năm hình thành và phát triển, Giấy Hải Tiến đã có bước phát triển vượt bậc và trở thành thương hiệu uy tín trên thị trường.\n" +
//                        "\n" +
//                        "Trong những năm qua, Công ty đã đầu tư lớn vào công nghệ và hiện đang sở hữu dây chuyền sản xuất giấy vở hiện đại bậc nhất Việt Nam. Theo đó, Giấy Hải Tiến đã phát triển nhiều dòng sản phẩm tập, vở, sổ tay đạt chất lượng cao, được nhiều thế hệ học sinh, sinh viên và các thầy cô giáo yêu mến, tin dùng. Các sản phẩm vở ôly nổi tiếng như: Ban Mai, Sắc màu, 123, Măng non, ABCD, Tuổi thơ… hay vở kẻ ngang như Newway, Xuân Hạ Thu Đông, Giáo án... \n" +
//                        "\n" +
//                        "Đặc biệt, vở ép keo đa lớp mang nhãn hiệu Haplus đã dần trở nên quen thuộc với nhiều học sinh, sinh viên. Ngoài ra, Giấy Hải Tiến còn cung cấp các sản phẩm vở vẽ, vở chép nhạc, giấy kiểm tra… đạt chất lượng cao bên cạnh các sản phẩm giấy văn phòng và sổ tay như Business, Classic, Meeting... Sản phẩm  đảm bảo giấy viết êm tay, không gây hại mắt, không thấm mực và bìa vở nhập ngoại. \n" +
//                        "\n" +
//                        "Giấy Hải Tiến được người tiêu dùng bình chọn \"Hàng Việt Nam chất lượng cao\" liên tục từ năm 2002 đến năm 2020, sản phẩm giấy vở Hải Tiến đã và đang là thương hiệu chiếm thị phần ngày càng lớn tại thị trường Hà Nội, các tỉnh miền Bắc, miền Trung và Nam Bộ.",
//                "HẢI TIẾN Vở 4 ô ly Cao Cấp 7 Sao - Ban Mai 48 trang, 80 trang (4 ô ly 2,5x2,5mm) Lốc 10 quyển","VP01",SIZE2,50));
//
//        TAOproducts.add(new product_item("vanphong","44",7700,13000,"https://cf.shopee.vn/file/f449ee98a156b5bc103825e7ea24d750",
//        "https://cf.shopee.vn/file/06e09507ac917cc184cfb115b324f356","https://cf.shopee.vn/file/3302610e888d41923733d60721e5b069,",
//                "https://cf.shopee.vn/file/03d30b8f1ddb95574fc2bfd72c56da10",COLOR4,"Thông tin sản phẩm: Lịch để bàn mini 2022\n" +
//                "Chất liệu: Giấy\n" +
//                "Trọng lượng: 18 g\n" +
//                "Kích thước gói hàng: 93 * 64 mm\n" +
//                "Gói hàng bao gồm: 1 sản phẩm \n" +
//                "\n" +
//                "Sản phẩm trong cửa hàng về cơ bản là hàng có sẵn \n" +
//                "Chúng tôi chỉ bán các sản phẩm chất lượng cao và kiểm tra chất lượng trước khi vận chuyển.\n" +
//                "Nếu bạn có bất kỳ thắc mắc hoặc góp ý nào, bạn có thể liên hệ với chúng tôi.\n" +
//                "\n" +
//                "▲ Lưu ý:\n" +
//                "• Do chênh lệch cài đặt ánh sáng và màn hình, màu sắc sản phẩm có thể hơi khác so với hình ảnh. \n" +
//                "• Vui lòng cho phép sai số 1-3cm về số đo do cách đo lường thủ công. \n" +
//                "• Xin cảm ơn bạn vì đã thông cảm","Lịch để bàn mini 2022 họa tiết hoạt hình dễ thương","VP02",SIZE3,50));
//
//        TAOproducts.add(new product_item("vanphong","34",19900, 30000,"https://cf.shopee.vn/file/fefb9bb2af1a82be411114fb31880a4e",
//        "https://cf.shopee.vn/file/9b1fe637f152e1bf635c3dd849081a47","https://cf.shopee.vn/file/d7234bb96acf53c55beeca226c4b4722",
//        "https://cf.shopee.vn/file/5eec665007a4b5844943a046534dc7d8",COLOR5,
//        "Tên Sản Phẩm : Thùng Rác Mini YSB Trang Trí Bàn Học A201\n" +
//                "\uD83C\uDF35 Kích Thước : 16.5 * 13 cm\n" +
//                "\uD83C\uDF35 Màu Sắc: Ngẫu Nhiên\n" +
//                "\uD83C\uDF35 Trọng Lượng : 150gr\n" +
//                "\n" +
//                "Đặc điểm nổi bật\n" +
//                "\uD83C\uDF44 Bàn làm việc thường xuyên có rác nhỏ sẽ làm ảnh hưởng xấu đến hình tượng cũng như hiệu suất làm việc.\n" +
//                "\uD83C\uDF44 Hộp Đựng Rác Văn Phòng Décor T6098 là sản phẩm thùng rác nhỏ, chuyên dùng để đựng các mẩu rác nhỏ như vỏ bánh, kẹo, giấy vụn, túi ni lông... giúp cho bàn làm việc luôn sạch sẽ\n" +
//                "\uD83C\uDF44 Thiết kế xinh xắn, chất liệu nhựa cao cấp an toàn khi sử dụng.\n" +
//                "===================================================\n" +
//                "\uD83C\uDF38\uD83C\uDF38\uD83C\uDF38 KHÁCH HÀNG VUI LÒNG ĐỌC KỸ TRƯỚC KHI MUA HÀNG NHÉ \uD83C\uDF38\uD83C\uDF38\uD83C\uDF38\n" +
//                "✔️   Hỗ trợ freeship cho đơn hàng 50k (áp mã giảm giá freeship)\n" +
//                "✔️  Không bán hàng kém chất lượng tới tay người tiêu dùng.\n" +
//                "✔️  Hàng luôn sẵn kho.Giá luôn tốt tuyệt đối.\n" +
//                "✔️  Thời gian giao hàng đến khách hàng là do bên vận chuyển giao, shop không phải trực tiếp giao hàng, các vấn đề về chậm trễ đơn hàng vui lòng liên hệ Teeci. Khách đọc mã số đơn hàng sẽ được nhân viên hỗ trợ \n" +
//                "✔️SHOP CHỈ GIẢI QUYẾT KHIẾU NẠI KHI CÓ VIDEO KHUI HÀNG( KHÔNG CÓ VIDEO SHOP XIN TỪ CHỐI GIẢI QUYẾT)\n" +
//                " \uD83D\uDC9F\uD83D\uDC9F\uD83D\uDC9F SHOP CAM KẾT \uD83D\uDC9F\uD83D\uDC9F\uD83D\uDC9F\n" +
//                "✔️Về sản phẩm: Shop cam kết cả về CHẤT LIỆU cũng như HÌNH DÁNG ( đúng với những gì được nêu bật trong phần mô tả sản phẩm).\n" +
//                "✔️Về giá cả : Shop nhập với số lượng nhiều và trực tiếp nên chi phí sẽ là RẺ NHẤT nhé. \n" +
//                "✔️Về dịch vụ: Shop sẽ cố gắng trả lời hết những thắc mắc xoay quanh sản phẩm nhé. \n" +
//                "✔️Thời gian chuẩn bị hàng: Hàng có sẵn, thời gian chuẩn bị tối ưu nhất. \uD83D\uDC9F\uD83D\uDC9F\uD83D\uDC9FQuyền Lợi của Khách Hàng\uD83D\uDC9F\uD83D\uDC9F\uD83D\uDC9F\n" +
//                "✔️ Khách hàng cũ : Mua lần thứ 2 trở đi sẽ được nhận mã giảm giá của shop << ib ngay cho mình nha>> để nhận nào . \n" +
//                "\uD83D\uDCE3Phản hồi : \n" +
//                "1 Xin vui lòng để lại cho chúng tôi một phản hồi tích cực (5 sao), nếu bạn hài lòng với các mặt hàng của chúng tôi. \n" +
//                "2 Vui lòng liên hệ với chúng tôi trước khi để lại bất kỳ phản hồi tiêu cực hoặc mở một tranh chấp. \n" +
//                "3 Xin vui lòng cho chúng tôi cơ hội để giải quyết bất kỳ vấn đề.\n" +
//                "\uD83D\uDCCC LƯU Ý  khi theo dõi (Follow) Shop bạn sẽ được gì ? \n" +
//                "\uD83C\uDF81 Nhận được mã giảm giá khi theo dõi shop\n" +
//                "\uD83C\uDF81  Nhận được những mã giảm giá sớm nhất. \n" +
//                "\uD83C\uDF81 Cập nhật những xu hướng mới nhất.. Trân Trọng","Thùng Rác Mini Để Bàn Văn Phòng-Xọt Rác Mini YSB YBS Đựng Đồ Dùng","VP03",SIZE,100));
//
//        TAOproducts.add(new product_item("vanphong","34",5000,8000,"https://cf.shopee.vn/file/d5b4bfdbbd0430e287446d8519e854ab",
//        "https://cf.shopee.vn/file/a0cd5dd82b133b6936dd5b929a5e3e95","https://cf.shopee.vn/file/0deb1c3436f53b8e373c689a007b2297",
//                "https://cf.shopee.vn/file/46f5b97f4a08d330a2fc0d7b6de84472",COLOR,
//        "Túi đựng tải liệu khổ A5 / A6 / A7 gắn sổ còng lá rời 6 lỗ có khoá kéo màu trong suốt \n" +
//                "\n" +
//                "Miêu tả sản phẩm: \n" +
//                "Tên sản phẩm: Túi đựng gắn sổ còng\n" +
//                "Đặc điểm sản phẩm: A5 / A6 / A7\n" +
//                "Chất liệu: PVC\n" +
//                "Kích thước: A7: khoảng 130 * 85MM / A6: 170 * 110MM / A5: 210 * 150MM\n" +
//                "Công dụng: dùng đựng tiền xu, tiền lẻ, thẻ, hình dán, v.v.\n" +
//                "Kiểu dáng: khoá kéo\n" +
//                "Số lượng: 1\n" +
//                "\n" +
//                "Thông tin sản phẩm: \n" +
//                "Túi đựng có khóa kéo A5 / A6 / A7, túi lá rời trong suốt, sổ tay, túi đựng cầm tay, túi đựng giấy ghi chú PVC\n" +
//                "Vị trí 6 lỗ còng tiêu chuẩn, có thể kẹp vào vở\n" +
//                "\n" +
//                "Gói hàng bao gồm: 1 * túi đựng gắn sổ còng lá rời sáu lỗ\n" +
//                "\n" +
//                "\n" +
//                "Lưu ý: Do chênh lệch cài đặt ánh sáng và màn hình, màu sắc sản phẩm có thể hơi khác so với hình ảnh. Vui lòng cho phép sai số một chút về số đo do cách đo lường thủ công. \n" +
//                "★ Bạn thân mến: Bạn có thể thấy rằng ai đó bán với giá thấp hơn chúng tôi nhưng họ không thể đảm bảo chất lượng và dịch vụ như chúng tôi, Hãy tin tưởng chúng tôi và theo dõi cửa hàng của chúng tôi. Xin cảm ơn bạn rất nhiều!\n",
//        "Túi Đựng Tài Liệu Khổ A5 / A6 / A7 6 Lỗ Trong Suốt Tiện Dụng","VP04",SIZE4,30));
//
//
//
//        TAOproducts.add(new product_item("sach","25",175000,234000,"https://cf.shopee.vn/file/7445d54acfd73a894f327ec87fe8dfb7",
//        "https://cf.shopee.vn/file/5c8b8b55bd6bfc7e55bf2a7c34b19a6d","https://cf.shopee.vn/file/8cf569dcf2b2bbe1a2d192f489b81b7d",
//                "https://cf.shopee.vn/file/724c7dd8d23d0a7ab5dddcf3d7eac67b",COLOR,
//        "Bộ sách Ehon \"Những chuyện vụn vặt của Maru\" phù hợp cho trẻ từ 24 tháng tuổi trở lên, đặc biệt là trẻ 4 - 6 tuổi. Series bao gồm có 6 cuốn, chia thành 2 bộ nhỏ hơn là Maru Lớn Rồi (gồm \"Maru rụng răng\", \"Maru một ngày xui xẻo\", \"Maru và hạt dưa”) và Maru Giúp Mẹ (gồm \" Maru đi vệ sinh\", \"Maru quét nhà\", \"Maru tập rửa bát\"), chú trọng về việc dạy trẻ các kỹ năng tư duy khi quan sát những sự việc xảy ra xung quanh hằng ngày.\n" +
//                "\n" +
//                "Maru cũng như bao bạn nhỏ khác, có những thắc mắc về chính bản thân mình và về thế giới xung quanh mà bạn ấy hỏi khắp mọi nơi, khám phá đủ mọi thứ để tìm câu trả lời. Tại sao bạn răng lại bỏ mình mà đi, Maru thắc mắc và buồn biết bao lâu, nhưng nhờ những người bạn thân thiết, bạn ấy đã nhận ra rằng rụng răng không phải là một điều đáng buồn, mà đó là dấu hiệu cho thấy rằng Maru đã sắp thành người lớn rồi. Câu chuyện rụng răng của Maru cũng là một lần “tập duyệt” cho lần đầu rụng răng của các bạn nhỏ sau này. Tương tự như vậy, câu chuyện “Maru và hạt dưa” hay “Maru một ngày xui xẻo” rất gần gũi và quen thuộc, dạy cho trẻ cách đối mặt với những sự việc bất ngờ đến trong cuộc sống.\n" +
//                "\n" +
//                "Bên cạnh việc tìm hiểu nhiều điều thú vị xoay quanh cuộc sống hằng ngày, bạn nhỏ Maru còn rất ngoan, gương mẫu khi biết cùng mẹ thực hiện công việc nhà. \n" +
//                "Công ty phát hành: Wabooks\n" +
//                "Nhà xuất bản: NXB Lao động - Xã hội\n" +
//                "Tác giả: Suzuki Mio\n" +
//                "Loại bìa:\tBìa mềm\n" +
//                "Số trang:\t24 trang","Sách Ehon - Combo 6 cuốn Maru - Ehon Nhật Bản dành cho bé từ 2 - 8 tuổi","BK01",SIZE5,40));
//
//        TAOproducts.add(new product_item("sach","35",97000,149000,"https://cf.shopee.vn/file/eb5d26243d7ac52ea8e5591df7e08ae0",
//        "https://cf.shopee.vn/file/71393cb33393281ac241724ca40f47e7","https://cf.shopee.vn/file/f7736b4dba19d01cd0fcbc7f1f08a4c2",
//        "https://cf.shopee.vn/file/2f5123956075c882d946cb325b1cbadf",COLOR,"Sách - Dòng Tiền Gắn Liền Lợi Nhuận\n" +
//                "Hóa giải nghịch lý kinh doanh có lãi mà không thấy tiền\n" +
//                "\n" +
//                "Rất nhiều bạn trẻ và cả những bạn không-còn-trẻ, mới khởi nghiệp hoặc đã khởi nghiệp được coi là thành công (vì sau 3 năm mà doanh nghiệp vẫn tồn tại) luôn thắc mắc một điều là tại sao doanh nghiệp vẫn tăng trưởng, có doanh thu, nhưng cứ lỗ? Mặc dù kế hoạch kinh doanh thì rất khả thi nhưng thậm chí doanh thu càng tăng, lỗ càng nhiều.\n" +
//                "\n" +
//                "Đó là vì các bạn quên đi một tham số quan trọng: là gía trị thời gian của tiền tệ, bởi vậy, các bạn chưa kiểm soát được DÒNG TIỀN!\n" +
//                "Có thể nói dòng tiền là nguồn lực cốt lõi của doanh nghiệp, nếu dòng tiền được quản trị đúng đắn, hiệu quả, mang lại hiệu suất về kinh tế, doanh nghiệp có thể tăng trưởng và thịnh vượng. Ngược lại, nếu quản lý dòng tiền kém hiệu quả, doanh nghiệp sẽ gặp rất nhiều rủi ro.\n" +
//                "\n" +
//                "Tác giả của hai cuốn sách kinh doanh kinh điển Kế hoạch bí ngô và Khởi nghiệp từ khốn khó cung cấp một giải pháp quản lý dòng tiền đơn giản, phản trực quan nhằm giúp các doanh nghiệp nhỏ thoát khỏi vòng xoáy phá sản và đạt được lợi nhuận ngay lập tức.\n" +
//                "Sử dụng phương pháp Dòng tiền gắn liền lợi nhuận (Profit First) của Mike Michalowicz, bạn sẽ biết:\n" +
//                "\n" +
//                "- Bốn nguyên tắc để đơn giản hóa quy trình kế toán;\n" +
//                "- Một doanh nghiệp nhỏ có lợi nhuận giá trị hơn nhiều so với một doanh nghiệp lớn cùng ngành;\n" +
//                "- Các doanh nghiệp đạt được lợi nhuận sớm và biết quản trị dòng tiền đúng cách có thể đạt được sự tăng trưởng dài hạn.\n" +
//                "Với hàng tá các nghiên cứu, lời khuyên cùng khiếu hài hước đặc trưng, Mike Michalowicz đã giúp các doanh nhân “lật ngược thế cờ” và đạt được doanh thu mà họ luôn mơ ước.\n" +
//                "\n" +
//                "Mã hàng\t8935251407914\n" +
//                "Tên Nhà Cung Cấp\tAlpha Books\n" +
//                "Tác giả\tMike Michalowicz\n" +
//                "Người Dịch\tNhóm dịch Alpha Books\n" +
//                "NXB\tNXB Công Thương\n" +
//                "Năm XB\t2020\n" +
//                "Trọng lượng (gr)\t300\n" +
//                "Kích thước\t16 x 24cm\n" +
//                "Số trang\t284\n" +
//                "Hình thức\tBìa Mềm","Sách - Dòng Tiền Gắn Liền Lợi Nhuận","BK02",SIZE5,30));
//
//        TAOproducts.add(new product_item("sach","38",65000,105000,"https://cf.shopee.vn/file/4d5f7def34e73711f848549fd88978e4",
//        "https://cf.shopee.vn/file/895107e2d2ec60901d1a58207741cdd0","https://cf.shopee.vn/file/608fc8cf598e9cff30cccd151575de64",
//        "https://cf.shopee.vn/file/fd4f77e3c44110167fcdfc6dc2a7c6bc",COLOR,"Tên sách: Ngày hôm nay của cậu thế nào \n" +
//                "Tác giả: A Crazy Mind Team\n" +
//                "Nhà xuất bản: NXB Hà Nội\n" +
//                "Khổ sách: 12x20 cm, Bìa mềm\n" +
//                "Giá bìa: 105.000\n" +
//                "Số trang: 188\n" +
//                "Tặng 2 bookmark xinh xắn\n" +
//                "Thể loại: Tâm lý, truyền cảm hứng. \n" +
//                "\n" +
//                "\"Ngày hôm nay của cậu thế nào?” là một cuốn sách có ba phần với ba bức tranh miêu tả ba trạng thái tâm lý khác nhau. Nếu bức tranh đầu tiên khắc họa những bức tường một người phải xây lên để có thể trú ngụ an toàn trong đó thì bức tranh thứ hai và thứ ba lần lượt là hình ảnh lắng mình với những cảm xúc nội tâm - gỡ bỏ từng viên gạch xuống để một lần nữa, người đó bước ra cuộc sống với một tâm thế rộng mở và vững vàng hơn.\n" +
//                "\n" +
//                "\"Ngày hôm nay của cậu thế nào?\" - một cuốn sách được kết hợp bởi những bạn trẻ trong nhóm A Crazy Mind với mong muốn truyền tải những thông điệp, lẽ sống một cách nhẹ nhàng và dịu êm thông qua những trải nghiệm của tuổi trẻ đầy nhiệt huyết, đam mê và khát vọng. Và chính những trải nghiệm này sẽ giúp \"chữa lành\" những tổn thương tâm lý của người đọc, để mọi người cùng tự tin, mạnh mẽ, tràn đầy niềm hứng khởi hơn trước cuộc sống luôn không ngừng biến động.\n" +
//                "NAME Ngày hôm nay của cậu thế nào - A Crazy Mind Team","Ngày hôm nay của cậu thế nào - A Crazy Mind Team\n" +
//                "SANPHAM_ID BK03","BK03",SIZE5,80));
//
//        TAOproducts.add(new product_item("sach","38",54000,86000,"https://cf.shopee.vn/file/775bf6a8fdbe5f79ad8aa5d7626cd869","https://cf.shopee.vn/file/b5bed61f6abdd57280fea3e658134b72",
//        "https://cf.shopee.vn/file/05a82e750bfa1439f863c01a2aeb210e","https://cf.shopee.vn/file/f6bea1f2e1aa442a0afd43f20f3f5e4c",COLOR,"Công ty phát hành\tFirst News - Trí Việt\n" +
//                "Tác giả\tDale Carnegie\n" +
//                "Ngày xuất bản\t03-2020\n" +
//                "Kích thước\t14.5 x 20.5 cm\n" +
//                "Nhà xuất bản\tNhà Xuất Bản Tổng hợp TP.HCM\n" +
//                "Loại bìa\tBìa mềm\n" +
//                "Số trang\t320\n" +
//                "SKU\t2436661537384\n" +
//                "Tại sao Đắc Nhân Tâm luôn trong Top sách bán chạy nhất thế giới?\n" +
//                "Bởi vì đó là cuốn sách mọi người đều nên đọc.\n" +
//                "Hiện nay có một sự hiểu nhầm đã xảy ra. Tuy Đắc Nhân Tâm là tựa sách hầu hết mọi người đều biết đến, vì những danh tiếng và mức độ phổ biến, nhưng một số người lại “ngại” đọc. Lý do vì họ tưởng đây là cuốn sách “dạy làm người” nên có tâm lý e ngại. Có lẽ là do khi giới thiệu về cuốn sách, người ta luôn gắn với miêu tả đây là “nghệ thuật đối nhân xử thế”, “nghệ thuật thu phục lòng người”… Những cụm từ này đã không còn hợp với hiện nay nữa, gây cảm giác xa lạ và không thực tế.\n" +
//                "Nhưng đâu phải thế, Đắc Nhân Tâm là cuốn sách không hề lỗi thời! \n" +
//                "Những vấn đề được chỉ ra trong đó đều là căn bản ứng xử giữa người với người. Nếu diễn giải theo từ ngữ bây giờ, có thể gọi đây là “giáo trình” giúp hiểu mình – hiểu người để thành công trong giao tiếp. Có ai sống mà không cần giao tiếp? Có bao nhiêu người ngày ngày mệt mỏi, khổ sở vì gặp phải các vấn đề trong giao tiếp? Vì thế, Đắc Nhân Tâm chính là cuốn sách dành cho mọi người. Con cái nên đọc – cha mẹ càng nên đọc, nhân viên nên đọc – sếp càng nên đọc, người quen nhau nên đọc – người lạ nhau càng nên đọc…. Và đó mới chính thật là lý do Đắc Nhân Tâm luôn lọt vào Top sách bán chạy nhất thế giới, dù đã ra đời cách đây gần 80 năm.\n" +
//                "Có lẽ sẽ có người vừa đọc vừa nghĩ, mấy điều trong sách này đơn giản mà, ai chẳng biết? Đúng thế, vì toàn bộ đều là những quy tắc, những cách cư xử căn bản giữa chúng ta với nhau thôi. Kiểu như “Không chỉ trích, oán trách hay than phiền”, “Thành thật khen ngợi và biết ơn người khác”, “Thật lòng làm cho người khác thấy rằng họ quan trọng”… Những điều này đúng thật là ai cũng biết, nhưng bạn có chắc bạn nhớ được và làm được những điều đơn giản đó? Vì vậy, cuốn sách mới ra đời, để giúp bạn thực hành.\n" +
//                "Nhưng có lẽ đa số người đọc sẽ thành thật gật gù đồng ý với từng trang sách. Ồ nếu như bình tâm suy xét lại mọi việc, thì trong bất cứ trường hợp nào mình cũng có thể cư xử đúng mực, không làm người khác tổn thương, giúp bầu không khí luôn thoải mái, và thế là cả hai bên đều vui vẻ, công việc cũng vì thế mà suôn sẻ, thành công hơn. Vậy chứ mà cũng không dễ, bởi “cái tôi” của mỗi người thường chiến thắng tâm trí trong đa số trường hợp. Để thỏa mãn nó, chúng ta hay mắc sai lầm không đáng. Đó cũng chính là lý do Đắc Nhân Tâm có mặt, để nhắc nhở và từng chút giúp ta uốn nắn chính “cái tôi” của mình.\n" +
//                "Đắc Nhân Tâm - Dale Carnegie\n" +
//                "Cuốn Sách Hay Nhất Của Mọi Thời Đại Đưa Bạn Đến Thành Công\n" +
//                "\n" +
//                "Đắc nhân tâm – How to win friends and Influence People của Dale Carnegie là quyển sách nổi tiếng nhất, bán chạy nhất và có tầm ảnh hưởng nhất của mọi thời đại. Tác phẩm đã được chuyển ngữ sang hầu hết các thứ tiếng trên thế giới và có mặt ở hàng trăm quốc gia. Đây là quyển sách duy nhất về thể loại self-help liên tục đứng đầu danh mục sách bán chạy nhất (best-selling Books) do báo The New York Times bình chọn suốt 10 năm liền. Riêng bản tiếng Anh của sách đã bán được hơn 15 triệu bản trên thế giới. Tác phẩm có sức lan toả vô cùng rộng lớn – dù bạn đi đến bất cứ đâu, bất kỳ quốc gia nào cũng đều có thể nhìn thấy. Tác phẩm được đánh giá là quyển sách đầu tiên và hay nhất, có ảnh hưởng làm thay đổi cuộc đời của hàng triệu người trên thế giới.\n" +
//                "\n" +
//                "Không còn nữa khái niệm giới hạn Đắc Nhân Tâm là nghệ thuật thu phục lòng người, là làm cho tất cả mọi người yêu mến mình. Đắc nhân tâm và cái Tài trong mỗi người chúng ta. Đắc Nhân Tâm trong ý nghĩa đó cần được thụ đắc bằng sự hiểu rõ bản thân, thành thật với chính mình, hiểu biết và quan tâm đến những người xung quanh để nhìn ra và khơi gợi những tiềm năng ẩn khuất nơi họ, giúp họ phát triển lên một tầm cao mới. Đây chính là nghệ thuật cao nhất về con người và chính là ý nghĩa sâu sắc nhất đúc kết từ những nguyên tắc vàng của Dale Carnegie.\n" +
//                "\n" +
//                "Quyển sách Đắc nhân tâm “Đầu tiên và hay nhất mọi thời đại về nghệ thuật giao tiếp và ứng xử”, quyển sách đã từng mang đến thành công và hạnh phúc cho hàng triệu người trên khắp thế giới","Sách - Đắc Nhân Tâm( khổ lớn)"
//,"BK04",SIZE5,37));
//        stt = 13;
//        stt2 =8;
//        for(product_item Obj : TAOproducts){
//            if(stt <=9) {
//                STT = "SP0"+stt;
//            }
//            else{
//                STT = "SP"+stt;
//            }
//            if(stt2 <=9) {
//                STT2 = "SP0"+stt2;
//            }
//            else{
//                STT2 = "SP"+stt2;
//            }
//            int myNum = 0;
//
//            try {
//                myNum = Integer.parseInt(Obj.getDiscount());
//            } catch(NumberFormatException nfe) {
//                System.out.println("Could not parse " + nfe);
//            }
//
//
//            data.put("CATEGORY",Obj.getCategory());
//            data.put("DISCOUNT",myNum);
//            data.put("GIA",Obj.getPrice());
//            data.put("GIACU",Obj.getGiaGoc());
//            data.put("IMAGE",Obj.getImageUrl());
//            data.put("NAME",Obj.getName());
//            data.put("SANPHAM_ID",Obj.getSANPHAM_ID());
//            data.put("SOLUONG",Obj.getSoluong());
//
//            db.collection("SAN_PHAM").document(STT)
//                    .set(data);
//
//
//            data2.put("IMAGE1",Obj.getImageUrl());
//            data2.put("IMAGE2",Obj.getIMAGE2());
//            data2.put("IMAGE3",Obj.getIMAGE3());
//            data2.put("MAUSAC",Obj.getCOLOR());
//            data2.put("MOTA",Obj.getMoTa());
//            data2.put("NAME",Obj.getName());
//            data2.put("SANPHAM_ID",Obj.getSANPHAM_ID());
//            data2.put("SIZE",Obj.getSIZE());
//
//            db.collection("CHI_TIET_SAN_PHAM").document(STT2)
//                    .set(data2);
//            stt++;
//            stt2++;
//        }



//        TAOproducts.add( new product_item(category,image,name,discount,soluong,giacu,gia));


//        BroadcastReceiver receiver=new BroadcastReceiver(){
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//            }
//        };
//
//        IntentFilter filter=new IntentFilter(Intent.EXTRA_STREAM);
//        registerReceiver(receiver,filter);

//        mCallback = (DataCommunication) MainActivity.this;
//        if(PrevActive!= null){
//        Log.e("PrevActive : " , PrevActive.toString());
//        }
//        extras = getIntent().getExtras();
//
//        if (extras != null) {
//            order = extras.getString("Order");
//            active = extras.getString("prevActive");
//            // and get whatever type user account id is
//        }


        i = getIntent();
        extras = getIntent().getExtras();


        if ( i!= null &&extras != null) {
            order = extras.getString("Order");
            PasssPassword = i.getStringExtra("password");
            Log.e("MainActive : ", PasssPassword.toString());
            getaddtoBasket = i.getBooleanExtra("addtoBask",false);

            userID = i.getStringExtra("userID");
            getaddDonHang = i.getBooleanExtra("addtoDonhang",false);

            ActiPrev = i.getStringExtra("PrevActive");
            if(ActiPrev !=null) {
                Log.e("PrevActive : ", ActiPrev.toString());
            }

            // and get whatever type user account id is
        }



//        if(savedInstanceState == null  ) {
//            Fragment newFragment = new BasketFragment();
//            this.getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.Main_root, new BasketFragment())
//                    .commit();
//        }

        Locale locales = new Locale("vi");
        Locale.setDefault(locales);
        Configuration config = new Configuration();
        config.locale = locales;
        this.getApplicationContext().getResources().updateConfiguration(config, null);

//        mTabLayout =findViewById(R.id.TopTabmain);
        viewMain = findViewById(R.id.ViewPagerMain);
        bottomnavigation = findViewById(R.id.BotomNavMain);




        MainViewPagerAdpater view_pager_adpater = new MainViewPagerAdpater(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,getIntent());

        viewMain.setAdapter(view_pager_adpater);
//        mTabLayout.setupWithViewPager(viewMain);
        if(ActiPrev !=null) {
            if (i != null && extras != null && ActiPrev.toString().equalsIgnoreCase("DetailProduct")) {
                viewMain.setCurrentItem(1);
                bottomnavigation.getMenu().findItem(R.id.Menu_Basket).setChecked(true);
            }
            else if (i != null  && getaddDonHang) {
                viewMain.setCurrentItem(2);
                bottomnavigation.getMenu().findItem(R.id.Menu_Profile).setChecked(true);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                     getaddDonHang = false;
                     i.putExtra("addtoDonhang",getaddDonHang);
                    }
                }, 2000);

            }
            else if (i != null && extras != null && ActiPrev.toString().equalsIgnoreCase("changeInfo")) {
                viewMain.setCurrentItem(2);
                bottomnavigation.getMenu().findItem(R.id.Menu_Profile).setChecked(true);
            }
            else if (i != null && extras != null && ActiPrev.toString().equalsIgnoreCase("changePassword")) {
                viewMain.setCurrentItem(2);
                bottomnavigation.getMenu().findItem(R.id.Menu_Profile).setChecked(true);
            }
        }
//        if(getaddtoBasket) {
//            MainViewPagerAdpater view_pager_adpater1 = new MainViewPagerAdpater(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,getIntent());
//            viewMain.setAdapter(view_pager_adpater1);
//            viewMain.setCurrentItem(1);
//            getaddtoBasket = false;
//            getIntent().putExtra("addtoBask",getaddtoBasket);
//        }



        viewMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: bottomnavigation.getMenu().findItem(R.id.Menu_Shop).setChecked(true); break;
                    case 1: {bottomnavigation.getMenu().findItem(R.id.Menu_Basket).setChecked(true);
//                        Toast.makeText(mContext,Boolean.toString(getaddtoBasket),Toast.LENGTH_SHORT).show();
                        if(getaddtoBasket) {
//                            MainViewPagerAdpater view_pager_adpater1 = new MainViewPagerAdpater(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,getIntent());
//                            viewMain.setAdapter(view_pager_adpater1);
//                            Log.e("MainActive:",Boolean.toString(getaddtoBasket));
//                            Toast.makeText(MainActivity.this,Boolean.toString(getaddtoBasket),Toast.LENGTH_SHORT).show();
//                            getaddtoBasket = false;
//                            getIntent().putExtra("addtoBask",getaddtoBasket);
////                            finish();
//                            overridePendingTransition(0, 0);
//                            getIntent().putExtra("addtoBask",true);
//                            startActivity(getIntent());
//                            overridePendingTransition(0, 0);
                        }
                        break;}
                    case 2: bottomnavigation.getMenu().findItem(R.id.Menu_Profile).setChecked(true); break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomnavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Menu_Shop: viewMain.setCurrentItem(0); break;
                    case R.id.Menu_Basket: viewMain.setCurrentItem(1); break;
                    case R.id.Menu_Profile: viewMain.setCurrentItem(2); break;
                }
                return true;
            }
        });

    }
    //        String[] Fragement =  {"QuanaoFragment","DientuFragment","VanphongFragment","SachFragment" };
//        String[] Title =  {App.getAppResources().getString(R.string.clothes),App.getAppResources().getString(R.string.Electron),App.getAppResources().getString(R.string.Office),App.getAppResources().getString(R.string.books) };
//        List<String> FragList = Arrays.asList(Fragement);
//        List<String> TiList = Arrays.asList(Title);

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        // Collect data from the intent and use it
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            getaddtoBasket = data.getBooleanExtra("addtoBask", false);
        }
    }

    @Override
    protected void onResume() {
//        if(getaddtoBasket()){
//            setaddtoBasket(false);
//            this.recreate();
//        }
//        if(getaddtoBasket)
//        {
//            this.recreate();
//            i.putExtra("addtoBask", false);
//        }
        super.onResume();

    }


    @Override
    public void supportNavigateUpTo(@NonNull Intent upIntent) {
//        if(getaddtoBasket)
//        {
//            this.recreate();
//            i.putExtra("addtoBask", false);
//        }
        super.supportNavigateUpTo(upIntent);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_quanao);
      if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
          super.onBackPressed();

      }
        super.onBackPressed();
    }

    @Override
    public boolean getaddtoBasket() {
        return getaddtoBasket;
    }

    @Override
    public void setaddtoBasket(boolean getaddtoBasket) {

    }

    @Override
    public String getPassName() {
        return passName;
    }

    @Override
    public void setPassName(String passName) {
        this.passName = passName;
    }

    @Override
    public String getPassCategory() {
        return passCategory;
    }

    @Override
    public void setPassCategory(String passCategory) {
        this.passCategory = passCategory;
    }

    @Override
    public int getPassquantity() {
        return passquantity;
    }

    @Override
    public void setPassquantity(int passquantity) {
        this.passquantity = passquantity;
    }

    @Override
    public String getPasscolor() {
        return passcolor;
    }

    @Override
    public void setPasscolor(String passcolor) {
        this.passcolor = passcolor;
    }

    @Override
    public String getPasssize() {
        return passsize;
    }

    @Override
    public void setPasssize(String passsize) {
        this.passsize = passsize;
    }

    @Override
    public int getPassPrice() {
        return passPrice;
    }

    @Override
    public void setPassPrice(int passPrice) {
        this.passPrice = passPrice;
    }

    @Override
    public int getPassSoluong() {
        return passSoluong;
    }
    
    @Override
    public void setPassSoluong(int passSoluong) {
        this.passSoluong = passSoluong;
    }

    @Override
    public void setPrevActive(String PrevActive) {
        this.PrevActive = PrevActive;
    }

    @Override
    public String getPrevActive() {
        return PrevActive;
    }



}