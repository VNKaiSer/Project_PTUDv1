/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import dto.DTO_CongNhan;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author DatPC
 */
public class test_dak {
    
    public static void main(String[] args) throws SQLException, ParseException {
        DAL_CongNhan dal = new DAL_CongNhan();
        DTO_CongNhan tmp = new DTO_CongNhan("20116031","Võ Tấn Đức",new Date(), true, "0329672303",new Date(),"tandatvok16@gmail.com","TanBinh","Đại học");
        dal.updateCongNhan(tmp);
    }
}
