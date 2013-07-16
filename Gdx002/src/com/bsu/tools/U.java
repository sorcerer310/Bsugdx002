package com.bsu.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.jws.soap.SOAPBinding.Style;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.GC.CLASSES;
import com.bsu.tools.GC.QUALITY;

public class U {

	private static Random rnd = new Random();

	/**
	 * 为一个固定值增加一部分浮动值
	 * 
	 * @param fix_val
	 *            固定的数值
	 * @param floatval_top
	 *            浮动值上限
	 * @param floatval_bottom
	 *            浮动值下限
	 * @return
	 */
	public static float getRandom(int fix_val, float floatval_bottom,
			float floatval_top) {
		int rndval = rnd
				.nextInt((int) ((floatval_top - floatval_bottom) * 10 + 1));
		return (fix_val + (rndval + floatval_bottom * 10)) / 10;
	}

	/**
	 * 计算攻击后产生的实际伤害
	 * 
	 * @param atkval
	 *            攻击方的攻击值
	 * @param defval
	 *            防御方的防御值
	 * @return 得出实际伤害
	 */
	public static int realDamage(int atkval, int defval) {
		return atkval * 100 / (100 + defval);
	}

	/**
	 * 判断rs中是否有Role在p坐标上
	 * 
	 * @param rs
	 *            所有的Role
	 * @param p
	 *            指定的box坐标
	 * @return
	 */
	public static boolean hasRoleInPos(Array<Role> rs, Vector2 p) {
		for (Role r : rs)
			if (r.getBoxX() == (int) p.x && r.getBoxY() == (int) p.y)
				return true;
		return false;
	}

	/**
	 * 给定一个机率参数，返回是否在机率之内
	 * 
	 * @param p
	 *            机率参数
	 * @return 返回机率为true或false
	 */
	public static boolean probability(float p) {
		if (p <= .0f)
			return false;
		if (p >= 1.0f)
			return true;
		int rval = rnd.nextInt(100);
		int pval = (int) (p * 100);
		System.out.println("rval:" + rval + " pval:" + pval);
		if (rval <= pval)
			// if(rnd.nextInt(100)<=(int)(p*100))
			return true;
		else
			return false;
	}

	public static Color getColorFromTalent(Role r, String s) {
		Color c = Color.WHITE;
		;
		float value = 0;
		if (s.equals("hp")) {
			value = r.hp_talent;
		}
		if (s.equals("attack")) {
			value = r.attack_talent;
		}
		if (s.equals("defend")) {
			value = r.defend_talent;
		}
		if (value < 1) {
			c = new Color(0, 255, 0, 1);
		}
		if (value >= 1 && value < 1.3f) {
			c = new Color(0, 0, 255, 1);
		}
		if (value >= 1.3f && value < 1.5f) {
			c = new Color(255, 0, 255, 1);
		}
		if (value >= 1.5f) {
			c = Color.ORANGE;
		}
		return c;
	}

	/**
	 * 根据role品质返回一个int,品质越高，int 越高
	 * 
	 * @return 绿色1.0，蓝色1.2，紫色1.4，橙色1.6
	 */
	public static float QualityInde(Role r) {
		float index = 0;
		if (r.quality == QUALITY.green) {
			index = 1;
		}
		if (r.quality == QUALITY.blue) {
			index = 1.2f;
		}
		if (r.quality == QUALITY.purple) {
			index = 1.4f;
		}
		if (r.quality == QUALITY.orange) {
			index = 1.6f;
		}
		return index;
	}

	// 取得角色基本的生命值
	public static int getRoleBaseHp(Role r) {
		int value = 0;
		if (r.classes == CLASSES.fighter) {
			value = GC.baseHpFight;
		}
		if (r.classes == CLASSES.cleric) {
			value = GC.baseHpCleric;
		}
		if (r.classes == CLASSES.wizard) {
			value = GC.baseHpWizard;
		}
		if (r.classes == CLASSES.sorcerer) {
			value = GC.baseHpSorcerer;
		}
		if (r.classes == CLASSES.archer) {
			value = GC.baseHpArcher;
		}
		return value;
	}

	// 取得角色基本攻击值
	public static int getRoleBaseAttack(Role r) {
		int value = 0;
		if (r.classes == CLASSES.fighter) {
			value = GC.baseAttackFight;
		}
		if (r.classes == CLASSES.cleric) {
			value = GC.baseAttackCleric;
		}
		if (r.classes == CLASSES.wizard) {
			value = GC.baseAttackWizard;
		}
		if (r.classes == CLASSES.sorcerer) {
			value = GC.baseAttackSorcerer;
		}
		if (r.classes == CLASSES.archer) {
			value = GC.baseAttackArcher;
		}
		return value;
	}

	// 取得角色基本防御值
	public static int getRoleBaseDefend(Role r) {
		int value = 0;
		if (r.classes == CLASSES.fighter) {
			value = GC.baseDefendFight;
		}
		if (r.classes == CLASSES.cleric) {
			value = GC.baseDefendCleric;
		}
		if (r.classes == CLASSES.wizard) {
			value = GC.baseDefendWizard;
		}
		if (r.classes == CLASSES.sorcerer) {
			value = GC.baseDefendSorcerer;
		}
		if (r.classes == CLASSES.archer) {
			value = GC.baseDefendArcher;
		}
		return value;
	}

	/**
	 * 获得角色的当前等级基本HP
	 * 
	 * @return
	 */
	public static int getCurrentBaseHp(Role r, int index) {
		return (int) (getRoleBaseHp(r) * r.hp_talent * index * QualityInde(r));
	}

	/**
	 * 获得角色的当前等级基本攻击力
	 * 
	 * @return
	 */
	public static int getCurrentBaseAttack(Role r, int index) {
		return (int) (getRoleBaseAttack(r) * r.attack_talent * index * QualityInde(r));
	}

	/**
	 * 获得角色的当前等级基本防御
	 * 
	 * @return
	 */
	public static int getCurrentBaseDefend(Role r, int index) {
		return (int) (getRoleBaseDefend(r) * r.defend_talent * index * QualityInde(r));
	}

	/**
	 * 获得升级所需经验
	 * 
	 * @return
	 */
	public static int getUpExp(Role r, int index) {
		int value = 0;
		for (int i = 1; i <= index; i++) {
			value += i;
		}
		return (int) (GC.baseExpUp * value / 2 * QualityInde(r));
	}

	/**
	 * 返回角色的职业字符串
	 * 
	 * @param r
	 * @return
	 */
	public static String getClasses(Role r) {
		String n = null;
		if (r.classes == CLASSES.fighter)
			n = "战士";// 战士
		if (r.classes == CLASSES.cleric)
			n = "牧师";// 牧师
		if (r.classes == CLASSES.wizard)
			n = "元素";// 元素
		if (r.classes == CLASSES.sorcerer)
			n = "黑暗";// 黑暗
		if (r.classes == CLASSES.archer)
			n = "弓手";// 弓手
		return n;
	}

	/**
	 * 设置显示头像
	 * 
	 * @param roleArray
	 *            将要取消显示的数组
	 * @param r
	 *            显示的目标role
	 */
	public static void showRoleSelect(Array<Role> roleArray, Role r) {
		for (Role e : roleArray) {
			e.roleIcon.showEffect(false);
			if (e.roleIcon != null)
				e.roleIcon.showEffect(false);
		}
		r.roleIcon.showEffect(true);
		if (r.roleIcon != null)
			r.roleIcon.showEffect(true);
	}

	public static void showRolesSelect(Array<Role> roleArray, Array<Role> r) {
		for (Role e : roleArray) {
			e.roleIcon.showEffect(false);
		}
		for (Role e : r) {
			e.roleIcon.showEffect(true);
		}
	}

	/**
	 * 将一个字符串分成几行
	 * 
	 * @param sv
	 *            缩放比
	 * @param s
	 * @return 字符串数组
	 */
	public static Array<String> getMuLabel(String s, float sv, float w) {
		Array<String> labelArray = new Array<String>();
		int nums = (int) (s.length() * sv * fontSize / w);
		if (s.length() * sv * fontSize % w == 0) {
			nums--;
		}
		int index = 0;
		if (nums > 0) {
			int startIndex = 0;
			while (index <= nums) {
				for (int i = startIndex; i <= s.length(); i++) {
					if (s.substring(startIndex, i).length() * sv * fontSize > w) {
						labelArray.add(s.substring(startIndex, i));
						index++;
						startIndex = i;
						break;
					} else {
						if (i == s.length()) {
							labelArray.add(s.substring(startIndex, i));
							index++;
							break;
						}
					}
				}
			}
		} else {
			labelArray.add(s);
		}
		return labelArray;
	}

	/**
	 * 选择的图片设置高亮（apha==1）
	 * 
	 * @param skillArray
	 * @param s
	 */
	public static void setSelectImg(Array<Image> allArray, Image s) {
		for (Image img : allArray) {
			setAlpha(img, 0.5f);
		}
		setAlpha(s, 1);
	}

	/**
	 * 当前技能
	 * 
	 * @param allArray
	 * @param s
	 * @param b
	 *            是否含有边框
	 */
	public static void setCurrentSkillImg(Array<Image> allArray, Image s) {
		for (int i = 0; i < allArray.size; i += 2) {
			setAlpha(allArray.get(i), 0.5f);
		}
		setAlpha(s, 1);
	}

	/**
	 * 设置一个widget透明度
	 * 
	 * @param img
	 * @param a
	 */

	public static void setAlpha(Actor actor, float a) {
		Color c = actor.getColor();
		actor.setColor(c.r, c.g, c.b, a);
	}

	/**
	 * 取得品质对应文字颜色
	 * 
	 * @param q
	 * @return
	 */
	public static Color getQualityColor(QUALITY q) {
		Color c = Color.WHITE;
		if (q == QUALITY.green) {
			c = new Color(0, 255, 0, 1);
		}
		if (q == QUALITY.blue) {
			c = new Color(0, 0, 255, 1);
		}
		if (q == QUALITY.purple) {
			c = new Color(255, 0, 255, 1);
		}
		if (q == QUALITY.orange) {
			c = Color.ORANGE;
		}
		return c;
	}

	private static FreeTypeFontGenerator Generator;
	private static FreeTypeBitmapFontData fontData;
	private static BitmapFont font;
	public static int fontSize = 20;

	/**
	 * 取得FONT
	 * 
	 * @return
	 */
	public static BitmapFont get_font() {
		if (Generator == null) {
			Generator = new FreeTypeFontGenerator(
					Gdx.files.internal("data/font/game.png"));
			fontData = Generator
					.generateData(
							fontSize,
							Generator.DEFAULT_CHARS
									+ "眩猝躯橙一乙二十丁厂七卜八人入儿九几了乃刀力又三干于亏士土工才下寸丈大与万上小口山千乞川亿个么久勺丸夕凡及广亡门义之尸已弓己卫子也女飞刃习叉马乡丰王井开夫天元无云专扎艺木五支厅不太犬区历友尤匹车巨牙屯比互切瓦止少日中贝内水冈见手午牛毛气升长仁什片仆化仇币仍仅斤爪反介父从今凶分乏公仓月氏勿风欠丹匀乌勾凤六文方火为斗忆计订户认心尺引丑巴孔队办以允予劝双书幻玉刊末未示击打巧正扑扒功扔去甘世古节本术可丙左厉石右布龙平灭轧东卡北占业旧帅归目旦且叮叶甲申号电田由只央史兄叼叫叨另叹四生失禾丘付仗代仙们仪白仔他斥瓜乎丛令用甩印乐句匆册犯外处冬鸟务包饥主市立闪兰半汁汇头汉宁穴它讨写让礼训必议讯记永司尼民出辽奶奴加召皮边孕发圣对台矛纠母幼丝式刑动扛寺吉扣考托老圾巩执扩扫地扬场耳共芒亚芝朽朴机权过臣再协西压厌在百有存而页匠夸夺灰达列死成夹轨邪划迈毕至此贞师尘尖劣光当早吐吓虫曲团同吊吃因吸吗屿帆岁回岂则刚网肉年朱先丢舌竹迁乔伟传乒乓休伍伏优伐延件任伤价份华仰仿伙伪自血向似后行舟全会杀合兆企众爷伞创肌朵杂危旬旨负各名多争色壮冲冰庄庆亦刘齐交次衣产决充妄闭问闯羊并关米灯州汗污江池汤忙兴宇守宅字安讲军许论农讽设访寻那迅尽导异孙阵阳收阶阴防奸如妇好她妈戏羽观欢买红纤约级纪驰巡寿弄麦形进戒吞远违运扶抚坛技坏扰拒找批扯址走抄坝贡攻赤折抓扮抢孝均抛投坟坑抗坊抖护壳志块扭声把报却劫芽花芹芬苍芳严芦劳克苏杆杜杠材村杏极李杨求更束豆两丽医辰励否还歼来连步坚旱盯呈时吴助县里呆园旷围呀吨足邮男困吵串员听吩吹呜吼吧别岗帐财钉针告我乱利秃秀私每兵估体何但伸作伯伶佣低你住位伴身皂佛近彻役返余希坐谷妥含邻岔肝肚肠龟免狂犹角删条卵岛迎饭饮系言冻状亩况床库疗应冷这序辛弃冶忘闲间闷判灶灿弟汪沙汽沃泛沟没沈沉怀忧快完宋宏牢究穷灾良证启评补初社识诉诊词译君灵即层尿尾迟局改张忌际陆阿陈阻附妙妖妨努忍劲鸡驱纯纱纲纳纵驳纷纸纹纺驴纽奉玩环武青责现表规抹拢拔拣坦担押抽拐拖者拍顶拆拥抵拘势抱垃拉拦幸拌招坡披拨择抬其取苦若茂苹苗英范直茄茎茅林枝杯柜析板松枪构杰述枕丧或画卧事刺枣雨卖矿码厕奔奇奋态欧垄妻轰顷转斩轮软到非叔肯齿些虎虏肾贤尚旺具果味昆国昌畅明易昂典固忠咐呼鸣咏呢岸岩帖罗帜岭凯败贩购图钓制知垂牧物乖刮秆和季委佳侍供使例版侄侦侧凭侨佩依的迫质欣征往爬彼径所舍金命斧爸采受乳贪念贫肤肺肢肿胀朋股肥服胁周昏鱼兔狐忽狗备饰饱饲变京享店夜庙府底剂郊废净盲放刻育闸闹郑券卷单炒炊炕炎炉沫浅法泄河沾泪油泊沿泡注泻泳泥沸波泼泽治怖性怕怜怪学宝宗定宜审宙官空帘实试郎诗肩房诚衬衫视话诞询该详建肃隶录居届刷屈弦承孟孤陕降限妹姑姐姓始驾参艰线练组细驶织终驻驼绍经贯奏春帮珍玻毒型挂封持项垮挎城挠政赴赵挡挺括拴拾挑指垫挣挤拼挖按挥挪某甚革荐巷带草茧茶荒茫荡荣故胡南药标枯柄栋相查柏柳柱柿栏树要咸威歪研砖厘厚砌砍面耐耍牵残殃轻鸦皆背战点临览竖省削尝是盼眨哄哑显冒映星昨畏趴胃贵界虹虾蚁思蚂虽品咽骂哗咱响哈咬咳哪炭峡罚贱贴骨钞钟钢钥钩卸缸拜看矩怎牲选适秒香种秋科重复竿段便俩货顺修保促侮俭俗俘信皇泉鬼侵追俊盾待律很须叙剑逃食盆胆胜胞胖脉勉狭狮独狡狱狠贸怨急饶蚀饺饼弯将奖哀亭亮度迹庭疮疯疫疤姿亲音帝施闻阀阁差养美姜叛送类迷前首逆总炼炸炮烂剃洁洪洒浇浊洞测洗活派洽染济洋洲浑浓津恒恢恰恼恨举觉宣室宫宪突穿窃客冠语扁袄祖神祝误诱说诵垦退既屋昼费陡眉孩除险院娃姥姨姻娇怒架贺盈勇怠柔垒绑绒结绕骄绘给络骆绝绞统耕耗艳泰珠班素蚕顽盏匪捞栽捕振载赶起盐捎捏埋捉捆捐损都哲逝捡换挽热恐壶挨耻耽恭莲莫荷获晋恶真框桂档桐株桥桃格校核样根索哥速逗栗配翅辱唇夏础破原套逐烈殊顾轿较顿毙致柴桌虑监紧党晒眠晓鸭晃晌晕蚊哨哭恩唤啊唉罢峰圆贼贿钱钳钻铁铃铅缺氧特牺造乘敌秤租秧积秩称秘透笔笑笋债借值倚倾倒倘俱倡候俯倍倦健臭射躬息徒徐舰舱般航途拿爹爱颂翁脆脂胸胳脏胶脑狸狼逢留皱饿恋桨浆衰高席准座症病疾疼疲脊效离唐资凉站剖竞部旁旅畜阅羞瓶拳粉料益兼烤烘烦烧烛烟递涛浙涝酒涉消浩海涂浴浮流润浪浸涨烫涌悟悄悔悦害宽家宵宴宾窄容宰案请朗诸读扇袜袖袍被祥课谁调冤谅谈谊剥恳展剧屑弱陵陶陷陪娱娘通能难预桑绢绣验继球理捧堵描域掩捷排掉推堆掀授教掏掠培接控探据掘职基著勒黄萌萝菌菜萄菊萍菠营械梦梢梅检梳梯桶救副票戚爽聋袭盛雪辅辆虚雀堂常匙晨睁眯眼悬野啦晚啄距跃略蛇累唱患唯崖崭崇圈铜铲银甜梨犁移笨笼笛符第敏做袋悠偿偶偷您售停偏假得衔盘船斜盒鸽悉欲彩领脚脖脸脱象够猜猪猎猫猛馅馆凑减毫麻痒痕廊康庸鹿盗章竟商族旋望率着盖粘粗粒断剪兽清添淋淹渠渐混渔淘液淡深婆梁渗情惜惭悼惧惕惊惨惯寇寄宿窑密谋谎祸谜逮敢屠弹随蛋隆隐婚婶颈绩绪续骑绳维绵绸绿琴斑替款堪塔搭越趁趋超提堤博揭喜插揪搜煮援裁搁搂搅握揉斯期欺联散惹葬葛董葡敬葱落朝辜葵棒棋植森椅椒棵棍棉棚棕惠惑逼厨厦硬确雁殖裂雄暂雅辈悲紫辉敞赏掌晴暑最量喷晶喇遇喊景践跌跑遗蛙蛛蜓喝喂喘喉幅帽赌赔黑铸铺链销锁锄锅锈锋锐短智毯鹅剩稍程稀税筐等筑策筛筒答筋筝傲傅牌堡集焦傍储奥街惩御循艇舒番释禽腊脾腔鲁猾猴然馋装蛮就痛童阔善羡普粪尊道曾焰港湖渣湿温渴滑湾渡游滋溉愤慌惰愧愉慨割寒富窜窝窗遍裕裤裙谢谣谦属屡强粥疏隔隙絮嫂登缎缓骗编缘瑞魂肆摄摸填搏塌鼓摆携搬摇搞塘摊蒜勤鹊蓝墓幕蓬蓄蒙蒸献禁楚想槐榆楼概赖酬感碍碑碎碰碗碌雷零雾雹输督龄鉴睛睡睬鄙愚暖盟歇暗照跨跳跪路跟遣蛾蜂嗓置罪罩错锡锣锤锦键锯矮辞稠愁筹签简毁舅鼠催傻像躲微愈遥腰腥腹腾腿触解酱痰廉新韵意粮数煎塑慈煤煌满漠源滤滥滔溪溜滚滨粱滩慎誉塞谨福群殿辟障嫌嫁叠缝缠静碧璃墙嘉摧截誓境摘摔撇聚慕暮蔑蔽模榴榜榨歌遭酷酿酸磁愿需裳颗嗽蜻蜡蝇蜘赚锹锻舞稳算箩管僚鼻魄貌膜膊膀鲜疑馒裹敲豪膏遮腐瘦辣竭端旗精歉弊熄熔漆漂漫滴演漏慢寨赛察蜜谱嫩翠熊凳骡缩慧撕撒趣趟撑播撞撤增聪鞋蕉蔬横槽樱橡飘醋醉震霉瞒题暴瞎影踢踏踩踪蝶蝴嘱墨镇靠稻黎稿稼箱箭篇僵躺僻德艘膝膛熟摩颜毅糊遵潜潮懂额慰劈操燕薯薪薄颠橘整融醒餐嘴蹄器赠默镜赞篮邀衡膨雕磨凝辨辩糖糕燃澡激懒壁避缴戴擦鞠藏霜霞瞧蹈螺穗繁辫赢糟糠燥臂翼骤鞭覆蹦镰翻鹰警攀蹲颤瓣爆疆壤耀躁嚼嚷籍魔灌蠢霸露囊罐",
							false);
			font = new BitmapFont(fontData, fontData.getTextureRegion(), false);
		}
		return font;
	}

	static LabelStyle label_style;

	/**
	 * 取得目标色label样式
	 * 
	 * @return
	 */
	public static LabelStyle get_Label_sytle() {
		if (label_style == null) {
			BitmapFont f = get_font();
			label_style = new LabelStyle(f, f.getColor());
		}
		return label_style;
	}

	static TextButtonStyle text_button_style;
	static TextButtonStyle normal_button_style;
	// 取得目标按钮样式
	public static TextButtonStyle get_text_button_style() {
		if (text_button_style == null) {
			TextButtonStyle textButtonStyle = new TextButtonStyle();
			textButtonStyle.up = U.get_skin().newDrawable("white",
					Color.DARK_GRAY);
			textButtonStyle.down = U.get_skin().newDrawable("white",
					Color.DARK_GRAY);
			textButtonStyle.checked = U.get_skin().newDrawable("white",
					Color.BLUE);
			textButtonStyle.over = U.get_skin().newDrawable("white",
					Color.LIGHT_GRAY);
			textButtonStyle.font = U.get_font();
			text_button_style = textButtonStyle;
		}
		return text_button_style;
	}
	//取得普通样式
	public static TextButtonStyle get_normal_button_style(){
		if(normal_button_style==null){
			TextButtonStyle textButtonStyle = new TextButtonStyle();
			textButtonStyle.font = U.get_font();
			normal_button_style=textButtonStyle;
		}
		return normal_button_style;
	}

	static Skin skin;

	public static Skin get_skin() {
		if (skin == null) {
			skin = new Skin(Gdx.files.internal("data/skin/bsuuiskin.json"));
		}
		return skin;
	}

	/**
	 * 将TiledMap中的坐标转换为gdx中的块坐标
	 * 
	 * @param x
	 *            TiledMap中的坐标x
	 * @param y
	 *            TiledMap中的坐标y
	 * @return 返回转换为box的坐标
	 */
	public static Vector2 TiledPos2GdxBoxPos(int x, int y) {
		int retx = x / GC.map_box_value;

		int rety = (GC.rect_height - y) / GC.map_box_value;
		return new Vector2(retx, rety);
	}

	/**
	 * 将真实坐标转为格子坐标
	 * 
	 * @param x
	 *            真实坐标
	 * @param y
	 *            真实坐标
	 * @return 返回对应的格子坐标
	 */
	public static Vector2 realPost2BoxPos(int x, int y) {
		return new Vector2(x / GC.map_box_value, y / GC.map_box_value);
	}

	/**
	 * 通过value获得key，由于一个value可能对应多个key，所以只返回最先查到的key即可
	 * 
	 * @param <T1>
	 *            hashmap key类型
	 * @param <T2>
	 *            hashmap value类型
	 * @return 返回通过value查到的key
	 */
	public static <T1, T2> T1 getKeyByValue(HashMap<T1, T2> hm, T2 v) {
		if (!hm.containsValue(v))
			return null;
		Set<Map.Entry<T1, T2>> set = hm.entrySet();
		Iterator<Map.Entry<T1, T2>> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<T1, T2> e = it.next();
			if (e.getValue() == v)
				return e.getKey();
			if (e.getValue().equals(v))
				return e.getKey();
		}
		return null;
	}

	/**
	 * 将字符串品质数据转为QUALITY类型值,默认返回green
	 * 
	 * @param q
	 *            带入的字符串品质
	 * @return
	 */
	public static QUALITY str2Quality(String q) {
		if (q.equals("green"))
			return QUALITY.green;
		else if (q.equals("blue"))
			return QUALITY.blue;
		else if (q.equals("orange"))
			return QUALITY.orange;
		else if (q.equals("purple"))
			return QUALITY.purple;
		else
			return QUALITY.green;
	}

	/**
	 * 通过上场的英雄判断是否有增加奖励技能，如果有计算增加多少百分比
	 * 
	 * @param froles
	 *            带入的战斗队伍
	 * @return 返回增加的成功率百分比
	 */
	public static float getRewardFromFightRole(Array<Role> froles) {
		for (int i = 0; i < froles.size; i++) {
			Role r = froles.get(i);
			for (int j = 0; j < r.skill_tree.size; j++) {
				Skill skl = r.skill_tree.get(j);
				if (skl.id == 72)
					return skl.getVal();
			}
		}
		return 1.0f;
	}
}
