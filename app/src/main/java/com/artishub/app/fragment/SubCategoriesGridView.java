package com.artishub.app.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artishub.app.R;
import com.artishub.app.databinding.GridViewLayoutBinding;

/**
 * Created by techugo on 30/6/17.
 */

public class SubCategoriesGridView extends android.support.v4.app.Fragment {
    GridViewLayoutBinding gridViewLayoutBinding;
//    SubCategroiesAdapter subCategroiesAdapter;
//    CategroiesWithSubCategories.MessageBean.SubcategoryBean subcategoryBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        gridViewLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.grid_view_layout, container, false);
        View view = gridViewLayoutBinding.getRoot();
//        gridViewLayoutBinding.recylerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        gridViewLayoutBinding.recylerView.addItemDecoration(new GridDecorator(getResources().getDimensionPixelSize(R.dimen._1dp), 2));
//        subcategoryBean = getArguments().getParcelable(AppConstants.PRODUCT);
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put(Key.CUSTOMER_ID, SharedPreference.getInstance(getActivity()).getData(Key.CUSTOMER_ID, ""));
//        hashMap.put(Key.SUB_CATEGORY_ID, subcategoryBean.getPkSubcategoryID() + "");
//        hashMap.put(Key.SUB_CATEGORY_NAME, subcategoryBean.getSubcategoryName() + "");
//        getProductOfSingleSubCategories(hashMap);
        return view;
    }

//    public void getProductOfSingleSubCategories(HashMap<String, String> hashMap) {
//        Common.startProgressDialog(getActivity());
//        Call<ProductSubCategoryResult> myRetro = RetrofitUtil.callRetrofit().getProductOfSingleSubCategory(hashMap, RetrofitUtil.getBearerAuth(SharedPreference.getInstance(getActivity()).getData(SpKey.BEARER_KEY, "")));
//        myRetro.enqueue(new Callback<ProductSubCategoryResult>() {
//            @Override
//            public void onResponse(Call<ProductSubCategoryResult> call, Response<ProductSubCategoryResult> response) {
//                Common.stopProgressDialog();
//                if (response.isSuccessful()) {
//                    SubCategoriesFragment.instance.setClick(true);
//                    ProductSubCategoryResult productSubCategoryResult = response.body();
//                    if (productSubCategoryResult.isSuccess() && productSubCategoryResult.getCode() == AppConstants.SUCCESS_CODE) {
//                        if (productSubCategoryResult.getMessage().getProducts().size() > 0) {
//                            gridViewLayoutBinding.recylerView.setVisibility(View.VISIBLE);
//                            gridViewLayoutBinding.noProduct.setVisibility(View.GONE);
//                            subCategroiesAdapter = new SubCategroiesAdapter(getActivity(), productSubCategoryResult.getMessage().getProducts(), new SubCategroiesAdapter.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(ProductSubCategoryResult.MessageBean.ProductsBean productsBean) {
//                                    RootFragment.instance.setProductDetailFragment("map", productsBean, null);
//
//                                }
//                            });
//                            gridViewLayoutBinding.recylerView.setAdapter(subCategroiesAdapter);
//                        } else {
//                            gridViewLayoutBinding.recylerView.setVisibility(View.GONE);
//                            gridViewLayoutBinding.noProduct.setVisibility(View.VISIBLE);
//                        }
//
//                    } else {
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProductSubCategoryResult> call, Throwable t) {
//                SubCategoriesFragment.instance.setClick(true);
//                Common.stopProgressDialog();
//            }
//        });
//    }
}
