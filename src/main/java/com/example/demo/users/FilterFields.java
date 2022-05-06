package com.example.demo.users;

import com.example.demo.users.common.PageOptions;


public class FilterFields {
   public Integer id;
   public String name;
   public PageOptions pageOptions = new PageOptions();

   public FilterFields(Integer id, String name) {
      this.id = id;
      this.name = name;
   }
}
