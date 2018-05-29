
#ifndef COMPAT_SNPRINTF_H
#define COMPAT_SNPRINTF_H

#include <stdarg.h>
#include <stdio.h>

int avpriv_snprintf(char *s, size_t n, const char *fmt, ...);
int avpriv_vsnprintf(char *s, size_t n, const char *fmt, va_list ap);

#undef snprintf
#undef _snprintf
#undef vsnprintf
#define snprintf avpriv_snprintf
#define _snprintf avpriv_snprintf
#define vsnprintf avpriv_vsnprintf

#endif /* COMPAT_SNPRINTF_H */
