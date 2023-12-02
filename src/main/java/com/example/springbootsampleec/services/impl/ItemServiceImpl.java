package com.example.springbootsampleec.services.impl;
 
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.repositories.ItemRepository;
import com.example.springbootsampleec.services.ItemService;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;
 
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;
 
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
 
// gradle で追加
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;

 
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    
    @Autowired
    private Environment environment; // 環境変数を使えるように。
 
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
 
    @Transactional(readOnly = true)
    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }
    

    @Transactional(readOnly = true)
    @Override
    public Optional<Item> findById(long id) {
        return itemRepository.findById(id);
    }
    
    @Transactional(readOnly = false)
    @Override
    public void updateItem(long id, String name, int price, int stock, String description,
    		MultipartFile image, MultipartFile img_1, MultipartFile img_2, MultipartFile img_3) {
        Item item =  findById(id).orElseThrow();
        item.setName(name);
        item.setPrice(price);
        item.setStock(stock);
        item.setDescription(description);
        
        if (image.getOriginalFilename().isEmpty()) {
            throw new RuntimeException("ファイルが設定されていません");
        }
        
     // 拡張子取得
        String extension_main = FilenameUtils.getExtension(image.getOriginalFilename());
        String extension_sub1 = FilenameUtils.getExtension(img_1.getOriginalFilename());
        String extension_sub2 = FilenameUtils.getExtension(img_2.getOriginalFilename());
        String extension_sub3 = FilenameUtils.getExtension(img_3.getOriginalFilename());
        
        // ランダムなファイル名を設定
        String randomFileName_main = RandomStringUtils.randomAlphanumeric(20) + "." + extension_main;
        uploadImage(image, randomFileName_main);
        String randomFileName_sub1 = RandomStringUtils.randomAlphanumeric(20) + "." + extension_sub1;
        uploadImage(img_1, randomFileName_sub1);
        String randomFileName_sub2 = RandomStringUtils.randomAlphanumeric(20) + "." + extension_sub2;
        uploadImage(img_2, randomFileName_sub2);
        String randomFileName_sub3 = RandomStringUtils.randomAlphanumeric(20) + "." + extension_sub3;
        uploadImage(img_3, randomFileName_sub3);
        
        item.setImage(randomFileName_main);
        item.setImg_1(randomFileName_sub1);
        item.setImg_2(randomFileName_sub2);
        item.setImg_3(randomFileName_sub3);
        
        itemRepository.saveAndFlush(item);
    }
    
    @Transactional
    @Override
    public void delete(long id) {
        Item item =  findById(id).orElseThrow();
        itemRepository.delete(item);
    }
 
    @Transactional
    @Override
    public void register(int shop_id, String name, int price, int stock, int type, String description,
   		 MultipartFile image,
   		 MultipartFile img_1,
   		 MultipartFile img_2,
   		 MultipartFile img_3) {
       if (image.getOriginalFilename().isEmpty()) {
           throw new RuntimeException("ファイルが設定されていません");
       }
       // 拡張子取得
       String extension_main = FilenameUtils.getExtension(image.getOriginalFilename());
       String extension_sub1 = FilenameUtils.getExtension(img_1.getOriginalFilename());
       String extension_sub2 = FilenameUtils.getExtension(img_2.getOriginalFilename());
       String extension_sub3 = FilenameUtils.getExtension(img_3.getOriginalFilename());
       
       // ランダムなファイル名を設定
       String randomFileName_main = RandomStringUtils.randomAlphanumeric(20) + "." + extension_main;
       uploadImage(image, randomFileName_main);
       String randomFileName_sub1 = RandomStringUtils.randomAlphanumeric(20) + "." + extension_sub1;
       uploadImage(img_1, randomFileName_sub1);
       String randomFileName_sub2 = RandomStringUtils.randomAlphanumeric(20) + "." + extension_sub2;
       uploadImage(img_2, randomFileName_sub2);
       String randomFileName_sub3 = RandomStringUtils.randomAlphanumeric(20) + "." + extension_sub3;
       uploadImage(img_3, randomFileName_sub3);
       
       // Item エンティティの生成
       Item item = new Item(null, null, null , name, price, stock, type, description,
       		randomFileName_main, randomFileName_sub1,
       		randomFileName_sub2, randomFileName_sub3, null, null);

 
        // Item を保存
        itemRepository.saveAndFlush(item);
    }
 
    private void uploadImage(MultipartFile multipartFile, String fileName) {
        // 保存先のパスを作成
        Path filePath = Paths.get(environment.getProperty("sample.images.imagedir") + fileName);
        try {
            // ファイルをバイト列に変換して書き込み
            byte[] bytes  = multipartFile.getBytes();
            OutputStream stream = Files.newOutputStream(filePath);
            stream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //検索機能
    @Override
	public List<Item> findByNameContaining(String keyword) {
		// TODO 自動生成されたメソッド・スタブ
		return itemRepository.findByNameContaining(keyword);
	}

}