package com.clay.covid_19helper.util

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

object Constants {

    const val BASE_URL = "https://api.covid19india.org"

    const val WORLD_API_BASE_URL = "https://covid19.mathdro.id/api/"

    const val WORLD_COVID_DATA_URL = "https://www.trackcorona.live/"

    const val MAP_CAMERA_ZOOM = 5f

    val countries_bb = object : HashMap<String, LatLngBounds>() {
        init {
            put(
                "AW",
                LatLngBounds(LatLng(12.1702998, -70.2809842), LatLng(12.8102998, -69.6409842))
            )
            put(
                "AF",
                LatLngBounds(LatLng(29.3772, 60.5176034), LatLng(38.4910682, 74.889862))
            )
            put(
                "AO",
                LatLngBounds(LatLng(-18.038945, 11.4609793), LatLng(-4.3880634, 24.0878856))
            )
            put(
                "AI",
                LatLngBounds(LatLng(18.0615454, -63.6391992), LatLng(18.7951194, -62.7125449))
            )
            put(
                "AX",
                LatLngBounds(LatLng(59.4541578, 19.0832098), LatLng(60.87665, 21.3456556))
            )
            put(
                "AL",
                LatLngBounds(LatLng(39.6448625, 19.1246095), LatLng(42.6610848, 21.0574335))
            )
            put(
                "AD",
                LatLngBounds(LatLng(42.4288238, 1.4135781), LatLng(42.6559357, 1.7863837))
            )
            put(
                "AE",
                LatLngBounds(LatLng(22.6444, 51.498), LatLng(26.2822, 56.3834))
            )
            put(
                "AR",
                LatLngBounds(LatLng(-55.1850761, -73.5600329), LatLng(-21.781168, -53.6374515))
            )
            put(
                "AM",
                LatLngBounds(LatLng(38.8404775, 43.4471395), LatLng(41.300712, 46.6333087))
            )
            put(
                "AS",
                LatLngBounds(LatLng(-14.7608358, -171.2951296), LatLng(-10.8449746, -167.9322899))
            )
            put(
                "AQ",
                LatLngBounds(LatLng(-85.0511287, -180.0), LatLng(-60.0, 180.0))
            )
            put(
                "TF",
                LatLngBounds(LatLng(-50.2187169, 39.4138676), LatLng(-11.3139928, 77.8494974))
            )
            put(
                "AG",
                LatLngBounds(LatLng(16.7573901, -62.5536517), LatLng(17.929, -61.447857))
            )
            put(
                "AU",
                LatLngBounds(LatLng(-55.3228175, 72.2460938), LatLng(-9.0882278, 168.2249543))
            )
            put(
                "AT",
                LatLngBounds(LatLng(46.3722761, 9.5307487), LatLng(49.0205305, 17.160776))
            )
            put(
                "AZ",
                LatLngBounds(LatLng(38.3929551, 44.7633701), LatLng(41.9502947, 51.0090302))
            )
            put(
                "BI",
                LatLngBounds(LatLng(-4.4693155, 29.0007401), LatLng(-2.3096796, 30.8498462))
            )
            put(
                "BE",
                LatLngBounds(LatLng(49.4969821, 2.3889137), LatLng(51.5516667, 6.408097))
            )
            put(
                "BJ",
                LatLngBounds(LatLng(6.0398696, 0.776667), LatLng(12.4092447, 3.843343))
            )
            put(
                "BF",
                LatLngBounds(LatLng(9.4104718, -5.5132416), LatLng(15.084, 2.4089717))
            )
            put(
                "BD",
                LatLngBounds(LatLng(20.3756582, 88.0075306), LatLng(26.6382534, 92.6804979))
            )
            put(
                "BG",
                LatLngBounds(LatLng(41.2353929, 22.3571459), LatLng(44.2167064, 28.8875409))
            )
            put(
                "BH",
                LatLngBounds(LatLng(25.535, 50.2697989), LatLng(26.6872444, 50.9233693))
            )
            put(
                "BS",
                LatLngBounds(LatLng(20.7059846, -80.7001941), LatLng(27.4734551, -72.4477521))
            )
            put(
                "BA",
                LatLngBounds(LatLng(42.5553114, 15.7287433), LatLng(45.2764135, 19.6237311))
            )
            put(
                "BL",
                LatLngBounds(LatLng(17.670931, -63.06639), LatLng(18.1375569, -62.5844019))
            )
            put(
                "BY",
                LatLngBounds(LatLng(51.2575982, 23.1783344), LatLng(56.17218, 32.7627809))
            )
            put(
                "BZ",
                LatLngBounds(LatLng(15.8857286, -89.2262083), LatLng(18.496001, -87.3098494))
            )
            put(
                "BM",
                LatLngBounds(LatLng(32.0469651, -65.1232222), LatLng(32.5913693, -64.4109842))
            )
            put(
                "BO",
                LatLngBounds(LatLng(-22.8982742, -69.6450073), LatLng(-9.6689438, -57.453))
            )
            put(
                "BR",
                LatLngBounds(LatLng(-33.8689056, -73.9830625), LatLng(5.2842873, -28.6341164))
            )
            put(
                "BB",
                LatLngBounds(LatLng(12.845, -59.8562115), LatLng(13.535, -59.2147175))
            )
            put(
                "BN",
                LatLngBounds(LatLng(4.002508, 114.0758734), LatLng(5.1011857, 115.3635623))
            )
            put(
                "BT",
                LatLngBounds(LatLng(26.702016, 88.7464724), LatLng(28.246987, 92.1252321))
            )
            put(
                "BV",
                LatLngBounds(LatLng(-54.654, 2.9345531), LatLng(-54.187, 3.7791099))
            )
            put(
                "BW",
                LatLngBounds(LatLng(-26.9059669, 19.9986474), LatLng(-17.778137, 29.375304))
            )
            put(
                "CF",
                LatLngBounds(LatLng(2.2156553, 14.4155426), LatLng(11.001389, 27.4540764))
            )
            put(
                "CA",
                LatLngBounds(LatLng(41.6765556, -141.00275), LatLng(83.3362128, -52.3231981))
            )
            put(
                "CC",
                LatLngBounds(LatLng(-12.4055983, 96.612524), LatLng(-11.6213132, 97.1357343))
            )
            put(
                "CH",
                LatLngBounds(LatLng(45.817995, 5.9559113), LatLng(47.8084648, 10.4922941))
            )
            put(
                "CL",
                LatLngBounds(LatLng(-56.725, -109.6795789), LatLng(-17.4983998, -66.0753474))
            )
            put(
                "CN",
                LatLngBounds(LatLng(8.8383436, 73.4997347), LatLng(53.5608154, 134.7754563))
            )
            put(
                "CI",
                LatLngBounds(LatLng(4.1621205, -8.601725), LatLng(10.740197, -2.493031))
            )
            put(
                "CM",
                LatLngBounds(LatLng(1.6546659, 8.3822176), LatLng(13.083333, 16.1921476))
            )
            put(
                "CD",
                LatLngBounds(LatLng(-13.459035, 12.039074), LatLng(5.3920026, 31.3056758))
            )
            put(
                "CG",
                LatLngBounds(LatLng(-5.149089, 11.0048205), LatLng(3.713056, 18.643611))
            )
            put(
                "CK",
                LatLngBounds(LatLng(-22.15807, -166.0856468), LatLng(-8.7168792, -157.1089329))
            )
            put(
                "CO",
                LatLngBounds(LatLng(-4.2316872, -82.1243666), LatLng(16.0571269, -66.8511907))
            )
            put(
                "KM",
                LatLngBounds(LatLng(-12.621, 43.025305), LatLng(-11.165, 44.7451922))
            )
            put(
                "CV",
                LatLngBounds(LatLng(14.8031546, -25.3609478), LatLng(17.2053108, -22.6673416))
            )
            put(
                "CR",
                LatLngBounds(LatLng(5.3329698, -87.2722647), LatLng(11.2195684, -82.5060208))
            )
            put(
                "CU",
                LatLngBounds(LatLng(19.6275294, -85.1679702), LatLng(23.4816972, -73.9190004))
            )
            put(
                "CX",
                LatLngBounds(LatLng(-10.5698515, 105.5336422), LatLng(-10.4123553, 105.7130159))
            )
            put(
                "KY",
                LatLngBounds(LatLng(19.0620619, -81.6313748), LatLng(19.9573759, -79.5110954))
            )
            put(
                "CY",
                LatLngBounds(LatLng(34.4383706, 32.0227581), LatLng(35.913252, 34.8553182))
            )
            put(
                "CZ",
                LatLngBounds(LatLng(48.5518083, 12.0905901), LatLng(51.0557036, 18.859216))
            )
            put(
                "DE",
                LatLngBounds(LatLng(47.2701114, 5.8663153), LatLng(55.099161, 15.0419319))
            )
            put(
                "DJ",
                LatLngBounds(LatLng(10.9149547, 41.7713139), LatLng(12.7923081, 43.6579046))
            )
            put(
                "DM",
                LatLngBounds(LatLng(15.0074207, -61.6869184), LatLng(15.7872222, -61.0329895))
            )
            put(
                "DK",
                LatLngBounds(LatLng(54.4516667, 7.7153255), LatLng(57.9524297, 15.5530641))
            )
            put(
                "DO",
                LatLngBounds(LatLng(17.2701708, -72.0574706), LatLng(21.303433, -68.1101463))
            )
            put(
                "DZ",
                LatLngBounds(LatLng(18.968147, -8.668908), LatLng(37.2962055, 11.997337))
            )
            put(
                "EC",
                LatLngBounds(LatLng(-5.0159314, -92.2072392), LatLng(1.8835964, -75.192504))
            )
            put(
                "EG",
                LatLngBounds(LatLng(22.0, 24.6499112), LatLng(31.8330854, 37.1153517))
            )
            put(
                "ER",
                LatLngBounds(LatLng(12.3548219, 36.4333653), LatLng(18.0709917, 43.3001714))
            )
            put(
                "EH",
                LatLngBounds(LatLng(20.556883, -17.3494721), LatLng(27.6666834, -8.666389))
            )
            put(
                "ES",
                LatLngBounds(LatLng(27.4335426, -18.3936845), LatLng(43.9933088, 4.5918885))
            )
            put(
                "EE",
                LatLngBounds(LatLng(57.5092997, 21.3826069), LatLng(59.9383754, 28.2100175))
            )
            put(
                "ET",
                LatLngBounds(LatLng(3.397448, 32.9975838), LatLng(14.8940537, 47.9823797))
            )
            put(
                "FI",
                LatLngBounds(LatLng(59.4541578, 19.0832098), LatLng(70.0922939, 31.5867071))
            )
            put(
                "FJ",
                LatLngBounds(LatLng(-21.9434274, 172.0), LatLng(-12.2613866, -178.5))
            )
            put(
                "FK",
                LatLngBounds(LatLng(-53.1186766, -61.7726772), LatLng(-50.7973007, -57.3662367))
            )
            put(
                "FR",
                LatLngBounds(LatLng(41.2632185, -5.4534286), LatLng(51.268318, 9.8678344))
            )
            put(
                "FO",
                LatLngBounds(LatLng(61.3915553, -7.6882939), LatLng(62.3942991, -6.2565525))
            )
            put(
                "FM",
                LatLngBounds(LatLng(0.827, 137.2234512), LatLng(10.291, 163.2364054))
            )
            put(
                "GA",
                LatLngBounds(LatLng(-4.1012261, 8.5002246), LatLng(2.3182171, 14.539444))
            )
            put(
                "GB",
                LatLngBounds(LatLng(49.674, -14.015517), LatLng(61.061, 2.0919117))
            )
            put(
                "GE",
                LatLngBounds(LatLng(41.0552922, 39.8844803), LatLng(43.5864294, 46.7365373))
            )
            put(
                "GG",
                LatLngBounds(LatLng(49.4155331, -2.6751703), LatLng(49.5090776, -2.501814))
            )
            put(
                "GH",
                LatLngBounds(LatLng(4.5392525, -3.260786), LatLng(11.1748562, 1.2732942))
            )
            put(
                "GI",
                LatLngBounds(LatLng(36.100807, -5.3941295), LatLng(36.180807, -5.3141295))
            )
            put(
                "GN",
                LatLngBounds(LatLng(7.1906045, -15.5680508), LatLng(12.67563, -7.6381993))
            )
            put(
                "GP",
                LatLngBounds(LatLng(15.8320085, -61.809764), LatLng(16.5144664, -61.0003663))
            )
            put(
                "GM",
                LatLngBounds(LatLng(13.061, -17.0288254), LatLng(13.8253137, -13.797778))
            )
            put(
                "GW",
                LatLngBounds(LatLng(10.6514215, -16.894523), LatLng(12.6862384, -13.6348777))
            )
            put(
                "GQ",
                LatLngBounds(LatLng(-1.6732196, 5.4172943), LatLng(3.989, 11.3598628))
            )
            put(
                "GR",
                LatLngBounds(LatLng(34.7006096, 19.2477876), LatLng(41.7488862, 29.7296986))
            )
            put(
                "GD",
                LatLngBounds(LatLng(11.786, -62.0065868), LatLng(12.5966532, -61.1732143))
            )
            put(
                "GL",
                LatLngBounds(LatLng(59.515387, -74.1250416), LatLng(83.875172, -10.0288759))
            )
            put(
                "GT",
                LatLngBounds(LatLng(13.6345804, -92.3105242), LatLng(17.8165947, -88.1755849))
            )
            put(
                "GF",
                LatLngBounds(LatLng(2.112222, -54.60278), LatLng(5.7507111, -51.6346139))
            )
            put(
                "GU",
                LatLngBounds(LatLng(13.182335, 144.563426), LatLng(13.706179, 145.009167))
            )
            put(
                "GY",
                LatLngBounds(LatLng(1.1710017, -61.414905), LatLng(8.6038842, -56.4689543))
            )
            put(
                "HK",
                LatLngBounds(LatLng(22.1193278, 114.0028131), LatLng(22.4393278, 114.3228131))
            )
            put(
                "HM",
                LatLngBounds(LatLng(-53.394741, 72.2460938), LatLng(-52.7030677, 74.1988754))
            )
            put(
                "HN",
                LatLngBounds(LatLng(12.9808485, -89.3568207), LatLng(17.619526, -82.1729621))
            )
            put(
                "HR",
                LatLngBounds(LatLng(42.1765993, 13.2104814), LatLng(46.555029, 19.4470842))
            )
            put(
                "HT",
                LatLngBounds(LatLng(17.9099291, -75.2384618), LatLng(20.2181368, -71.6217461))
            )
            put(
                "HU",
                LatLngBounds(LatLng(45.737128, 16.1138867), LatLng(48.585257, 22.8977094))
            )
            put(
                "ID",
                LatLngBounds(LatLng(-11.2085669, 94.7717124), LatLng(6.2744496, 141.0194444))
            )
            put(
                "IM",
                LatLngBounds(LatLng(54.0539576, -4.7946845), LatLng(54.4178705, -4.3076853))
            )
            put(
                "IN",
                LatLngBounds(LatLng(6.5546079, 68.1113787), LatLng(35.6745457, 97.395561))
            )
            put(
                "IO",
                LatLngBounds(LatLng(-7.6454079, 71.036504), LatLng(-5.037066, 72.7020157))
            )
            put(
                "IE",
                LatLngBounds(LatLng(51.222, -11.0133788), LatLng(55.636, -5.6582363))
            )
            put(
                "IR",
                LatLngBounds(LatLng(24.8465103, 44.0318908), LatLng(39.7816502, 63.3332704))
            )
            put(
                "IQ",
                LatLngBounds(LatLng(29.0585661, 38.7936719), LatLng(37.380932, 48.8412702))
            )
            put(
                "IS",
                LatLngBounds(LatLng(63.0859177, -25.0135069), LatLng(67.353, -12.8046162))
            )
            put(
                "IL",
                LatLngBounds(LatLng(29.4533796, 34.2674994), LatLng(33.3356317, 35.8950234))
            )
            put(
                "IT",
                LatLngBounds(LatLng(35.2889616, 6.6272658), LatLng(47.0921462, 18.7844746))
            )
            put(
                "JM",
                LatLngBounds(LatLng(16.5899443, -78.5782366), LatLng(18.7256394, -75.7541143))
            )
            put(
                "JE",
                LatLngBounds(LatLng(49.1625179, -2.254512), LatLng(49.2621288, -2.0104193))
            )
            put(
                "JO",
                LatLngBounds(LatLng(29.183401, 34.8844372), LatLng(33.3750617, 39.3012981))
            )
            put(
                "JP",
                LatLngBounds(LatLng(20.2145811, 122.7141754), LatLng(45.7112046, 154.205541))
            )
            put(
                "KZ",
                LatLngBounds(LatLng(40.5686476, 46.4932179), LatLng(55.4421701, 87.3156316))
            )
            put(
                "KE",
                LatLngBounds(LatLng(-4.8995204, 33.9098987), LatLng(4.62, 41.899578))
            )
            put(
                "KG",
                LatLngBounds(LatLng(39.1728437, 69.2649523), LatLng(43.2667971, 80.2295793))
            )
            put(
                "KH",
                LatLngBounds(LatLng(9.4752639, 102.3338282), LatLng(14.6904224, 107.6276788))
            )
            put(
                "KI",
                LatLngBounds(LatLng(-7.0516717, -179.1645388), LatLng(7.9483283, -164.1645388))
            )
            put(
                "KN",
                LatLngBounds(LatLng(16.895, -63.051129), LatLng(17.6158146, -62.3303519))
            )
            put(
                "KR",
                LatLngBounds(LatLng(32.9104556, 124.354847), LatLng(38.623477, 132.1467806))
            )
            put(
                "KW",
                LatLngBounds(LatLng(28.5243622, 46.5526837), LatLng(30.1038082, 49.0046809))
            )
            put(
                "LA",
                LatLngBounds(LatLng(13.9096752, 100.0843247), LatLng(22.5086717, 107.6349989))
            )
            put(
                "LB",
                LatLngBounds(LatLng(33.0479858, 34.8825667), LatLng(34.6923543, 36.625))
            )
            put(
                "LR",
                LatLngBounds(LatLng(4.1555907, -11.6080764), LatLng(8.5519861, -7.367323))
            )
            put(
                "LY",
                LatLngBounds(LatLng(19.5008138, 9.391081), LatLng(33.3545898, 25.3770629))
            )
            put(
                "LC",
                LatLngBounds(LatLng(13.508, -61.2853867), LatLng(14.2725, -60.6669363))
            )
            put(
                "LI",
                LatLngBounds(LatLng(47.0484291, 9.4716736), LatLng(47.270581, 9.6357143))
            )
            put(
                "LK",
                LatLngBounds(LatLng(5.719, 79.3959205), LatLng(10.035, 82.0810141))
            )
            put(
                "LS",
                LatLngBounds(LatLng(-30.6772773, 27.0114632), LatLng(-28.570615, 29.4557099))
            )
            put(
                "LT",
                LatLngBounds(LatLng(53.8967893, 20.653783), LatLng(56.4504213, 26.8355198))
            )
            put(
                "LU",
                LatLngBounds(LatLng(49.4969821, 4.9684415), LatLng(50.430377, 6.0344254))
            )
            put(
                "LV",
                LatLngBounds(LatLng(55.6746505, 20.6715407), LatLng(58.0855688, 28.2414904))
            )
            put(
                "MO",
                LatLngBounds(LatLng(22.0766667, 113.5281666), LatLng(22.2170361, 113.6301389))
            )
            put(
                "MF",
                LatLngBounds(LatLng(17.8963535, -63.3605643), LatLng(18.1902778, -62.7644063))
            )
            put(
                "MA",
                LatLngBounds(LatLng(21.3365321, -17.2551456), LatLng(36.0505269, -0.998429))
            )
            put(
                "MC",
                LatLngBounds(LatLng(43.7247599, 7.4090279), LatLng(43.7519311, 7.4398704))
            )
            put(
                "MD",
                LatLngBounds(LatLng(45.4674139, 26.6162189), LatLng(48.4918695, 30.1636756))
            )
            put(
                "MG",
                LatLngBounds(LatLng(-25.6071002, 43.2202072), LatLng(-11.9519693, 50.4862553))
            )
            put(
                "MV",
                LatLngBounds(LatLng(-0.9074935, 72.3554187), LatLng(7.3106246, 73.9700962))
            )
            put(
                "MX",
                LatLngBounds(LatLng(14.3886243, -118.59919), LatLng(32.7186553, -86.493266))
            )
            put(
                "MH",
                LatLngBounds(LatLng(-0.5481258, 163.4985095), LatLng(14.4518742, 178.4985095))
            )
            put(
                "MK",
                LatLngBounds(LatLng(40.8536596, 20.4529023), LatLng(42.3735359, 23.034051))
            )
            put(
                "ML",
                LatLngBounds(LatLng(10.147811, -12.2402835), LatLng(25.001084, 4.2673828))
            )
            put(
                "MT",
                LatLngBounds(LatLng(35.6029696, 13.9324226), LatLng(36.2852706, 14.8267966))
            )
            put(
                "MM",
                LatLngBounds(LatLng(9.4399432, 92.1719423), LatLng(28.547835, 101.1700796))
            )
            put(
                "ME",
                LatLngBounds(LatLng(41.7495999, 18.4195781), LatLng(43.5585061, 20.3561641))
            )
            put(
                "MN",
                LatLngBounds(LatLng(41.5800276, 87.73762), LatLng(52.1496, 119.931949))
            )
            put(
                "MP",
                LatLngBounds(LatLng(14.036565, 144.813338), LatLng(20.616556, 146.154418))
            )
            put(
                "MZ",
                LatLngBounds(LatLng(-26.9209427, 30.2138197), LatLng(-10.3252149, 41.0545908))
            )
            put(
                "MR",
                LatLngBounds(LatLng(14.7209909, -17.068081), LatLng(27.314942, -4.8333344))
            )
            put(
                "MS",
                LatLngBounds(LatLng(16.475, -62.450667), LatLng(17.0152978, -61.9353818))
            )
            put(
                "MQ",
                LatLngBounds(LatLng(14.3948596, -61.2290815), LatLng(14.8787029, -60.8095833))
            )
            put(
                "MU",
                LatLngBounds(LatLng(-20.725, 56.3825151), LatLng(-10.138, 63.7151319))
            )
            put(
                "MW",
                LatLngBounds(LatLng(-17.1296031, 32.6703616), LatLng(-9.3683261, 35.9185731))
            )
            put(
                "MY",
                LatLngBounds(LatLng(-5.1076241, 105.3471939), LatLng(9.8923759, 120.3471939))
            )
            put(
                "YT",
                LatLngBounds(LatLng(-13.0210119, 45.0183298), LatLng(-12.6365902, 45.2999917))
            )
            put(
                "NA",
                LatLngBounds(LatLng(-28.96945, 11.5280384), LatLng(-16.9634855, 25.2617671))
            )
            put(
                "NC",
                LatLngBounds(LatLng(-23.2217509, 162.6034343), LatLng(-17.6868616, 167.8109827))
            )
            put(
                "NE",
                LatLngBounds(LatLng(11.693756, 0.1689653), LatLng(23.517178, 15.996667))
            )
            put(
                "NF",
                LatLngBounds(LatLng(-29.333, 167.6873878), LatLng(-28.796, 168.2249543))
            )
            put(
                "NG",
                LatLngBounds(LatLng(4.0690959, 2.676932), LatLng(13.885645, 14.678014))
            )
            put(
                "NI",
                LatLngBounds(LatLng(10.7076565, -87.901532), LatLng(15.0331183, -82.6227023))
            )
            put(
                "NU",
                LatLngBounds(LatLng(-19.3548665, -170.1595029), LatLng(-18.7534559, -169.5647229))
            )
            put(
                "NL",
                LatLngBounds(LatLng(50.7295671, 1.9193492), LatLng(53.7253321, 7.2274985))
            )
            put(
                "NO",
                LatLngBounds(LatLng(57.7590052, 4.0875274), LatLng(71.3848787, 31.7614911))
            )
            put(
                "NP",
                LatLngBounds(LatLng(26.3477581, 80.0586226), LatLng(30.446945, 88.2015257))
            )
            put(
                "NR",
                LatLngBounds(LatLng(-0.5541334, 166.9091794), LatLng(-0.5025906, 166.9589235))
            )
            put(
                "NZ",
                LatLngBounds(LatLng(-52.8213687, -179.059153), LatLng(-29.0303303, 179.3643594))
            )
            put(
                "OM",
                LatLngBounds(LatLng(16.4649608, 52.0), LatLng(26.7026737, 60.054577))
            )
            put(
                "PK",
                LatLngBounds(LatLng(23.5393916, 60.872855), LatLng(37.084107, 77.1203914))
            )
            put(
                "PA",
                LatLngBounds(LatLng(7.0338679, -83.0517245), LatLng(9.8701757, -77.1393779))
            )
            put(
                "PN",
                LatLngBounds(LatLng(-25.1306736, -130.8049862), LatLng(-23.8655769, -124.717534))
            )
            put(
                "PE",
                LatLngBounds(LatLng(-20.1984472, -84.6356535), LatLng(-0.0392818, -68.6519906))
            )
            put(
                "PH",
                LatLngBounds(LatLng(4.2158064, 114.0952145), LatLng(21.3217806, 126.8072562))
            )
            put(
                "PW",
                LatLngBounds(LatLng(2.748, 131.0685462), LatLng(8.222, 134.7714735))
            )
            put(
                "PG",
                LatLngBounds(LatLng(-13.1816069, 136.7489081), LatLng(1.8183931, 151.7489081))
            )
            put(
                "PL",
                LatLngBounds(LatLng(49.0020468, 14.1229707), LatLng(55.0336963, 24.145783))
            )
            put(
                "PR",
                LatLngBounds(LatLng(17.9268695, -67.271492), LatLng(18.5159789, -65.5897525))
            )
            put(
                "KP",
                LatLngBounds(LatLng(37.5867855, 124.0913902), LatLng(43.0089642, 130.924647))
            )
            put(
                "PT",
                LatLngBounds(LatLng(29.8288021, -31.5575303), LatLng(42.1543112, -6.1891593))
            )
            put(
                "PY",
                LatLngBounds(LatLng(-27.6063935, -62.6442036), LatLng(-19.2876472, -54.258))
            )
            put(
                "PS",
                LatLngBounds(LatLng(31.2201289, 34.0689732), LatLng(32.5521479, 35.5739235))
            )
            put(
                "PF",
                LatLngBounds(LatLng(-28.0990232, -154.9360599), LatLng(-7.6592173, -134.244799))
            )
            put(
                "QA",
                LatLngBounds(LatLng(24.4707534, 50.5675), LatLng(26.3830212, 52.638011))
            )
            put(
                "RE",
                LatLngBounds(LatLng(-21.3897308, 55.2164268), LatLng(-20.8717136, 55.8366924))
            )
            put(
                "RO",
                LatLngBounds(LatLng(43.618682, 20.2619773), LatLng(48.2653964, 30.0454257))
            )
            put(
                "RU",
                LatLngBounds(LatLng(41.1850968, 19.6389), LatLng(82.0586232, 180.0))
            )
            put(
                "RW",
                LatLngBounds(LatLng(-2.8389804, 28.8617546), LatLng(-1.0474083, 30.8990738))
            )
            put(
                "SA",
                LatLngBounds(LatLng(16.29, 34.4571718), LatLng(32.1543377, 55.6666851))
            )
            put(
                "SD",
                LatLngBounds(LatLng(8.685278, 21.8145046), LatLng(22.224918, 39.0576252))
            )
            put(
                "SN",
                LatLngBounds(LatLng(12.2372838, -17.7862419), LatLng(16.6919712, -11.3458996))
            )
            put(
                "SG",
                LatLngBounds(LatLng(1.1304753, 103.6920359), LatLng(1.4504753, 104.0120359))
            )
            put(
                "GS",
                LatLngBounds(LatLng(-59.684, -42.354739), LatLng(-53.3500755, -25.8468303))
            )
            put(
                "SH",
                LatLngBounds(LatLng(-16.23, -5.9973424), LatLng(-15.704, -5.4234153))
            )
            put(
                "SJ",
                LatLngBounds(LatLng(70.6260825, -9.6848146), LatLng(81.028076, 34.6891253))
            )
            put(
                "SB",
                LatLngBounds(LatLng(-13.2424298, 155.3190556), LatLng(-4.81085, 170.3964667))
            )
            put(
                "SL",
                LatLngBounds(LatLng(6.755, -13.5003389), LatLng(9.999973, -10.271683))
            )
            put(
                "SV",
                LatLngBounds(LatLng(12.976046, -90.1790975), LatLng(14.4510488, -87.6351394))
            )
            put(
                "SM",
                LatLngBounds(LatLng(43.8937002, 12.4033246), LatLng(43.992093, 12.5160665))
            )
            put(
                "SO",
                LatLngBounds(LatLng(-1.8031969, 40.98918), LatLng(12.1889121, 51.6177696))
            )
            put(
                "PM",
                LatLngBounds(LatLng(46.5507173, -56.6972961), LatLng(47.365, -55.9033333))
            )
            put(
                "RS",
                LatLngBounds(LatLng(42.2322435, 18.8142875), LatLng(46.1900524, 23.006309))
            )
            put(
                "ST",
                LatLngBounds(LatLng(-0.2135137, 6.260642), LatLng(1.9257601, 7.6704783))
            )
            put(
                "SR",
                LatLngBounds(LatLng(1.8312802, -58.070833), LatLng(6.225, -53.8433358))
            )
            put(
                "SK",
                LatLngBounds(LatLng(47.7314286, 16.8331891), LatLng(49.6138162, 22.56571))
            )
            put(
                "SI",
                LatLngBounds(LatLng(45.4214242, 13.3754696), LatLng(46.8766816, 16.5967702))
            )
            put(
                "SE",
                LatLngBounds(LatLng(55.1331192, 10.5930952), LatLng(69.0599699, 24.1776819))
            )
            put(
                "SZ",
                LatLngBounds(LatLng(-27.3175201, 30.7908), LatLng(-25.71876, 32.1349923))
            )
            put(
                "SC",
                LatLngBounds(LatLng(-10.4649258, 45.9988759), LatLng(-3.512, 56.4979396))
            )
            put(
                "SY",
                LatLngBounds(LatLng(32.311354, 35.4714427), LatLng(37.3184589, 42.3745687))
            )
            put(
                "TC",
                LatLngBounds(LatLng(20.9553418, -72.6799046), LatLng(22.1630989, -70.8643591))
            )
            put(
                "TD",
                LatLngBounds(LatLng(7.44107, 13.47348), LatLng(23.4975, 24.0))
            )
            put(
                "TG",
                LatLngBounds(LatLng(5.926547, -0.1439746), LatLng(11.1395102, 1.8087605))
            )
            put(
                "TH",
                LatLngBounds(LatLng(5.612851, 97.3438072), LatLng(20.4648337, 105.636812))
            )
            put(
                "TJ",
                LatLngBounds(LatLng(36.6711153, 67.3332775), LatLng(41.0450935, 75.1539563))
            )
            put(
                "TK",
                LatLngBounds(LatLng(-9.6442499, -172.7213673), LatLng(-8.3328631, -170.9797586))
            )
            put(
                "TM",
                LatLngBounds(LatLng(35.129093, 52.335076), LatLng(42.7975571, 66.6895177))
            )
            put(
                "TL",
                LatLngBounds(LatLng(-9.5642775, 124.0415703), LatLng(-8.0895459, 127.5335392))
            )
            put(
                "TO",
                LatLngBounds(LatLng(-24.1034499, -179.3866055), LatLng(-15.3655722, -173.5295458))
            )
            put(
                "TT",
                LatLngBounds(LatLng(9.8732106, -62.083056), LatLng(11.5628372, -60.2895848))
            )
            put(
                "TN",
                LatLngBounds(LatLng(30.230236, 7.5219807), LatLng(37.7612052, 11.8801133))
            )
            put(
                "TR",
                LatLngBounds(LatLng(35.8076804, 25.6212891), LatLng(42.297, 44.8176638))
            )
            put(
                "TV",
                LatLngBounds(LatLng(-9.9939389, 175.1590468), LatLng(-5.4369611, 178.7344938))
            )
            put(
                "TW",
                LatLngBounds(LatLng(10.374269, 114.3599058), LatLng(26.4372222, 122.297))
            )
            put(
                "TZ",
                LatLngBounds(LatLng(-11.761254, 29.3269773), LatLng(-0.9854812, 40.6584071))
            )
            put(
                "UG",
                LatLngBounds(LatLng(-1.4823179, 29.573433), LatLng(4.2340766, 35.000308))
            )
            put(
                "UA",
                LatLngBounds(LatLng(44.184598, 22.137059), LatLng(52.3791473, 40.2275801))
            )
            put(
                "UM",
                LatLngBounds(LatLng(6.1779744, -162.6816297), LatLng(6.6514388, -162.1339885))
            )
            put(
                "UY",
                LatLngBounds(LatLng(-35.7824481, -58.4948438), LatLng(-30.0853962, -53.0755833))
            )
            put(
                "US",
                LatLngBounds(LatLng(24.9493, -125.0011), LatLng(49.5904, -66.9326))
            )
            put(
                "UZ",
                LatLngBounds(LatLng(37.1821164, 55.9977865), LatLng(45.590118, 73.1397362))
            )
            put(
                "VA",
                LatLngBounds(LatLng(41.9002044, 12.4457442), LatLng(41.9073912, 12.4583653))
            )
            put(
                "VC",
                LatLngBounds(LatLng(12.5166548, -61.6657471), LatLng(13.583, -60.9094146))
            )
            put(
                "VE",
                LatLngBounds(LatLng(0.647529, -73.3529632), LatLng(15.9158431, -59.5427079))
            )
            put(
                "VG",
                LatLngBounds(LatLng(17.623468, -65.159094), LatLng(18.464984, -64.512674))
            )
            put(
                "VI",
                LatLngBounds(LatLng(17.623468, -65.159094), LatLng(18.464984, -64.512674))
            )
            put(
                "VN",
                LatLngBounds(LatLng(8.1790665, 102.14441), LatLng(23.393395, 114.3337595))
            )
            put(
                "VU",
                LatLngBounds(LatLng(-20.4627425, 166.3355255), LatLng(-12.8713777, 170.449982))
            )
            put(
                "WF",
                LatLngBounds(LatLng(-14.5630748, -178.3873749), LatLng(-12.9827961, -175.9190391))
            )
            put(
                "WS",
                LatLngBounds(LatLng(-14.2770916, -173.0091864), LatLng(-13.2381892, -171.1929229))
            )
            put(
                "YE",
                LatLngBounds(LatLng(11.9084802, 41.60825), LatLng(19.0, 54.7389375))
            )
            put(
                "ZA",
                LatLngBounds(LatLng(-47.1788335, 16.3335213), LatLng(-22.1250301, 38.2898954))
            )
            put(
                "ZM",
                LatLngBounds(LatLng(-18.0765945, 21.9993509), LatLng(-8.2712822, 33.701111))
            )
            put(
                "ZW",
                LatLngBounds(LatLng(-22.4241096, 25.2373), LatLng(-15.6097033, 33.0683413))
            )
        }
    }


}