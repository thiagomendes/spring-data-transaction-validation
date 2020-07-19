package br.com.tmdev.spring.data.transaction.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        exampleService.update(exampleEntity);

        logger.info("2 - Sleep 15s");
        Thread.sleep(15000);

        // Listing
        List<ExampleEntity> all = this.getAll();
        all.forEach(i -> System.out.println(i.getSomething()));
        //exampleService.execute();

        //throw new RuntimeException();
    }

    @GetMapping("/getAll")
    public List<ExampleEntity> getAll() {
        return exampleService.getAll();
    }
}

