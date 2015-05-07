package ro.teamnet.zth.app;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyParamRequest;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.fmk.AnnotationScanUtils;
import ro.teamnet.zth.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                        methodAttributes.setMethodParameterTypes(controllerMethod.getParameterTypes());
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void sendException(DispatchException de, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.printf("Nu s-a mapat URL-ul");
    }

    private Object dispatch(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String path = req.getPathInfo();
        
        MethodAttributes methodAttributes =  allowedMethods.get(path);
        if (methodAttributes != null) {

            String controllerClass = methodAttributes.getControllerClass();
            Class<?> controller = Class.forName(controllerClass);
            Object newControllerInstance = controller.newInstance();


            String methodName = methodAttributes.getMethodName();
            Method method = controller.getMethod(methodName, methodAttributes.getMethodParameterTypes());
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            if(parameterAnnotations.length > 0) {
                MyParamRequest annotation = (MyParamRequest) parameterAnnotations[0][0];
                List<String> methodParamsValues = new ArrayList<>();
                String valOfParanName = req.getParameter(annotation.paranName());
                methodParamsValues.add(valOfParanName);
                Object reply = method.invoke(newControllerInstance, (String[]) methodParamsValues.toArray(new String[0]));
                return reply;
            }
            else {
                return method.invoke(newControllerInstance);
            }

        }
        
        
        
        Object o = null;
        /*
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

        if(path.startsWith("/jobs")) {
            JobsController jobsController = new JobsController();
            String allJobs = jobsController.getAllJobs();
            return allJobs;
        }

        if(path.startsWith("/locations")) {
            LocationController locationController = new LocationController();
            String allLocations = locationController.getAllLocations();
            return allLocations;
        }
        */
        throw new DispatchException();
        //return o;
        //return "Hello";


    }

    private void reply(Object r, HttpServletRequest req, HttpServletResponse resp) throws IOException {
            PrintWriter out = resp.getWriter();
            //out.printf(r.toString());
            ObjectWriter mapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = mapper.writeValueAsString(r);
            out.write(json);
    }
}
