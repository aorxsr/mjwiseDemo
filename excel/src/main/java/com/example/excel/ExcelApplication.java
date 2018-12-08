package com.example.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ExcelApplication implements CommandLineRunner {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private UserRepository userJpaRepository;

    public static void main(String[] args) {
        SpringApplication.run(ExcelApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        poi03();
        //上面那个是向数据库增加数据，下面这个是从数据库导出
        writeExcelAndData();
    }

    /**
     * 暫時用xlsx格式的
     *
     * @throws IOException
     */
    public void writeExcelAndData() throws IOException {

        System.out.println(userJpaRepository);
        System.out.println(areaRepository);

        File file = new File("D:\\upload\\writeDemo.xlsx");
        Workbook workbook = new XSSFWorkbook();
        Sheet areaSheet = workbook.createSheet("area");
        FileOutputStream outputStream = new FileOutputStream(file);
		/*workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();*/
        //创建文件，并且创建一个Sheet
//		System.out.println(areaList);
        List<Area> areaList = areaRepository.findAll();
        System.out.println(areaList.size());
        if (areaList.size() != 0) {
            //这是进来了 ，我们要设置第一行，第一行不在循环里面设置
            Row row = null;
            row = areaSheet.createRow(0);
            row.createCell(0).setCellValue("Num");
            row.createCell(1).setCellValue("Province");
            row.createCell(2).setCellValue("City()");
            row.createCell(3).setCellValue("District");
            row.createCell(4).setCellValue("PostCode");
            for (int i = 0; i < areaList.size(); i++) {
                System.out.println(i);
                row = areaSheet.createRow(i + 1);//创建多少个行
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(areaList.get(i).getProvince());
                row.createCell(2).setCellValue(areaList.get(i).getCity());
                row.createCell(3).setCellValue(areaList.get(i).getDistrict());
                row.createCell(4).setCellValue(areaList.get(i).getPostCode());
                System.out.println(row.toString());
            }
        }

        //以上是area的下面是User的
        Sheet userSheet = workbook.createSheet("user");
        Row row = userSheet.createRow(0);
        row.createCell(0).setCellValue("userId");
        row.createCell(1).setCellValue("name");
        row.createCell(2).setCellValue("address");

        List<User> userList = userJpaRepository.findAll();

        for (int i = 0; i < userList.size(); i++) {
            row = userSheet.createRow(i+1);
            row.createCell(0).setCellValue(userList.get(i).getId());
            row.createCell(1).setCellValue(userList.get(i).getUsername());
            row.createCell(2).setCellValue(userList.get(i).getPassword());
        }

        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();

    }


    public void poi03() {
        List<Area> areaList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        try {
            // 1、获取文件输入流
            InputStream inputStream = new FileInputStream("D:\\upload\\demo.xlsx");
            // 2、获取Excel工作簿对象
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            int sheetCount = workbook.getNumberOfSheets();
            System.out.println("Sheet共有:" + sheetCount + "个!");
            // 假设 当前是两个.也可能有多个
            List<Object> listEntity = new ArrayList<>();

            for (int i = 0; i < sheetCount; i++) {
                String sheetName = workbook.getSheetAt(i).getSheetName();
                switch (sheetName) {
                    case "area":
                        listEntity.add(workbook.getSheetAt(i));
                        break;
                    case "user":
                        listEntity.add(workbook.getSheetAt(i));
                        break;
                    default:
                        System.out.println(workbook.getSheetAt(i).getSheetName() + ":不属于本项目!");
                        break;
                }
            }

            for (int y = 0; y < listEntity.size(); y++) {

                // 3、得到Excel工作表对象
                XSSFSheet sheetAt = workbook.getSheetAt(y);
                // 4、循环读取表格数据
                for (Row row : sheetAt) {
                    // 首行（即表头）不读取
                    if (row.getRowNum() == 0) {
                        continue;
                    }

                    switch (sheetAt.getSheetName()) {
                        case "area":
                            // 读取当前行中单元格数据，索引从0开始
                            for (int i = 0; i < 5; i++) {
                                row.getCell(i).setCellType(CellType.STRING);
                            }
                            Area area = new Area();
                            area.setCity(row.getCell(2).getStringCellValue());
                            area.setDistrict(row.getCell(3).getStringCellValue());
                            area.setProvince(row.getCell(1).getStringCellValue());
                            area.setPostCode(row.getCell(4).getStringCellValue());
                            areaList.add(area);
                            break;
                        case "user":
                            // 读取当前行中单元格数据，索引从0开始
                            for (int i = 0; i < 4; i++) {
                                row.getCell(i).setCellType(CellType.STRING);
                            }
                            //单元格 是从1开始 我Excel第一行是ID  所以从第二行开始
                            User user = new User();
                            user.setUsername(row.getCell(2).getStringCellValue());
                            user.setPassword(row.getCell(3).getStringCellValue());
                            userList.add(user);
                            break;
                        default:
                            break;
                    }
                }
            }
            areaRepository.saveAll(areaList);
            userJpaRepository.saveAll(userList);
            // 5、关闭流
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
