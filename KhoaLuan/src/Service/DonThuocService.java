package Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import enity.BenhNhan;
import enity.DonThuoc;
import enity.PhieuKhambenh;

public class DonThuocService {
	
	static String POST_DON_THUOC="http://13.250.190.43:5001/donthuoc/insert";
	
	/**
	 * @author Vien
	 * date : 13/5/2021
	 * @return Thêm đơn thuốc vào cơ sở dữ liệu 
	 * @decripstion : Thêm đơn thuốc bằng cái sử dụng RestFull API
	 * */
	//[START POST Request]
	public  DonThuoc POSTDonThuocReturnPK(DonThuoc donthuoc) throws IOException {
		DonThuoc bn= new DonThuoc();
		String readLine = null;
		Gson gson = new GsonBuilder()
    		    .setDateFormat("yyyy-MM-dd")
    		    .create();
		String POST_PARAMS = gson.toJson(donthuoc);
	    System.out.println(POST_PARAMS);
	    URL obj = new URL(POST_DON_THUOC);
	    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
	    postConnection.setRequestMethod("POST");
	    postConnection.setRequestProperty("Content-Type", "application/json");


	    postConnection.setDoOutput(true);
	    OutputStream os = postConnection.getOutputStream();
	    os.write(POST_PARAMS.getBytes());
	    os.flush();
	    os.close();


	    int responseCode = postConnection.getResponseCode();
	    

	    if (responseCode == HttpURLConnection.HTTP_OK) { //success
	    	BufferedReader in = new BufferedReader(
					new InputStreamReader(postConnection.getInputStream()));
			String response = new String();
			while ((readLine = in .readLine()) != null) {
				response+=(readLine);
			} in .close();
			Gson gson1 = new GsonBuilder()
	    		    .setDateFormat("yyyy-MM-dd")
	    		    .create();
			bn = gson1.fromJson(response, DonThuoc.class);

	        // print result
	        System.out.println(response.toString());
	    } else {
	        System.out.println("POST NOT WORKED");
	    }
	    return bn;
	}
	//[End POST Request]

	
}
