package monitor.pictureutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class SignatureAndVerify2 {
	public static String RIVATE_KEY = "d:\\pem\\pkcs8_private_key.pem";
	public static String TUPU_PUBLIC_KEY = "d:\\pem\\open_tuputech_com_public_key.pem";

	/**
	 * 进行签名
	 * 
	 * @param sign_string
	 *            参与签名的文本
	 * 
	 **/
	public static String Signature(String sign_string) {
		try {
			// 读取你的密钥pkcs8_private_key.pem
			File private_key_pem = new File(RIVATE_KEY);
			System.out.println(private_key_pem.getParent());
			InputStream inPrivate = new FileInputStream(private_key_pem);
			String privateKeyStr = readKey(inPrivate);
			byte[] buffer = Base64.decode(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");

			// 获取密钥对象
			PrivateKey privateKey = (RSAPrivateKey) keyFactory
					.generatePrivate(keySpec);

			// 用私钥对信息生成数字签名
			Signature signer = Signature.getInstance("SHA256WithRSA");
			signer.initSign(privateKey);
			signer.update(sign_string.getBytes());
			byte[] signed = signer.sign();
			return new String(Base64.encode(signed));
		} catch (Exception e) {
			return "err";
		}
	}

	/**
	 * 进行验证
	 * 
	 * @param signature
	 *            数字签名
	 * @param json
	 *            真正的有效数据的字符串
	 * 
	 **/
	public static boolean Verify(String signature, String json) {
		try {
			// 读取图普公钥open_tuputech_com_public_key.pem
			File open_tuputech_com_public_key_pem = new File(TUPU_PUBLIC_KEY);
			InputStream inPublic = new FileInputStream(
					open_tuputech_com_public_key_pem);
			String publicKeyStr = readKey(inPublic);
			byte[] buffer = Base64.decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			// 获取公钥匙对象
			PublicKey pubKey = (RSAPublicKey) keyFactory
					.generatePublic(keySpec);

			Signature signer = Signature.getInstance("SHA256WithRSA");
			signer.initVerify(pubKey);
			signer.update(json.getBytes());
			// 验证签名是否正常
			return signer.verify(Base64.decode(signature));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 读取密钥信息
	 * 
	 * @param in
	 * @throws IOException
	 */
	private static String readKey(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String readLine = null;
		StringBuilder sb = new StringBuilder();
		while ((readLine = br.readLine()) != null) {
			if (readLine.charAt(0) == '-') {
				continue;
			} else {
				sb.append(readLine);
				sb.append('\r');
			}
		}
		return sb.toString();
	}
}