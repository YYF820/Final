package ua.nure.hanzha.SummaryTask4.servlet.callable.facultiesSorter;

import ua.nure.hanzha.SummaryTask4.bean.FacultiesInfoBean;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 18/08/15.
 */
public class FacultiesSorterOperationsMap {

    private static final String SORT_TYPE_BY_NAME_ASC = "byNameAsc";
    private static final String SORT_TYPE_BY_NAME_DESC = "byNameDesc";
    private static final String SORT_TYPE_BY_BUDGET_SPOTS_ASC = "byBudgetSpotsAsc";
    private static final String SORT_TYPE_BY_BUDGET_SPOTS_DESC = "byBudgetSpotsDesc";
    private static final String SORT_TYPE_BY_ALL_SPOTS_ASC = "byAllSpotsAsc";
    private static final String SORT_TYPE_BY_ALL_SPOTS_DESC = "byAllSpotsDesc";

    private static FacultiesSorterOperationsMap facultiesSorterOperationsMap;
    private static Map<String, FacultiesSorterCallable> facultiesSorterCallableMap;

    private FacultiesSorterOperationsMap() {

    }

    public static FacultiesSorterOperationsMap getInstance() {
        if (facultiesSorterOperationsMap == null) {
            facultiesSorterOperationsMap = new FacultiesSorterOperationsMap();
        }
        return facultiesSorterOperationsMap;
    }

    public static FacultiesSorterCallable getFacultiesSorterCallable(String sortType) {
        return facultiesSorterCallableMap.get(sortType);
    }

    public static void initFacultiesSorterCallableMap(final HttpSession session, final List<FacultiesInfoBean> facultiesInfoBeans) {
        facultiesSorterCallableMap = new HashMap<>();
        facultiesSorterCallableMap.put(
                SORT_TYPE_BY_NAME_ASC,
                new FacultiesSorterCallable() {
                    @Override
                    public void call(String sessionAttrSortType) {
                        Collections.sort(facultiesInfoBeans, new Comparator<FacultiesInfoBean>() {
                            @Override
                            public int compare(FacultiesInfoBean o1, FacultiesInfoBean o2) {
                                return o1.getFaculty().getName().compareTo(o2.getFaculty().getName());
                            }
                        });
                        session.setAttribute(sessionAttrSortType, SORT_TYPE_BY_NAME_ASC);
                    }
                }
        );
        facultiesSorterCallableMap.put(
                SORT_TYPE_BY_NAME_DESC,
                new FacultiesSorterCallable() {
                    @Override
                    public void call(String sessionAttribute) {
                        Collections.sort(facultiesInfoBeans, new Comparator<FacultiesInfoBean>() {
                            @Override
                            public int compare(FacultiesInfoBean o1, FacultiesInfoBean o2) {
                                return o2.getFaculty().getName().compareTo(o1.getFaculty().getName());
                            }
                        });
                        session.setAttribute(sessionAttribute, SORT_TYPE_BY_NAME_DESC);
                    }
                }
        );
        facultiesSorterCallableMap.put(
                SORT_TYPE_BY_BUDGET_SPOTS_DESC,
                new FacultiesSorterCallable() {
                    @Override
                    public void call(String sessionAttribute) {
                        Collections.sort(facultiesInfoBeans, new Comparator<FacultiesInfoBean>() {
                            @Override
                            public int compare(FacultiesInfoBean o1, FacultiesInfoBean o2) {
                                if (o1.getFaculty().getBudgetSpots() < o2.getFaculty().getBudgetSpots()) {
                                    return 1;
                                } else if (o1.getFaculty().getBudgetSpots() > o2.getFaculty().getBudgetSpots()) {
                                    return -1;
                                } else {
                                    return 0;
                                }
                            }
                        });
                        session.setAttribute(sessionAttribute, SORT_TYPE_BY_BUDGET_SPOTS_DESC);
                    }
                }
        );
        facultiesSorterCallableMap.put(
                SORT_TYPE_BY_BUDGET_SPOTS_ASC,
                new FacultiesSorterCallable() {
                    @Override
                    public void call(String sessionAttribute) {
                        Collections.sort(facultiesInfoBeans, new Comparator<FacultiesInfoBean>() {
                            @Override
                            public int compare(FacultiesInfoBean o1, FacultiesInfoBean o2) {
                                if (o2.getFaculty().getBudgetSpots() < o1.getFaculty().getBudgetSpots()) {
                                    return 1;
                                } else if (o2.getFaculty().getBudgetSpots() > o1.getFaculty().getBudgetSpots()) {
                                    return -1;
                                } else {
                                    return 0;
                                }
                            }
                        });
                        session.setAttribute(sessionAttribute, SORT_TYPE_BY_BUDGET_SPOTS_ASC);
                    }
                }
        );
        facultiesSorterCallableMap.put(
                SORT_TYPE_BY_ALL_SPOTS_DESC,
                new FacultiesSorterCallable() {
                    @Override
                    public void call(String sessionAttribute) {
                        Collections.sort(facultiesInfoBeans, new Comparator<FacultiesInfoBean>() {
                            @Override
                            public int compare(FacultiesInfoBean o1, FacultiesInfoBean o2) {
                                if (o1.getFaculty().getTotalSpots() < o2.getFaculty().getTotalSpots()) {
                                    return 1;
                                } else if (o1.getFaculty().getTotalSpots() > o2.getFaculty().getTotalSpots()) {
                                    return -1;
                                } else {
                                    return 0;
                                }
                            }
                        });
                        session.setAttribute(sessionAttribute, SORT_TYPE_BY_ALL_SPOTS_DESC);
                    }
                }
        );
        facultiesSorterCallableMap.put(
                SORT_TYPE_BY_ALL_SPOTS_ASC,
                new FacultiesSorterCallable() {
                    @Override
                    public void call(String sessionAttribute) {
                        Collections.sort(facultiesInfoBeans, new Comparator<FacultiesInfoBean>() {
                            @Override
                            public int compare(FacultiesInfoBean o1, FacultiesInfoBean o2) {
                                if (o2.getFaculty().getTotalSpots() < o1.getFaculty().getTotalSpots()) {
                                    return 1;
                                } else if (o2.getFaculty().getTotalSpots() > o1.getFaculty().getTotalSpots()) {
                                    return -1;
                                } else {
                                    return 0;
                                }
                            }
                        });
                        session.setAttribute(sessionAttribute, SORT_TYPE_BY_ALL_SPOTS_ASC);
                    }
                }
        );
    }
}
