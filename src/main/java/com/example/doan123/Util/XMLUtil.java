package com.example.doan123.Util;

import com.example.doan123.Model.PhuTung;
import javafx.collections.transformation.FilteredList;

import org.w3c.dom.Document;
import  org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLUtil {
    //xuất file xml
    public static boolean xuatXML(List<PhuTung> phuTungList, File file){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // tạo thẻ gốc <khohang>
            Element rootElement = document.createElement("KhoHang");
            document.appendChild(rootElement);

            //duyệt phụ tùng
            for(PhuTung pt: phuTungList){
                Element phutung = document.createElement("PhuTung");
                rootElement.appendChild(phutung);

                Element id = document.createElement("ID");
                phutung.appendChild(id);

                Element ten = document.createElement("TenPhuTung");
                ten.appendChild(document.createTextNode(pt.getTenPhuTung() != null ? pt.getTenPhuTung() : ""));
                phutung.appendChild(ten);

                Element soLuong = document.createElement("SoLuong");
                soLuong.appendChild(document.createTextNode(String.valueOf(pt.getSoLuongTon())));
                phutung.appendChild(soLuong);


                Element donGia = document.createElement("DonGia");
                donGia.appendChild(document.createTextNode(String.valueOf(pt.getGiaBan())));
                phutung.appendChild(donGia);
            }

            // ghi ra file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");  // tự động thụt lề
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            return true;
        } catch (Exception e){
            System.out.println("Lỗi xuất XML: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //nhập file XML
    public static List<PhuTung> nhapXML(File file){
        List<PhuTung> phuTungList = new ArrayList<>();
        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("PhuTung");

            for(int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;

                    PhuTung pt = new PhuTung();
                    pt.setId(Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent()));
                    pt.setTenPhuTung(element.getElementsByTagName("TenPhuTung").item(0).getTextContent());
                    pt.setSoLuongTon(Integer.parseInt(element.getElementsByTagName("SoLuong").item(0).getTextContent()));
                    pt.setGiaBan(Double.parseDouble(element.getElementsByTagName("DonGia").item(0).getTextContent()));

                    phuTungList.add(pt);
                }
            }
        } catch (Exception e){
            System.out.println("❌ Lỗi đọc XML: " + e.getMessage());
            e.printStackTrace();
        }
        return phuTungList;
    }
}
