from allauth.socialaccount.adapter import DefaultSocialAccountAdapter
from users.models import User

class SocialAccountAdapter(DefaultSocialAccountAdapter):
    def pre_social_login(self, request, sociallogin):
        # social account already exists, so this is just a login
        if sociallogin.is_existing:
            return

        email = sociallogin.account.extra_data.get('email', None)
        if not (email):
            print('email not given')
            return

        try:
            existing_user = User.objects.get(email__iexact=email)
        except User.DoesNotExist:
            return

        # if it does, connect this new social login to the existing user
        sociallogin.connect(request, existing_user)