package br.com.tmdev.spring.data.transaction.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExampleService {

    private Logger logger = LoggerFactory.getLogger(ExampleService.class);

    @Autowired
    private ExampleRepository exampleRepository;

    @Transactional()
    public void execute() throws Exception {
        // Saving
        ExampleEntity exampleEntity = new ExampleEntity();
        exampleEntity.setSomething("something");
        this.save(exampleEntity);

        logger.info("1 - Sleep 15s");
        Thread.sleep(15000);

        //Updating
        ExampleEntity exampleEntityByFind = this.findById(1);
        this.update(exampleEntityByFind);

        logger.info("2 - Sleep 15s");
        Thread.sleep(15000);

        // Listing
        List<ExampleEntity> all = this.getAll();
        all.forEach(i -> System.out.println(i.getSomething()));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(ExampleEntity exampleEntity) {
        exampleRepository.save(exampleEntity);
    }

    public List<ExampleEntity> getAll() {
        return exampleRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(ExampleEntity exampleEntity) {
        exampleEntity.setSomething("Via update: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        exampleRepository.save(exampleEntity);
    }

    public ExampleEntity findById(Integer id) {
        return exampleRepository.findById(id).orElse(null);
    }

}
