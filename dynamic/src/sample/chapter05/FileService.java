package sample.chapter05;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.sample.dao.FileDao;
import com.bstek.dorado.sample.entity.File;

@Component
public class FileService {

	@Resource
	private FileDao fileDao;

	@DataProvider
	public void getAll(Page<File> page) {
		fileDao.getAll(page);
	}

	@DataResolver
	@Transactional
	public void updateSave(Collection<File> files) {
		fileDao.persistEntities(files);
	}
}
