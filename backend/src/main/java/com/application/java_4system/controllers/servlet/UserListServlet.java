package com.application.java_4system.controllers.servlet;

import com.application.java_4system.converters.SortCriteriaArrayConverter;
import com.application.java_4system.converters.SortCriteriaSingleValueConverter;
import com.application.java_4system.payload.requests.SearchSortPaginationConfig;
import com.application.java_4system.payload.requests.SortCriteria;
import com.application.java_4system.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServlet;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet("/user/list")
public class UserListServlet extends HttpServlet {
    private final SortCriteriaArrayConverter sortCriteriaArrayConverter;

    private final SortCriteriaSingleValueConverter sortCriteriaSingleValueConverter;


    private final UserService userService;

    @Autowired
    public UserListServlet(SortCriteriaArrayConverter sortCriteriaArrayConverter, SortCriteriaSingleValueConverter sortCriteriaSingleValueConverter, UserService userService) {
        this.sortCriteriaArrayConverter = sortCriteriaArrayConverter;
        this.sortCriteriaSingleValueConverter = sortCriteriaSingleValueConverter;
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pageParam = req.getParameter("page");
        String sizeParam = req.getParameter("size");
        String searchTerm = req.getParameter("searchTerm");

        int page = pageParam != null ? Integer.parseInt(pageParam) : 0;
        int size = sizeParam != null ? Integer.parseInt(sizeParam) : 1;

        SearchSortPaginationConfig config = new SearchSortPaginationConfig();
        config.setPage(page);
        config.setSize(size);
        config.setSearchTerm(searchTerm);

        List<SortCriteria> sortCriteria = new ArrayList<>();

        var parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            var paramName = parameterNames.nextElement().toString();
            if (paramName.startsWith("sort")) {
                String[] values = req.getParameterValues(paramName);
                for (String value : values) {
                    if (values.length > 1) {
                        sortCriteria.addAll(Objects.requireNonNull(sortCriteriaArrayConverter.convert(values)));
                    } else {
                        sortCriteria.addAll(Objects.requireNonNull(sortCriteriaSingleValueConverter.convert(value)));
                    }
                }
            }
        }
        config.setSortCriteria(sortCriteria);

        var userList = userService.list(config);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(resp.getWriter(), userList);
    }
}
