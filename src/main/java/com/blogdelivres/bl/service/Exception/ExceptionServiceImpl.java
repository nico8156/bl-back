package com.blogdelivres.bl.service.Exception;

import com.blogdelivres.bl.exceptions.CustomException;
import org.springframework.stereotype.Service;

@Service
public class ExceptionServiceImpl implements ExceptionService{
    @Override
    public void alertForException(){
        throw new CustomException("oups un probleme");
    }
}
