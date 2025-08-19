import type { Ref } from 'vue';

import { computed, ref, unref } from 'vue';

/**
 * Paginates an array of items
 * @param list The array to paginate
 * @param pageNo The current page number (1-based)
 * @param pageSize Number of items per page
 * @returns Paginated array slice
 * @throws {Error} If pageNo or pageSize are invalid
 */
function pagination<T = any>(list: T[], pageNo: number, pageSize: number): T[] {
  if (pageNo < 1) throw new Error('Page number must be positive');
  if (pageSize < 1) throw new Error('Page size must be positive');

  const offset = (pageNo - 1) * Number(pageSize);
  const ret =
    offset + pageSize >= list.length
      ? list.slice(offset)
      : list.slice(offset, offset + pageSize);
  return ret;
}

export function usePagination<T = any>(list: Ref<T[]>, pageSize: number) {
  const currentPage = ref(1);
  const pageSizeRef = ref(pageSize);

  const totalPages = computed(() =>
    Math.ceil(unref(list).length / unref(pageSizeRef)),
  );

  const paginationList = computed(() => {
    return pagination(unref(list), unref(currentPage), unref(pageSizeRef));
  });

  const total = computed(() => {
    return unref(list).length;
  });

  function setCurrentPage(page: number) {
    const maxPages = unref(totalPages);
    
    // 自动修正无效页码而不是抛出错误
    if (page < 1) {
      currentPage.value = 1;
    } else if (maxPages > 0 && page > maxPages) {
      currentPage.value = maxPages;
    } else if (maxPages === 0) {
      // 如果没有数据，设置为1
      currentPage.value = 1;
    } else {
      currentPage.value = page;
    }
  }

  function setPageSize(pageSize: number) {
    if (pageSize < 1) {
      throw new Error('Page size must be positive');
    }
    pageSizeRef.value = pageSize;
    // Reset to first page to prevent invalid state
    currentPage.value = 1;
  }

  return { setCurrentPage, total, setPageSize, paginationList };
}
