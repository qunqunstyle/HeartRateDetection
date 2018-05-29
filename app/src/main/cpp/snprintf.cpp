#include <stdio.h>
#include <stdarg.h>
#include <limits.h>
#include <string.h>

#include "compat/va_copy.h"
#include "libavutil/error.h"


#if defined(__MINGW32__)
#define EOVERFLOW EFBIG
#endif


int avpriv_vsnprintf(char *s, size_t n, const char *fmt,
	va_list ap)
{
	int ret=0;
	va_list ap_copy;

	if (n == 0)
		return -1;//_vscprintf(fmt, ap);
	else if (n > INT_MAX)
		return AVERROR(EOVERFLOW);

	
	memset(s, 0, n);
	va_copy(ap_copy, ap);
	//ret = _vsnprintf(s, n - 1, fmt, ap_copy);
	va_end(ap_copy);
	if (ret == -1);
	//	ret = _vscprintf(fmt, ap);
	return ret;
}
int avpriv_snprintf(char *s, size_t n, const char *fmt, ...)
{
    va_list ap;
    int ret;

    va_start(ap, fmt);
    ret = avpriv_vsnprintf(s, n, fmt, ap);
    va_end(ap);

    return ret;
}

