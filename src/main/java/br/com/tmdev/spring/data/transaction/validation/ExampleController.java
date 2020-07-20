package br.com.tmdev.spring.data.transaction.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Transactional
public class ExampleController {

    private Logger logger = LoggerFactory.getLogger(ExampleController.class);

    @Autowired
    private ExampleService exampleService;

    @PostMapping("/execute")
    public void execute() throws Exception {

        // Saving
        ExampleEntity exampleEntity = new ExampleEntity();
        exampleEntity.setSomething("Via insert: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        exampleService.save(exampleEntity);

        logger.info("1 - Sleep 15s");
        Thread.sleep(15000);

        //Updating
        //ExampleEntity exampleEntityByFind = exampleService.findById(1);
        //exampleService.update(exampleEntityByFind);
        exampleEntity.setSomething("Via update: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        exampleService.update(exampleEntity);

        logger.info("2 - Sleep 15s");
        Thread.sleep(15000);

        // Listing
        List<ExampleEntity> all = this.getAll();
        all.forEach(i -> System.out.println(i.getSomething()));
        //exampleService.execute();

        //throw new RuntimeException();
    }

    @PostMapping("/race")
    public ResponseEntity<Void> race() throws Exception {
        String threadName = Thread.currentThread().getName();
        logger.info("Thread name: " + threadName);

        ExampleEntity exampleEntityByFind = exampleService.findById(1);

        logger.info("1 - Sleep 15s");
        Thread.sleep(15000);

        exampleEntityByFind.setUsedBy(threadName);
        exampleEntityByFind.setInUse(1);
        //exampleService.update(exampleEntityByFind);
        int count = exampleService.updateUsedBy(exampleEntityByFind);

        if (count == 0) {
            logger.error("No rows updated");
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/clear")
    public void clearUse() {
        ExampleEntity exampleEntityByFind = exampleService.findById(1);
        exampleEntityByFind.setUsedBy(null);
        exampleEntityByFind.setInUse(0);
        exampleService.update(exampleEntityByFind);
    }

    @GetMapping("/getAll")
    public List<ExampleEntity> getAll() {
        return exampleService.getAll();
    }

    @PostMapping("/insert")
    public ExampleEntity insert() {
        ExampleEntity exampleEntity = new ExampleEntity();
        exampleEntity.setSomething("Inicial insert");
        return exampleService.insert(exampleEntity);
    }
}

