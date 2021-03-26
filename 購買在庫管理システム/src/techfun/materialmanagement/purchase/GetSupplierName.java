package techfun.materialmanagement.purchase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetSupplierName {

	public static void main(String[] args) {

		String buyerCd = null;
		String componentCd = null;
		String supplierCd = null;
		String supplierNm = null;
		int orderQty = 0;

		System.out.println("プログラム「GetSupplierName」の処理を開始します！");

		List<DeliveryBean> deliList = new ArrayList<DeliveryBean>();//リストのインスタンス化

		//マップのインスタンス化
		Map<String, String> delmap = new HashMap<String, String>();

		BufferedReader br1 = null;

		try {
			// 納品予定ファイルを読み込み
			br1 = new BufferedReader(new FileReader("C:\\temp\\jishuu\\DELIVERY_PLANS.txt"));

			String str = null;

			// ファイルには複数行文字が記述されてる場合もあるので繰り返し処理(一行ずつ読み込み)
			while ((str = br1.readLine()) != null) {
				DeliveryBean bean1 = new DeliveryBean();//インスタンス化
				//１行を","で分割
				String[] splbeen = str.split(",");
				//InfoBeanに格納
				bean1.setBuyerCd(splbeen[0]);//	購買担当コード
				bean1.setComponentCd(splbeen[1]);//部品コード
				bean1.setSupplierCd(splbeen[2]);//仕入先コード
				bean1.setSupplierNm(splbeen[3]);//仕入名
				bean1.setOrderQty(Integer.parseInt(splbeen[4]));//発注数量

				deliList.add(bean1);//リストにjavabeansの型で追加s
			}

			// 各個人情報をコンソールに出力
			//リストにあるだけループ
			//for (DeliveryBean bn : deliList) {
			//	System.out.println(bn.getBuyerCd() + "\t" + bn.getComponentCd() + "\t" + bn.getSupplierCd() + "\t"
			//			+ bn.getOrderQty());
			//}

		} catch (IOException e) {
			// 例外内容を表示します
			e.printStackTrace();

		} finally {
			if (br1 != null) {
				try {
					// ストリームをクローズします
					br1.close();

				} catch (IOException e) {
					// 例外内容を表示します
					e.printStackTrace();
				}
			}

		}

		//仕入先

		BufferedReader br2 = null;
		try {

			// 仕入先ファイルを読み込み
			br2 = new BufferedReader(new FileReader("C:\\temp\\jishuu\\SUPPLIER.txt"));

			String delstr = null;

			while ((delstr = br2.readLine()) != null) {

				//仕入先コードと仕入先名称をmapに格納
				supplierCd = delstr.substring(0, 8);
				supplierNm = delstr.substring(8, 48);

				delmap.put(supplierCd, supplierNm);
			}

		} catch (IOException e) {
			// 例外内容を表示します
			e.printStackTrace();

		} finally {
			if (br2 != null) {
				try {
					// ストリームをクローズします
					br2.close();

				} catch (IOException e) {
					// 例外内容を表示します
					e.printStackTrace();
				}
			}
		}

		// 新納品予定ファイルを書き込み

		//仕入先名称を追加

		// keySetメソッドを利用してキーを全件取得する

		for (DeliveryBean deliveryBean : deliList) {

			supplierCd = deliveryBean.getSupplierCd();//仕入コード

			// mapからsupplierCdをキーとした場合の値を取得する

			supplierNm = delmap.get(supplierCd);
			if (delmap.containsKey(supplierCd)) {
				//納品予定ファイルの仕入先名称を仕入先情報ファイルの仕入先名称に上書きする
				deliveryBean.setSupplierNm(supplierNm);
			}
		}

		BufferedWriter bw = null;

		try {

			// 書き込みストリームを生成します。
			bw = new BufferedWriter(new FileWriter("C:\\temp\\jishuu\\NEW_DELIVERY_PLANS.txt", true));

			//リストにあるだけループ
			for (DeliveryBean bn : deliList) {

				//ファイルに出力する
				bw.write(bn.getBuyerCd() + "," + bn.getComponentCd() + "," + bn.getSupplierCd() + ","
						+ bn.getSupplierNm() + "," + bn.getOrderQty());
				bw.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					// ストリームをクローズします
					bw.close();

				} catch (IOException e) {
					// 例外内容を表示します
					e.printStackTrace();
				}
			}
		}

		System.out.println("プログラム「GetSupplierName」の処理を終了します！");
	}

}
