package ro.teamnet.zth.app;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.app.controller.DepartmentController;
import ro.teamnet.zth.app.controller.EmployeeController;
import ro.teamnet.zth.fmk.AnnotationScanUtils;
import ro.teamnet.zth.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adi on 06.05.2015.
 */
public class MyDispatcherServlet extends HttpServlet {
    Map<String, MethodAttributes> allowedMethods = new HashMap<>();
    @Override
    public void init() throws ServletException {
        try {
            //gaseste  toate  clasele dintr-un pachet si aduce toate obiectele de tip class
            Iterable<Class> classes = AnnotationScanUtils.getClasses("ro.teamnet.zth.app.controller");
            //System.out.println(aClass.getName());
            getAllowedRequestMethods(classes);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getAllowedRequestMethods(Iterable<Class> classes) {
        for (Class controller : classes) {
            if(controller.isAnnotationPresent(MyController.class)) {
                MyController myControllerAnnotation  = (MyController)controller.getAnnotation(MyController.class);
                String controllerUrlPath = myControllerAnnotation.urlPath();
                Method[] controllerMethods = controller.getMethods();
                for(Method controllerMethod:controllerMethods) {
                    if (controllerMethod.isAnnotationPresent(MyRequestMethod.class)) {
                        MyRequestMethod myRequestMethod = (MyRequestMethod)controllerMethod.getAnnotation(MyRequestMethod.class);
                        String key = controllerUrlPath + myRequestMethod.urlPath();

                        MethodAttributes methodAttributes = new MethodAttributes();
                        methodAttributes.setControllerClass(controller.getName());
                        methodAttributes.setMethodName(controllerMethod.getName());
                        methodAttributes.setMethodType(myRequestMethod.methodType());

                        allowedMethods.put(key, methodAttributes);
                    }
                }
            }
        }
        System.out.println(allowedMethods.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Delegate to someone (an application controller)*/

        dispatchReply("GET", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Delegate to someone (an application controller)*/

        dispatchReply("POST", req, resp);
    }

    private void dispatchReply(String get, HttpServletRequest req, HttpServletResponse resp) {

        try {
        Object r = dispatch(req,resp);
            reply(r, req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DispatchException de) {
            try {
                sendException(de, resp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendException(DispatchException de, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.printf("Nu s-a mapat URL-ul");
    }

    private Object dispatch(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getPathInfo();

        Object o = null;
        if(path.startsWith("/employees")) {

            EmployeeController employeeController = new EmployeeController();
            //String allEmployees = employeeController.getAllEmployees();
            String allEmployees = employeeController.getAllEmployees();
            return allEmployees;
            //o = (String)allEmployees;


        }
        if(path.startsWith("/departments" )) {
            DepartmentController departmentController = new DepartmentController();
            String allDepartments = departmentController.getAllDepartments();
            return allDepartments;
        }
        throw new DispatchException();
        //return o;
        //return "Hello";


    }

    private void reply(Object r, HttpServletRequest req, HttpServletResponse resp) throws IOException {
            PrintWriter out = resp.getWriter();
            out.printf(r.toString());
    }
}
